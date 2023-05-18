package com.example.leaguelookup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.leaguelookup.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding. Is the view visible?"
        }
    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.miniMap.load("http://ddragon.leagueoflegends.com/cdn/6.8.1/img/map/map11.png"){
            size(1800, 1800)
        }
        binding.clearOnClick.setOnClickListener{
            binding.mapDrawing.clearDraw()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.mapDrawing.setOnTouchListener { _, event ->
            binding.mapDrawing.onTouchEvent(event)
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}