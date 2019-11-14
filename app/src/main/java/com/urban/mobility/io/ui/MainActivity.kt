package com.urban.mobility.io.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.urban.mobility.io.R
import com.urban.mobility.io.ui.repo.RepoDetailsFragment
import com.urban.mobility.io.ui.trendings.TrendingReposFragment
import com.urban.mobility.io.utils.showToast
import com.urban.mobility.io.utils.verifyAvailableNetwork
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var actionManager: ActionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        actionManager.onActionListener = ::fireAction

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            actionManager.fire(Action(ActionType.ACTION_TRENDING_REPOS))
        }
    }

    private fun fireAction(action: Action) {
        if (verifyAvailableNetwork(this)) {
            when (action.type) {
                ActionType.UNKNOWN -> Log.w(javaClass.simpleName, "Unknown Action Fired!")
                ActionType.ACTION_TRENDING_REPOS -> transition(
                    TrendingReposFragment.newInstance(),
                    replace = false
                )
                ActionType.ACTION_REPO -> transition(RepoDetailsFragment.newInstance(action.data))
            }
        } else
            showToast(this, getString(R.string.no_internet))
    }

    private fun transition(fragment: BaseFragment, keepCurrent: Boolean = true,
                           replace: Boolean = true) {
        if (!keepCurrent && supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }

        val transaction = supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)

        if (replace) {
            transaction
                    .replace(R.id.container, fragment)
                    .addToBackStack(fragment.javaClass.simpleName)
        } else {
            transaction.add(R.id.container, fragment)
        }

        transaction.commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            fragmentDispatchingAndroidInjector
}
