package com.example.leaguelookup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leaguelookup.databinding.FragmentChampionMasteryBinding
import kotlinx.coroutines.launch

class ChampionMasteryFragment : Fragment() {
    private var _binding: FragmentChampionMasteryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access mastery binding"
        }

    private val args: ChampionMasteryFragmentArgs by navArgs()
    private val championMasteryViewModel: ChampionMasteryViewModel by viewModels {
        ChampionMasteryViewModelFactory(args.summonerId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChampionMasteryBinding.inflate(inflater, container, false)
        binding.summonerName.text = args.summonerName
//        binding.backButton.setOnClickListener {
//            findNavController().navigateUp()
//        }
        binding.masteryRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                championMasteryViewModel.championMastery.collect { masteryList ->
                    binding.masteryRecyclerView.adapter = ChampionListAdapter(masteryList)
                }
            }
        }
    }
}