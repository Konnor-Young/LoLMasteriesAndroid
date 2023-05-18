package com.example.leaguelookup.api

import com.example.leaguelookup.SummonerResponse
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "RGAPI-2ed19305-8567-48db-b5d6-6d583dc0e678"

interface SummonerApi {
    @GET(
        "lol/summoner/v4/summoners/by-name/" +
                "{summonerName}" +
                "?api_key=$API_KEY")
    suspend fun fetchSummoner(@Path("summonerName") summonerName: String): SummonerResponse
}