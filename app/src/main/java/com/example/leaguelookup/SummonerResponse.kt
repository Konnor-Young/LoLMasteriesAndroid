package com.example.leaguelookup

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SummonerResponse(
    val id: String,
    val accountId: String,
    val puuid: String,
    val name: String,
    val profileIconId: Int,
    val summonerLevel: Int
)
