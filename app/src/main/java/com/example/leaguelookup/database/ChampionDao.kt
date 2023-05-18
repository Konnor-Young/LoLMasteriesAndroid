package com.example.leaguelookup.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaguelookup.ChampionEntity
import com.example.leaguelookup.VersionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ChampionDao {
    @Query("SELECT * FROM champions")
    fun getChampions(): Flow<List<ChampionEntity>>

    @Query("SELECT * FROM champions WHERE `key`=(:key)")
    suspend fun getChampion(key: String): ChampionEntity

    @Query("SELECT * FROM champions WHERE `key` IN (:keys)")
    fun getChampionsList(keys: List<String>): Flow<List<ChampionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChampions(champions: List<ChampionEntity>)

    @Query("SELECT * FROM versions LIMIT 1")
    suspend fun getVersion(): VersionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVersion(version: VersionEntity)
}