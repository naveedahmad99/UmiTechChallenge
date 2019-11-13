package com.urban.mobility.io.ui.trendings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.urban.mobility.io.data.interfaces.ISchedulersProvider
import com.urban.mobility.io.domain.RepositoryContract

@Suppress("UNCHECKED_CAST")
class TrendingReposViewModelFactory constructor(
        private val repository: RepositoryContract.ITrendingReposRepository,
        private val schedulers: ISchedulersProvider
) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrendingReposViewModel(repository, schedulers) as T
    }
}