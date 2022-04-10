package com.amoronk.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amoronk.android.R
import com.amoronk.android.data.local.model.Devs
import kotlinx.android.synthetic.main.item_dev.view.*
import java.util.*

class FavDevAdapter(val devs: ArrayList<Devs.DevEntity>) :
    RecyclerView.Adapter<FavDevAdapter.FavDevViewHolder>() {

    fun updateDevs(newDevs: List<Devs.DevEntity>) {
        devs.clear()
        devs.addAll(newDevs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavDevViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_dev, parent, false)
    )

    override fun onBindViewHolder(holder: FavDevViewHolder, position: Int) {
        holder.bind(devs[position])
    }

    override fun getItemCount() = devs.size

    inner class FavDevViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val username = view.name
        private val url = view.url
        private val profileImageView = view.image
        private val dev_layout = view.dev_item

        fun bind(devEntity: Devs.DevEntity) {
            username.text = devEntity.userName.uppercase(Locale.getDefault())
            url.text = devEntity.url

            profileImageView.load(devEntity.avatarUrl){
                placeholder(R.drawable.ic_default_image)
            }



        }
    }
}