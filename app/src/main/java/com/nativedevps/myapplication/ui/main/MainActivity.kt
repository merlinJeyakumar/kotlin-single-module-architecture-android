package com.nativedevps.myapplication.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.nativedevps.arch.R
import com.nativedevps.arch.databinding.ActivityMainBinding
import com.nativedevps.base.NativeDevpsBaseActivity
import com.nativedevps.support.base_class.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : NativeDevpsBaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main,
    MainViewModel::class.java
) {

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)

        initView()
        initListener()
        initData()
        initPreview()
    }

    private fun initListener() {
        //todo: initialize view listeners
    }

    private fun initView() = with(binding) {
        //todo: initialize listeners

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //todo: set drawables for night mode
    }


    private fun initData() = with(viewModel) {
        //todo: initial livedata/observer
    }


    private fun initPreview() = with(binding) {
        //todo: initial rendering to view
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}