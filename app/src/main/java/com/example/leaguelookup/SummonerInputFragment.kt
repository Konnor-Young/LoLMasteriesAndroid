package com.example.leaguelookup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leaguelookup.databinding.FragmentSummonerInputBinding
import kotlinx.coroutines.launch

class SummonerInputFragment : Fragment() {
    private var _binding: FragmentSummonerInputBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding. Is the view visible?"
        }
    private val summonerInputViewModel: SummonerInputViewModel by viewModels()
    var selectedRegion: String? = DataRepository.get().region
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummonerInputBinding.inflate(inflater, container, false)
        binding.inputRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.addSummoner.setOnClickListener{
            val newName = binding.addSummonerName.text.toString()

            val isFriend = binding.friendCheck.isChecked

            summonerInputViewModel.addSummoner(newName, isFriend)
        }
        binding.seeMap.setOnClickListener{
            findNavController().navigate(SummonerInputFragmentDirections.showMap())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        summonerInputViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showToast(message)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                summonerInputViewModel.summonerNames.collect { summoners ->
                    binding.inputRecyclerView.adapter =
                        SummonerListAdapter(summoners) { summonerId, summonerName ->
                            Log.d("Summoner ID", "On Click: $summonerId")
                            val action = SummonerInputFragmentDirections.showChampionMastery(summonerId, summonerName)
                            Log.d("SummonerInputFragment", "action: $action")
                            findNavController().navigate(action)
                        }
                }
            }
        }

        val spinner: Spinner = binding.regionSpinner
        val adapter = SpinnerAdapter(resources.getStringArray(R.array.regions_array).toList())
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = adapter.getItem(position)
                selectedRegion = when (selectedItem) {
                    "NA1" -> "na1"
                    "EUW1" -> "euw1"
                    "EUN1" -> "eun1"
                    "KR" -> "kr"
                    "OC1" -> "oc1"
                    "JP1" -> "jp1"
                    "LA1" -> "la1"
                    "LA2" -> "la2"
                    "BR1" -> "br1"
                    else -> "na1"
                }
                DataRepository.get().setSelectedRegion(selectedRegion.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRegion = "na1"
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}