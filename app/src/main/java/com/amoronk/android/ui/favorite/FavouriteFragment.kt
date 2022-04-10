package com.amoronk.android.ui.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.amoronk.android.R
import com.amoronk.android.ui.MainActivity
import com.amoronk.android.ui.home.adapter.FavDevAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    val viewModel: FavouriteViewModel by viewModels()

    private  val mAdapter by lazy { FavDevAdapter(arrayListOf()) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favDevRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        viewModel.fetchFavDevs(true)

        observerLisData()
    }

    private fun observerLisData() {

        viewModel.favDataResult.observe(viewLifecycleOwner) {

            if (it != null) {

                emptyState.visibility = View.GONE
                favDevRecyclerView.visibility = View.VISIBLE
                mAdapter.updateDevs(it)

            } else {
                emptyState.visibility = View.VISIBLE
                favDevRecyclerView.visibility = View.GONE
            }
        }
    }

}