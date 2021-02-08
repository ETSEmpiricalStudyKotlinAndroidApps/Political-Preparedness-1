package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.application.MyApplication
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener
import timber.log.Timber

class ElectionsFragment : Fragment() {

    private lateinit var binding: FragmentElectionBinding

    private val electionsViewModel: ElectionsViewModel by viewModels() {
        ElectionsViewModelFactory((requireContext().applicationContext as MyApplication).getDataSource())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_election, container, false)
        binding.viewModel = electionsViewModel
        binding.lifecycleOwner = this
        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        electionsViewModel.dataLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if(isLoading) {
                hideRecyclerShowLoading()
            } else {
                hideLoadingShowRecycler()
            }
        })


        setRecyclerAdapter()

        return binding.root
    }

    private fun hideLoadingShowRecycler() {
        binding.recyclerUpcomingElections.visibility = View.VISIBLE
        binding.progressUpcomingEvents.visibility = View.GONE
    }

    private fun hideRecyclerShowLoading() {
        binding.recyclerUpcomingElections.visibility = View.GONE
        binding.progressUpcomingEvents.visibility = View.VISIBLE
    }

    private fun setRecyclerAdapter() {
            binding.recyclerUpcomingElections.apply {
                this.adapter = ElectionListAdapter(ElectionListener {
                    Toast.makeText(requireActivity(), "Test", Toast.LENGTH_SHORT).show()
                })
        }
    }

    //TODO: Refresh adapters when fragment loads

}