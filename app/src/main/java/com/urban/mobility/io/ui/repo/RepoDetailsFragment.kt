package com.urban.mobility.io.ui.repo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.urban.mobility.io.R
import com.urban.mobility.io.data.DATE_FORMAT
import com.urban.mobility.io.data.KEY_DATA
import com.urban.mobility.io.data.PREFS_LOGGED_IN
import com.urban.mobility.io.data.REFRESH_TIME_IN_MILLISECONDS
import com.urban.mobility.io.data.enums.Error
import com.urban.mobility.io.domain.Repository
import com.urban.mobility.io.ui.BaseFragment
import com.urban.mobility.io.ui.LaunchActivity
import com.urban.mobility.io.utils.SharedPreferenceHelper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_repo_details.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RepoDetailsFragment : BaseFragment() {

    private val mTAG = RepoDetailsFragment::class.java.simpleName
    var repository: Repository? = null

    companion object {
        fun newInstance(repoBundle: Bundle?): RepoDetailsFragment {
            val fragment = RepoDetailsFragment()
            fragment.arguments = repoBundle
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: RepoDetailViewModelFactory

    private val reposDetailVM: ReposDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ReposDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reposDetailVM.reposLiveData.observe(this, Observer<Repository>(::onChanged))
        reposDetailVM.errorsLiveData.observe(this, Observer<Error>(::onChanged))
    }

    private fun onChanged(data: Any?) {
        when (data) {
            is Error -> {
                when (data) {
                    Error.SUCCESS -> {
                    }
                    else -> {
                        Log.d(mTAG, data.name)
                        SharedPreferenceHelper.setSharedPreferenceBoolean(
                            this.activity!!,
                            PREFS_LOGGED_IN, false
                        )
                        startActivity(Intent(this.activity!!, LaunchActivity::class.java))
                        refreshData()
                    }
                }
            }
            is Repository -> {
                Log.d(mTAG, "Posted Message")
                if (data.updatedAt != repository!!.updatedAt || data.stars != repository!!.stars || data.forks != repository!!.forks || data.repoDescription != repository!!.repoDescription) {
                    bindData((data))
                }
                refreshData()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Repository>(KEY_DATA)?.let { repo ->
            bindData(repo)
            refreshData()
        }
    }

    private fun bindData(repo: Repository) {
        repository = repo
        Picasso
            .get()
            .load(Uri.parse(repo.owner.avatar))
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .centerCrop()
            .fit()
            .into(image_repo_owner)
        text_repo_title.text = repo.name
        text_author.text = repo.owner.username
        text_repo_description.text = repo.repoDescription
        text_stars.text = repo.stars.toString()
        text_forks.text = repo.forks.toString()

        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val updatedAt: Date = dateFormat.parse(repo.updatedAt)

        val text = DateUtils.getRelativeDateTimeString(
            context, updatedAt.time, DateUtils.SECOND_IN_MILLIS,
            DateUtils.YEAR_IN_MILLIS, 0
        )
        text_updated_at.text = getString(R.string.prefix_updated, text)
    }

    private fun refreshData() {
        handler.sendEmptyMessageDelayed(0, REFRESH_TIME_IN_MILLISECONDS)
    }

    override fun getLayoutId(): Int = R.layout.fragment_repo_details

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            Log.d(mTAG, "handle message")
            reposDetailVM.loadTrendingRepos(repository!!.owner.username, repository!!.name)
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}