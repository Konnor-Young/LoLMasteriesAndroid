package com.example.leaguelookup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SummonerInputViewModel : ViewModel() {
    private val dataRepository = DataRepository.get()

    private val _summonerNames: MutableStateFlow<List<SummonerEntity>> =
        MutableStateFlow(emptyList())
    val summonerNames: StateFlow<List<SummonerEntity>>
        get() = _summonerNames.asStateFlow()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun addSummoner(summonerName: String, isFriend: Boolean) {
        viewModelScope.launch {
            try {
                dataRepository.addSummoner(summonerName, isFriend)
            } catch (e: Exception) {
                _errorMessage.value = "Summoner Not Found"
            }
        }
    }

    init {
        viewModelScope.launch {
            dataRepository.getSummoners().collect() {
                _summonerNames.value = it
            }
        }
    }
}