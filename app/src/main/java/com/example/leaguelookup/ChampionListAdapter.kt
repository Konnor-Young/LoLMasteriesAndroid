package com.example.leaguelookup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.leaguelookup.databinding.ListItemMasteryBinding

class ChampionHolder(
    private val binding: ListItemMasteryBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(masteryEntity: MasteryEntity) {
        binding.iconChampion.load(masteryEntity.image)
        binding.nameChampion.text = masteryEntity.name
        binding.masteryLevel.text = masteryEntity.masteryLevel.toString()
        binding.masteryPoints.text = masteryEntity.masteryPoint.toString()
    }
}
class ChampionListAdapter (
    private val masteries: List<MasteryEntity>,
        ) : RecyclerView.Adapter<ChampionHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChampionHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMasteryBinding.inflate(inflater, parent, false)
        return ChampionHolder(binding)
    }

    override fun onBindViewHolder(holder: ChampionHolder, position: Int) {
        val mastery = masteries[position]
        holder.bind(mastery)
    }

    override fun getItemCount() = masteries.size
        }