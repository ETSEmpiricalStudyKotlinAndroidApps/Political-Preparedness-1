package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ViewHolderElectionBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(private val clickListener: ElectionListener): ListAdapter<Election, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
       val election = getItem(position)

        holder.bind(election)
        holder.itemView.setOnClickListener {
            clickListener.onClick(election)
        }
    }
}

class ElectionViewHolder private constructor(private val binding: ViewHolderElectionBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(election: Election) {
        binding.election = election
        binding.executePendingBindings()
    }


    companion object {
        fun from(parent: ViewGroup): ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ViewHolderElectionBinding.inflate(layoutInflater, parent, false)

            return ElectionViewHolder(binding)
        }
    }
}

class ElectionDiffCallback : DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}

class ElectionListener(private val clickListener: (election: Election) -> Unit) {
    fun onClick(election: Election) = clickListener(election)
}