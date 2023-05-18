package com.example.leaguelookup.api

import com.example.leaguelookup.ChampionResponse
import retrofit2.http.GET
import java.util.*

interface ChampionApi {

    @GET("champion.json")
    suspend fun fetchChampions(): ChampionResponse
}