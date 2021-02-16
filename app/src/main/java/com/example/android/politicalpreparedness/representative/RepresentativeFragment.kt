package com.example.android.politicalpreparedness.representative

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

class RepresentativeFragment : Fragment() {

    private lateinit var binding: FragmentRepresentativeBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val permissionList = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    companion object {
        private const val LOCATION_REQUEST_ID = 0
    }

    private val representativeViewModel: RepresentativeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_representative, container, false)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.lifecycleOwner = this
        binding.viewModel = representativeViewModel


        setRepresentativeAdapter()

        binding.buttonLocation.setOnClickListener {
            if(checkLocationPermissions()) {
                getLocation()
            }
        }

        binding.buttonSearch.setOnClickListener {
            representativeViewModel.searchRepresentatives()
        }

        binding.state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                representativeViewModel.address.value?.state = binding.state.selectedItem as String
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                representativeViewModel.address.value?.state = binding.state.selectedItem as String
            }

        }

        return binding.root
    }

    private fun setRepresentativeAdapter() {
       binding.recyclerRepresentatives.apply {
           adapter = RepresentativeListAdapter()
       }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_REQUEST_ID) {

            if(checkLocationPermissions()) {
                getLocation()
            } else {
                return
            }
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return if (isPermissionGranted()) {
            true
        } else {
            requestPermission()
            true
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), permissionList, LOCATION_REQUEST_ID)
    }

    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            val address = geoCodeLocation(location)
            representativeViewModel.getGeoAddress(address)
        }
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

   private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}