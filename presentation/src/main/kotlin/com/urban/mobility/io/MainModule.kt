package com.urban.mobility.io

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {
    @Binds
    abstract fun bindMainPresenter(presenter: MainPresenter): MainContract.Presenter

    @Binds
    abstract fun bindMainFragment(view: MainFragment): MainContract.View

    /* FIXME MUST add below method to BindingModules */
    // @ContributesAndroidInjector(modules = [MainModule::class]) @FragmentScope abstract fun contributeMainFragmentInjector(): MainFragment
}
