package com.example.leaguelookup

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SummonerEntity(
    @PrimaryKey val Id: String,
    val name: String,
    val puuid: String,
    val accountId: String,
    val team: Boolean,
    val icon: String,
    val summonerLevel: Int
)