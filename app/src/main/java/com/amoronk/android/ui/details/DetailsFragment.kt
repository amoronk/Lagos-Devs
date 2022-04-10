package com.amoronk.android.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.amoronk.android.R
import com.amoronk.android.data.local.model.Devs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    lateinit var updatedDev:Devs.DevEntity

    val viewModel: DetailsViewModel by viewModels()

    private val devId by lazy { arguments.let {
        DetailsFragmentArgs.fromBundle(it!!).id
    } }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchDevData(devId.toString())

        observeDataSet()
    }


    private fun observeDataSet() {

        viewModel.devDataResult.observe(viewLifecycleOwner) {
            if (it != null) {
                setUPView(it)
            }
        }
    }

    private fun setUPView(dev: Devs.DevEntity) {

        updatedDev = Devs.DevEntity(
            dev.id,
            dev.devId,
            dev.userName,
            dev.avatarUrl,
            dev.url,
            dev.score,
            dev.isFav
        )


        profileImage.load(dev.avatarUrl){
            placeholder(R.drawable.ic_default_image)
        }

        userNameVal.text = dev.userName

        urlVal.text = dev.url

        scoreVal.text = dev.score.toString()

        if (dev.isFav) {
            favImageButton.setImageResource(R.drawable.ic_fav)
        }

        favImageButton.setOnClickListener {
            val fav: Boolean

            if (updatedDev.isFav) {

                fav = false
                favImageButton.setImageResource(R.drawable.ic_fav_outline)

            } else {
                fav = true
                favImageButton.setImageResource(R.drawable.ic_fav)
            }

             updatedDev = Devs.DevEntity(
                dev.id,
                dev.devId,
                dev.userName,
                dev.avatarUrl,
                dev.url,
                dev.score,
                isFav = fav
            )


            viewModel.updateLagosDev(updatedDev)
        }

    }


}