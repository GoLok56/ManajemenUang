package io.github.golok56.manajemenuang.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.viewmodels.MainViewModel
import io.github.golok56.manajemenuang.ui.base.BaseActivity
import io.github.golok56.manajemenuang.views.adapter.PageAdapter
import io.github.golok56.manajemenuang.views.fragment.dialog.NewUserDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), NewUserDialog.Communicator {

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get rid the shadow below ActionBar
        supportActionBar?.elevation = 0f

        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (!mViewModel.hasLogin()) {
            showDialog()
        } else {
            initView()
        }
    }

    override fun onBackPressed() {
        if (mViewModel.backPressedDuration + TIME_DELAY > System.currentTimeMillis()) {
            finish()
            super.onBackPressed()
        } else {
            showToast(mViewModel.confirmText)
        }

        mViewModel.backPressedDuration = System.currentTimeMillis()
    }

    override fun onPositive() {
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_main)
        vpMainActivity.adapter = PageAdapter(supportFragmentManager)
        tlMainActivity.setupWithViewPager(vpMainActivity)
    }

    private fun showDialog() {
        NewUserDialog().show(supportFragmentManager, DIALOG_TAG)
    }

    companion object {
        private val TIME_DELAY = 2000
        private val DIALOG_TAG = "dialog_tag"

        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

}
