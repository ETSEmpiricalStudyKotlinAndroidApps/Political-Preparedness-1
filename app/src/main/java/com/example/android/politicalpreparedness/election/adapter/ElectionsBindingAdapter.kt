package com.example.android.politicalpreparedness.election.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election

@BindingAdapter("elections")
fun loadElections(recyclerView: RecyclerView, elections: List<Election>?) {
    elections?.let {
        (recyclerView.adapter as ElectionListAdapter).submitList(elections)
    }
}