package com.urban.mobility.io

import javax.inject.Inject

class MainPresenter @Inject constructor(/* add private val here. Exp: UseCase,View,.. */) :
    MainContract.Presenter {
    override fun dispose() {
        /* TODO dispose UseCase here */
    }
}
