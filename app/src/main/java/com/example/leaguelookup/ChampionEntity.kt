package com.example.leaguelookup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "champions")
data class ChampionEntity(
    @PrimaryKey val key: String,
    val name: String,
    val title: String,
    val image: String,
    val stats: String
)
