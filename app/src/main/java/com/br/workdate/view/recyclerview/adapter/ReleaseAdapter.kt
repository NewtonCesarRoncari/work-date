package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.databinding.ListItemReleaseBinding
import com.br.workdate.model.Release

class ReleaseAdapter(
    private val context: Context,
    private val releases: MutableList<Release>
) : RecyclerView.Adapter<ReleaseAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = DataBindingUtil.inflate<ListItemReleaseBinding>(
            inflater,
            R.layout.list_item_release,
            parent,
            false
        )
        return MyViewHolder(viewDataBinding)
    }

    override fun getItemCount() = releases.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(releases[position])
    }

    inner class MyViewHolder(private val viewDataBinding: ListItemReleaseBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        fun bind(release: Release) {
            viewDataBinding.release = release.makeReleaseForLayout(context)
        }
    }
}