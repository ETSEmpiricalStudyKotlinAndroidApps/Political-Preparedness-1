package com.example.android.politicalpreparedness.election.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election

@BindingAdapter("elections")
fun loadElections(recyclerView: RecyclerView, elections: List<Election>?) {
    elections?.let {
        (recyclerView.adapter as ElectionListAdapter).submitList(elections)
    }
}

@BindingAdapter("onClick")
fun setOnCLick(view: View, url: String?) {
    view.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }
}