package com.urban.mobility.io.ui.trendings

import androidx.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.urban.mobility.io.R
import com.squareup.picasso.Picasso

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("app:srcCompat")
    fun setImageUrl(view: ImageView, url: String) {
        Picasso
                .get()
                .load(Uri.parse(url))
                .placeholder(R.drawable.ic_octoface)
                .error(R.drawable.ic_octoface)
                .centerCrop()
                .fit()
                .into(view)
    }

}