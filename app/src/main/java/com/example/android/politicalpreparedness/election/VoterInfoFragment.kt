package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.application.MyApplication
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

class VoterInfoFragment : Fragment() {

    private lateinit var binding: FragmentVoterInfoBinding

    private val args: VoterInfoFragmentArgs by navArgs()

    private val voterInfoViewModel: VoterInfoViewModel by viewModels {
        VoterInfoViewModelFactory((requireContext().applicationContext as MyApplication).getDataSource())
    }

    private lateinit var button: Button

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voter_info, container, false)

        val electionId = args.argElectionId
        voterInfoViewModel.retrieveVoterInfoResponse(electionId)
        voterInfoViewModel.doesEntryExist(electionId)

        binding.viewModel = voterInfoViewModel
        binding.lifecycleOwner = this

        button = binding.buttonFollowElection


        checkButtonInitialState()
        setButtonListener()

        return binding.root
    }


    /**
     * Checks the button initial state to determine which text to show
     */
    private fun checkButtonInitialState() {
        voterInfoViewModel.entryExists.observe(viewLifecycleOwner, { exists ->
            if(exists) {
                button.text = getString(R.string.unfollow_election)
            } else {
                button.text = getString(R.string.follow_election)
            }
        })
    }

    /**
     * Sets listener for button and handles Button State
     */
    private fun setButtonListener() {
        val button = binding.buttonFollowElection

        binding.buttonFollowElection.setOnClickListener {
            if(button.text == getString(R.string.unfollow_election)) {
                voterInfoViewModel.removeElection()
                button.text = getString(R.string.follow_election)
            } else {
                voterInfoViewModel.saveElection()
                button.text = getString(R.string.unfollow_election)
            }
        }
    }
}