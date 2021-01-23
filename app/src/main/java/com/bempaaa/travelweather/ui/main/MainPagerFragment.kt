package com.bempaaa.travelweather.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bempaaa.travelweather.NavigationListener
import com.bempaaa.travelweather.R
import kotlinx.android.synthetic.main.fragment_pager.*

class MainPagerFragment : Fragment(R.layout.fragment_pager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager.apply {
            adapter = SectionsPagerAdapter(requireContext(), childFragmentManager)
            pager_tabs.setupWithViewPager(this)
        }

        bottom_fab.setOnClickListener {
            (requireActivity() as? NavigationListener)?.onFabClicked()
        }
    }
}