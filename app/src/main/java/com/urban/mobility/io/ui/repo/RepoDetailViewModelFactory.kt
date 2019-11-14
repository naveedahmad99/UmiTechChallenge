package com.urban.mobility.io.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.urban.mobility.io.data.interfaces.ISchedulersProvider
import com.urban.mobility.io.domain.RepositoryContract

@Suppress("UNCHECKED_CAST")
class RepoDetailViewModelFactory constructor(
    private val repository: RepositoryContract.IRepoDetailReposRepository,
    private val schedulers: ISchedulersProvider
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReposDetailViewModel(repository, schedulers) as T
    }
}