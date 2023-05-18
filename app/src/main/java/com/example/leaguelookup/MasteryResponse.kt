package com.example.leaguelookup

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MasteryResponse (
    val mastery: List<MasteryObj>
        )