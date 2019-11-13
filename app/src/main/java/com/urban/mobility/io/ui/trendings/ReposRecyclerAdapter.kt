package com.urban.mobility.io.ui.trendings

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.urban.mobility.io.domain.Repository
import com.urban.mobility.io.BR
import com.urban.mobility.io.R

class ReposRecyclerAdapter(val repositories: MutableList<Repository>)
    : RecyclerView.Adapter<DataBindingViewHolder>() {
    var onRepoItemClickListener: ((Repository) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DataBindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_item, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun getItemCount(): Int = repositories.size

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bindVariable(BR.repository, repositories[position])
        holder.itemView.setOnClickListener { _ ->
            onRepoItemClickListener?.let { it(repositories[holder.adapterPosition]) }
        }
    }

}