package com.urban.mobility.io.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kyle.githubapi.api.GithubService
import com.example.kyle.githubapi.login.AccessToken
import com.urban.mobility.io.R
import com.urban.mobility.io.data.*
import com.urban.mobility.io.utils.SharedPreferenceHelper
import com.urban.mobility.io.utils.showToast
import com.urban.mobility.io.utils.verifyAvailableNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LaunchViewModel : ViewModel() {

    val TAG = LaunchViewModel::class.java.simpleName

    fun checkIfLoggedIn(activity: Activity) {
        if (verifyAvailableNetwork(activity)) {
            if (SharedPreferenceHelper.getSharedPreferenceBoolean(
                    activity,
                    PREFS_LOGGED_IN,
                    false
                )
            ) {
                ACCESS_TOKEN =
                    SharedPreferenceHelper.getSharedPreferenceString(activity, PREFS_TOKEN, "")!!
                TOKEN_TYPE = SharedPreferenceHelper.getSharedPreferenceString(
                    activity,
                    PREFS_TOKEN_TYPE,
                    ""
                )!!
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finish()
            }
        } else
            showToast(activity, activity.getString(R.string.no_internet))
    }

    fun dealWithOauthResponse(activity: Activity, intent: Intent, redirectUri: String) {
        val uri: Uri = intent.data!!
        if (uri.toString().startsWith(redirectUri)) {
            val code: String = uri.getQueryParameter("code")!!
            val accessTokenCall = GithubService().getAccessToken(CLIENT_ID, CLIENT_SECRET, code)
            accessTokenCall.enqueue(object : Callback<AccessToken> {
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    Log.e(TAG, t.message, t)
                }

                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                    if (response.isSuccessful) {
                        val token = response.body()!!
                        Log.i(TAG, response.body()!!.accessToken)
                        SharedPreferenceHelper.setSharedPreferenceString(
                            activity,
                            PREFS_TOKEN, token.accessToken
                        )
                        SharedPreferenceHelper.setSharedPreferenceString(
                            activity,
                            PREFS_TOKEN_TYPE, response.body()!!.tokenType
                        )
                        SharedPreferenceHelper.setSharedPreferenceBoolean(
                            activity,
                            PREFS_LOGGED_IN, true
                        )
                        ACCESS_TOKEN = token.accessToken
                        TOKEN_TYPE = token.tokenType
                        activity.startActivity(Intent(activity, MainActivity::class.java))
                        activity.finish()
                    } else {
                        Log.e(TAG, "Error: ${response.code()} ${response.message()}")
                    }
                }
            })
        }
    }
}