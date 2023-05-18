package com.example.leaguelookup

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.example.leaguelookup.api.ChampionApi
import com.example.leaguelookup.api.MasteryApi
import com.example.leaguelookup.api.SummonerApi
import com.example.leaguelookup.database.ChampionDao
import com.example.leaguelookup.database.ChampionDatabase
import com.example.leaguelookup.database.SummonerDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.*

class DataRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val championApi: ChampionApi
    private val summonerApi: SummonerApi
    private val masteryApi: MasteryApi
    private val championDao: ChampionDao
    private val summonerDao: SummonerDao
    private val championURL = getApiUrl()
    var region: String = "na1"

    init {
        val championRetrofit = Retrofit.Builder()
            .baseUrl(championURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        championApi = championRetrofit.create(ChampionApi::class.java)

        val summonerRetrofit = Retrofit.Builder()
            .baseUrl("https://${region}.api.riotgames.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        summonerApi = summonerRetrofit.create(SummonerApi::class.java)

        val masteryRetrofit = Retrofit.Builder()
            .baseUrl("https://${region}.api.riotgames.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        masteryApi = masteryRetrofit.create(MasteryApi::class.java)

        val database = ChampionDatabase.getDatabase(context)
        championDao = database.championDao()
        summonerDao = database.summonerDao()

        coroutineScope.launch {
            updateChampionDatabaseIfNeeded()
        }
    }

    fun setSelectedRegion(newRegion: String) {
        region = newRegion
    }

    private suspend fun fetchSummoner(summonerName: String): SummonerResponse {
        return summonerApi.fetchSummoner(summonerName)
    }

    suspend fun fetchMastery(id: String): List<MasteryEntity> {
        val masteryList = masteryApi.fetchMastery(id)
        return masteryList.map { masteryObj ->
            masteryObj.toMasteryEntity(masteryObj.championId, this)
        }
    }

    suspend fun getChampionById(Id: String): ChampionEntity {
        return championDao.getChampion(Id)
    }

    suspend fun updateChampionDatabaseIfNeeded() {
        // Check if the database is instantiated
        val localVersion = championDao.getVersion()

        // Fetch the ChampionResponse from the API
        val championResponse = championApi.fetchChampions()

        // Check if the versions match
        if ((localVersion == null) || (localVersion.version != championResponse.version)) {
            // If they don't match, update both databases
            val championEntities = championResponse.data.map { it.value.toChampionEntity() }
            val versionEntity = VersionEntity(version = championResponse.version)

            championDao.insertChampions(championEntities)
            championDao.insertVersion(versionEntity)
        }
    }

    fun getSummoners(): Flow<List<SummonerEntity>> = summonerDao.getSummoners()

    suspend fun addSummoner(summonerName: String, team: Boolean) {
        val summonerResponse = fetchSummoner(summonerName)
        val summonerEntity = summonerResponse.toSummonerEntity(team)
        summonerDao.insertSummoner(summonerEntity)
    }

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DataRepository(context)
            }
        }

        fun get(): DataRepository {
            return INSTANCE
                ?: throw IllegalStateException("Data Repository must be initialized")
        }
    }


    fun ChampionObj.toChampionEntity(): ChampionEntity {
        val moshi = Moshi.Builder().build()
        val statsAdapter = moshi.adapter(Stats::class.java)
        val statsJson = statsAdapter.toJson(this.stats)
        var name = this.name
        if (name.contains(" ") || name.contains("'")) {
            name.replace(" ", "")
                .replace("'", "")
        }
        var imageUrl = "http://ddragon.leagueoflegends.com/cdn/13.7.1/img/champion/${name}.png"
        if (name == "LeBlanc") {
            imageUrl = "http://ddragon.leagueoflegends.com/cdn/13.7.1/img/champion/Leblanc.png"
        }

        return ChampionEntity(
            key = key,
            name = name,
            title = title,
            image = imageUrl,
            stats = statsJson
        )
    }

    fun SummonerResponse.toSummonerEntity(team: Boolean): SummonerEntity {
        val imageUrl =
            "http://ddragon.leagueoflegends.com/cdn/13.7.1/img/profileicon/${this.profileIconId.toString()}.png"
//        val color = when (team){
//            team -> ContextCompat.getColor(context, R.color.my_team)
//            else -> ContextCompat.getColor(context, R.color.their_team)
//        }

        return SummonerEntity(
            Id = id,
            name = name,
            puuid = puuid,
            accountId = accountId,
            team = team,
            icon = imageUrl,
            summonerLevel = summonerLevel
        )
    }

    suspend fun MasteryObj.toMasteryEntity(
        id: Long,
        dataRepository: DataRepository
    ): MasteryEntity {
        val key = id.toString()
        val champion = dataRepository.getChampionById(key)
        return MasteryEntity(
            name = champion.name,
            image = champion.image,
            masteryLevel = masteryLevel,
            masteryPoint = masteryPoints
        )
    }

    private fun getApiUrl(): String {
        return when (Locale.getDefault().language) {
            "fr" -> "http://ddragon.leagueoflegends.com/cdn/13.7.1/data/fr_FR/"
            else -> "http://ddragon.leagueoflegends.com/cdn/13.7.1/data/en_US/"
        }
    }
}