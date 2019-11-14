package com.urban.mobility.io.ui.trendings

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.urban.mobility.io.R

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("srcCompat")
    fun setImageUrl(view: ImageView, url: String) {
        Picasso
                .get()
                .load(Uri.parse(url))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
                .centerCrop()
                .fit()
                .into(view)
    }

}