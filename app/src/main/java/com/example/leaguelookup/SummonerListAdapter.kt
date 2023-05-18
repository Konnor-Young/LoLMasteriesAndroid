package com.example.leaguelookup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.leaguelookup.databinding.ListItemSummonerBinding

class SummonerHolder(
    private val binding: ListItemSummonerBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(summoner: SummonerEntity, onSummonerClicked: (summonerId: String, summonerName: String) -> Unit) {
        binding.summonerName.text = summoner.name
        binding.summonerIcon.load(summoner.icon)
        binding.root.setOnClickListener{
            onSummonerClicked(summoner.Id, summoner.name)
        }
    }
}
class SummonerListAdapter(
    private val summoners: List<SummonerEntity>,
    private val onSummonerClicked: (summonerId: String, summonerName: String) -> Unit
    ) : RecyclerView.Adapter<SummonerHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SummonerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSummonerBinding.inflate(inflater, parent, false)
        return SummonerHolder(binding)
    }

    override fun onBindViewHolder(holder: SummonerHolder, position: Int) {
        val summoner = summoners[position]
        holder.bind(summoner, onSummonerClicked)
    }

    override fun getItemCount() = summoners.size
}