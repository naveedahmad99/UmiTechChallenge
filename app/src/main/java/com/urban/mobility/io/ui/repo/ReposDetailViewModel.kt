package com.urban.mobility.io.ui.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.urban.mobility.io.data.enums.Error
import com.urban.mobility.io.data.interfaces.ISchedulersProvider
import com.urban.mobility.io.domain.Repository
import com.urban.mobility.io.domain.RepositoryContract
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


class ReposDetailViewModel(
    private val repository: RepositoryContract.IRepoDetailReposRepository,
    private val schedulers: ISchedulersProvider
) : ViewModel() {
    val errorsLiveData: LiveData<Error> by lazy {
        MutableLiveData<Error>()
    }
    val reposLiveData: LiveData<Repository> by lazy {
        MutableLiveData<Repository>()
    }

    @SuppressLint("CheckResult")
    fun loadTrendingRepos(ownerName: String, repoName: String) {
        repository
            .loadRepoDetail(ownerName, repoName)
            .observeOn(schedulers.ui())
            .subscribe({
                //                    (errorsLiveData as MutableLiveData<*>).value = Error.SUCCESS
                (reposLiveData as MutableLiveData<*>).value = it
            }, { error ->
                (errorsLiveData as MutableLiveData<*>).value = when (error) {
                    is HttpException -> {
                        if (error.code() == 422) {
                            Error.NO_MORE_DATA
                        } else {
                            Error.UNKNOWN
                        }
                    }
                    is SocketTimeoutException -> Error.TIMEOUT
                    is IOException -> Error.DISCONNECTED
                    else -> Error.UNKNOWN
                }
            })
    }
}