package com.example.leaguelookup.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leaguelookup.ChampionEntity
import com.example.leaguelookup.SummonerEntity
import com.example.leaguelookup.VersionEntity

@Database(entities = [ChampionEntity::class, VersionEntity::class, SummonerEntity::class], version = 3, exportSchema = false)
abstract class ChampionDatabase : RoomDatabase() {
    abstract fun championDao(): ChampionDao
    abstract fun summonerDao(): SummonerDao

    companion object {
        @Volatile
        private var INSTANCE: ChampionDatabase? = null

        fun getDatabase(context: Context): ChampionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChampionDatabase::class.java,
                    "champion_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
