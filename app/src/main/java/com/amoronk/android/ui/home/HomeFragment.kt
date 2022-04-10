package com.amoronk.android.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.amoronk.android.R
import com.amoronk.android.ui.home.adapter.DevListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
@ExperimentalPagingApi
class HomeFragment : Fragment(R.layout.fragment_home), ListAction {

    val viewModel: HomeViewModel by viewModels()

    private val mAdapter by lazy { DevListAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchDevs()

        initView()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().findViewById<View>(R.id.homeFavImageButton)
            ?.setOnClickListener {
                findNavController().navigate(R.id.action_home_to_fav)
            }
    }

    private fun initView() {
        setUpAdapter()
        observeDataSet()
    }

    private fun setUpAdapter() {

        devRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.addLoadStateListener { loadState ->
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                AlertDialog.Builder(view?.context)
                    .setTitle("Error Loading data")
                    .setMessage(it.error.localizedMessage)
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Retry") { _, _ ->
                        mAdapter.retry()
                    }
                    .show()
            }
        }

    }

    @SuppressLint("CheckResult")
    private fun observeDataSet() {

        viewModel.pagingDataResult.observe(viewLifecycleOwner, Observer {
            mAdapter.submitData(lifecycle, it)

        })

    }

    override fun onClicked(devID: Long) {

        val action = HomeFragmentDirections.actionHomeToDetails(devID)
        findNavController().navigate(action)


    }


}