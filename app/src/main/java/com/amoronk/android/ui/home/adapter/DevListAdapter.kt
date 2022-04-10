package com.amoronk.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.amoronk.android.R
import com.amoronk.android.data.local.model.Devs
import com.amoronk.android.ui.home.ListAction
import kotlinx.android.synthetic.main.item_dev.view.*
import java.util.*

class DevListAdapter(val action: ListAction): PagingDataAdapter<Devs.DevEntity, DevListAdapter.DevListViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: DevListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DevListViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_dev, parent, false))

    inner class DevListViewHolder(view: View):RecyclerView.ViewHolder(view){

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

            dev_layout.setOnClickListener {
                action.onClicked(devEntity.id)
            }

        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Devs.DevEntity>() {
            override fun areItemsTheSame(oldItem: Devs.DevEntity, newItem: Devs.DevEntity): Boolean {
                return oldItem.devId == newItem.devId
            }

            override fun areContentsTheSame(oldItem: Devs.DevEntity, newItem: Devs.DevEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}