package com.urban.mobility.io.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.urban.mobility.io.R
import com.urban.mobility.io.data.CLIENT_ID
import com.urban.mobility.io.data.REDIRECTION_URL
import com.urban.mobility.io.utils.showToast
import com.urban.mobility.io.utils.verifyAvailableNetwork


class LaunchActivity : AppCompatActivity() {

    private val redirectUri: String = REDIRECTION_URL
    private lateinit var mViewModel: LaunchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mViewModel = ViewModelProviders.of(this).get(LaunchViewModel::class.java)
        mViewModel.checkIfLoggedIn(this)
    }


    fun oauthOnClick(view: View) {
        if (verifyAvailableNetwork(this)) {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/login/oauth/authorize?client_id=$CLIENT_ID&scope=repo&redirect_uri=$redirectUri")
            )
            startActivity(intent)
        } else
            showToast(this, getString(R.string.no_internet))
    }

    override fun onResume() {
        super.onResume()

        if (intent != null && intent.data != null) {
            mViewModel.dealWithOauthResponse(this, intent, redirectUri)
        }
    }
}
