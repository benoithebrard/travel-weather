package com.bempaaa.travelweather.ui.destination

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
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

        motion_layout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout, p1: Int, p2: Int) {
            }

            override fun onTransitionChange(p0: MotionLayout, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout, p1: Int) {
                if (p0.progress == 1f) {
                    Toast.makeText(requireContext(), "Plane has landed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout, p1: Int, p2: Boolean, p3: Float) {
            }
        })
    }
}