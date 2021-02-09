package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
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

        electionsViewModel.dataLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                hideRecyclerShowLoading(binding.recyclerUpcomingElections, binding.progressUpcomingElections)
            } else {
                hideLoadingShowRecycler(binding.recyclerUpcomingElections, binding.progressUpcomingElections)
            }
        })

        electionsViewModel.dataLoadingSaved.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                hideRecyclerShowLoading(binding.recyclerSavedElections, binding.progressSavedElections)
            } else {
                hideLoadingShowRecycler(binding.recyclerSavedElections, binding.progressSavedElections)
            }
        })

        electionsViewModel.navigate.observe(viewLifecycleOwner, Observer {election ->
            if(election != null) {
                this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
                electionsViewModel.doneNavigating()
            }
        })

        setRecyclerAdapter()

        return binding.root
    }

    private fun hideLoadingShowRecycler(recyclerView: RecyclerView, progressBar: ProgressBar) {
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun hideRecyclerShowLoading(recyclerView: RecyclerView, progressBar: ProgressBar) {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun setRecyclerAdapter() {
        binding.recyclerUpcomingElections.apply {
            this.adapter = ElectionListAdapter(ElectionListener {
                electionsViewModel.navigate(it)
            })
        }

        binding.recyclerSavedElections.apply {
            this.adapter = ElectionListAdapter(ElectionListener {
                electionsViewModel.navigate(it)
            })
        }
    }

}