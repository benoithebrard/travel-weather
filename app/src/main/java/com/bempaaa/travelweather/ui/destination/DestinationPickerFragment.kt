package com.bempaaa.travelweather.ui.destination

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bempaaa.travelweather.NavigationListener
import com.bempaaa.travelweather.R
import kotlinx.android.synthetic.main.fragment_destination_picker.*

class DestinationPickerFragment : Fragment(R.layout.fragment_destination_picker) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            (requireActivity() as? NavigationListener)?.onBackClicked()
        }
    }
}