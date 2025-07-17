package com.nativedevps.myapplication.ui.main

import android.app.Application
import com.nativedevps.base.NativeDevpsBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : NativeDevpsBaseViewModel(application) {

    override fun onCreate() {
        //noop
    }
}