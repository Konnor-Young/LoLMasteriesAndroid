package com.example.leaguelookup.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leaguelookup.SummonerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SummonerDao {
    @Query("SELECT * FROM SummonerEntity")
    fun getSummoners(): Flow<List<SummonerEntity>>

    @Query("SELECT * FROM SummonerEntity WHERE id = :id")
    suspend fun getSummoner(id: String): SummonerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummoner(summoner: SummonerEntity)

    @Query("DELETE FROM SummonerEntity WHERE id = :id")
    suspend fun deleteSummoner(id: String)
}