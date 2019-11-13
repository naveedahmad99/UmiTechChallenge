package com.urban.mobility.io.ui.trendings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urban.mobility.io.R
import com.urban.mobility.io.data.enums.Error
import com.urban.mobility.io.data.KEY_DATA
import com.urban.mobility.io.domain.Repository
import com.urban.mobility.io.ui.Action
import com.urban.mobility.io.ui.ActionManager
import com.urban.mobility.io.ui.ActionType
import com.urban.mobility.io.ui.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_trending_repos.*
import javax.inject.Inject

class TrendingReposFragment : BaseFragment() {

    companion object {
        fun newInstance(): TrendingReposFragment = TrendingReposFragment()
    }

    @Inject
    lateinit var viewModelFactory: TrendingReposViewModelFactory
    @Inject
    lateinit var actionManager: ActionManager

    private val trendingReposVM: TrendingReposViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TrendingReposViewModel::class.java]
    }

    private var loadMore = true
    private var refresh = true
    private var reposAdapter: ReposRecyclerAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trendingReposVM.reposLiveData.observe(this, Observer<MutableList<Repository>>(::onChanged))
        trendingReposVM.errorsLiveData.observe(this, Observer<Error>(::onChanged))
    }

    override fun onViewReady(view: View, savedInstanceState: Bundle?) {
        if (reposAdapter == null) {
            reposAdapter = ReposRecyclerAdapter(mutableListOf())
            reposAdapter?.onRepoItemClickListener = ::onRepoClicked
            loadRepos(true)
        }

        recycler_repos.adapter = reposAdapter
        recycler_repos.layoutManager = LinearLayoutManager(context)
        swipe_repos.setOnRefreshListener {
            loadRepos(true)
        }

        recycler_repos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager
                layoutManager?.let {
                    val last = (it as LinearLayoutManager).findLastVisibleItemPosition()
                    val count = recyclerView.adapter?.itemCount
                    if (loadMore && !swipe_repos.isRefreshing && last == count!! - 1) {
                        loadRepos(false)
                    }
                }
            }
        })
    }

    private fun loadRepos(reset: Boolean) {
        swipe_repos.isRefreshing = true
        if (reset)
            refresh = true
        trendingReposVM.loadTrendingRepos(reset)
    }

    private fun onChanged(data: Any?) {
        swipe_repos.isRefreshing = false
        when (data) {
            is Error -> {
                when (data) {
                    Error.SUCCESS -> {
                    }
                    Error.NO_MORE_DATA -> {
                        loadMore = false
                    }
                }
            }
            is MutableList<*> -> {
                if (refresh) {
                    reposAdapter!!.repositories.clear()
                    refresh = false
                }
                reposAdapter!!.repositories.addAll(data as Collection<Repository>)
                recycler_repos.adapter!!.notifyDataSetChanged()
            }
        }
    }

    private fun onRepoClicked(repository: Repository) {
        val data = Bundle()
        data.putParcelable(KEY_DATA, repository)
        actionManager.fire(Action(ActionType.ACTION_REPO, data))
    }

    override fun getLayoutId(): Int = R.layout.fragment_trending_repos
}