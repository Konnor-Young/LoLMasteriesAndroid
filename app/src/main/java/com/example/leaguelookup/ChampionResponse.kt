package com.example.leaguelookup

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChampionResponse (
    val version: String,
    val data: Map<String, ChampionObj>
    )