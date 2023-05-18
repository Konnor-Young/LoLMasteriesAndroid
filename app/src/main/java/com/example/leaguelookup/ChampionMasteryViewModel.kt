package com.example.leaguelookup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChampionMasteryViewModel(summonerId: String) : ViewModel() {
    private val dataRepository = DataRepository.get()

    private val _championMastery: MutableStateFlow<List<MasteryEntity>> = MutableStateFlow(emptyList())
    val championMastery: StateFlow<List<MasteryEntity>>
        get() = _championMastery.asStateFlow()

    init {
        fetchChampionMastery(summonerId)
    }

    private fun fetchChampionMastery(summonerId: String) {
        viewModelScope.launch {
            val masteryResponse = dataRepository.fetchMastery(summonerId)
            _championMastery.value = masteryResponse
        }
    }
}

class ChampionMasteryViewModelFactory(
    private val summonerId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChampionMasteryViewModel(summonerId) as T
    }
}