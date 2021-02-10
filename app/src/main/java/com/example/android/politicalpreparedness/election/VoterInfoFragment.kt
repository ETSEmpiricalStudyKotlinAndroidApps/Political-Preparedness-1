package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_voter_info, container, false)

        val electionId = args.argElectionId
        voterInfoViewModel.retrieveVoterInfoResponse(electionId)

        binding.viewModel = voterInfoViewModel
        binding.lifecycleOwner = this


        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        return binding.root

    }

    //TODO: Create method to load URL intents

}