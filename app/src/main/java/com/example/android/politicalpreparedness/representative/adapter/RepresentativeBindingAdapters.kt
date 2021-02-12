package com.example.android.politicalpreparedness.representative.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.representative.model.Representative

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    src?.let {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context)
                .load(uri)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(view)
    }
}

@BindingAdapter("representatives")
fun loadRepresentatives(recyclerView: RecyclerView, representatives: List<Representative>?) {
    representatives?.let {
        (recyclerView.adapter as RepresentativeListAdapter).submitList(representatives)
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

@BindingAdapter("onClickIcon")
fun setOnCLick(view: View, url: String?) {
    view.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        view.context.startActivity(intent)
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
    return adapter as ArrayAdapter<T>
}
