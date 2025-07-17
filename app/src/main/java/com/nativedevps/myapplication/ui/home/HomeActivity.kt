package com.nativedevps.myapplication.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.google.errorprone.annotations.Keep
import com.nativedevps.arch.R
import com.nativedevps.arch.databinding.ActivityHomeBinding
import com.nativedevps.base.NativeDevpsBaseActivity
import com.nativedevps.support.base.fcm.NativeDevelopersFCMService.Companion.CONST_NOTIFICATION_LINK_URL
import com.nativedevps.support.custom_views.dialogs.LoaderDialog
import com.nativedevps.support.utility.date_time_utility.MillisecondUtility.now
import com.nativedevps.support.utility.date_time_utility.getStringDateFromMillis
import com.nativedevps.support.utility.debugging.Log
import com.nativedevps.support.utility.networking.attemptOrNull
import com.nativedevps.support.utility.text.getVersionName
import com.nativedevps.support.utility.threading.runOnAsyncThread
import com.nativedevps.ui.about.dialog.AboutDialog
import com.nativedevps.ui.contact_us.ContactUsActivity
import dagger.hilt.android.AndroidEntryPoint
import io.karn.notify.Notify
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeActivity : NativeDevpsBaseActivity<ActivityHomeBinding, HomeViewModel>(
    R.layout.activity_home,
    HomeViewModel::class.java
) {

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)

        checkNotificationPayload(savedInstanceState)
        initNotifications()
        initListener()
    }

    private fun checkNotificationPayload(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            return
        }
        Log.v("checkNotificationPayload", "LinkUrl: ${intent.extras?.getString(CONST_NOTIFICATION_LINK_URL)}")

        val linkUrl = intent.extras?.getString(CONST_NOTIFICATION_LINK_URL) ?: return
        val notificationNavigation = NotificationNavigation(linkUrl)
        notificationNavigation.navigatingPath?.let { navigatingPath ->
            when (navigatingPath) {
                is NotificationNavigation.ContactUsNavigationPath -> {
                    if (navigatingPath.isRefreshRequired) {
                        ContactUsActivity.startSession(
                            this@HomeActivity,
                            viewModel
                        )
                    }
                }
            }
        } ?: run {
            Log.e("HomeActivity", "No valid navigation path found in notification")
        }
    }

    private fun initListener() = with(binding) {
        informationButton.setOnClickListener {
            showInformation("Some information ${getStringDateFromMillis(now)}")
        }

        aboutButton.setOnClickListener {
            AboutDialog.build(
                this@HomeActivity
            )
        }
        showLoader.setOnClickListener {
            viewModel.showProgressDialog("meow meow", cancelable = true)
            runOnAsyncThread {
                delay(10 * 1000)
                viewModel.hideProgressDialog()
            }
        }
        showDialog.setOnClickListener {
            showListDialog().also { dialog ->
                dialog.checkable = true
                dialog.setList(listOf("One", "Two", "Three", "Four", "Five"))
                dialog.hasPositiveButton = true
                dialog.onActionButton {
                    if (it) {
                        Log.e("itemModels", "itemModels: ${dialog.checkedList}")
                    }
                    dialog.dismiss()
                }
            }
        }
        showListDialog.setOnClickListener {
            showListDialog().also { dialog ->
                dialog.setList(listOf("One", "Two", "Three", "Four", "Five"))
                dialog.hasPositiveButton = true
                dialog.onItemsSelected { itemModels, longPress ->

                    itemModels
                }
                dialog.onActionButton {
                    if (it) {
                        Log.e("itemModels", "itemModels: ${dialog.checkedList}")
                    }
                    dialog.dismiss()
                }
            }
        }
        initialApisButton.setOnClickListener {
            viewModel.mockCallInitialApis()
        }
        notificationButton.setOnClickListener {
            showNotification()
        }
    }

    private fun showNotification() {
        Notify.with(this).header {
            this.icon = R.drawable.ic_launcher_foreground
        }.apply {
            asBigText {
                this.title = "some sample title"
                this.text =
                    "This is a sample notification body text that is used to demonstrate the big text notification style in Android. It can be quite long and will be truncated if it exceeds the limit."
                this.expandedText =
                    "This is the bigText that will be shown when the notification is expanded. It can contain more details about the notification, such as additional information or context."
            }.alerting("default-channel") {
                lockScreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
                channelImportance = Notify.IMPORTANCE_MAX
                showBadge = true
            }
            show()
        }
    }

    override fun getLoaderConstraints(): LoaderDialog.LoaderConstraints {
        return LoaderDialog.LoaderConstraints(
            lottieFile = R.raw.lottie_loader_elephant,
            cardBackgroundColor = R.color.colorPrimary
        )
    }

    override fun getAppVersion(): String? {
        return getVersionName()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent) // important if you're using getIntent() later
        checkNotificationPayload(null)
    }

    override fun onDestroy() {
        viewModel.analyticAppExit()
        super.onDestroy()
    }
}

@Keep
class NotificationNavigation(
    url: String,
    private var parsedUri: Uri = Uri.parse(url)
) {

    val navigatingPath: NavigatingPath?
        get() {
            return attemptOrNull {
                NavigatingPathType.valueOf(
                    parsedUri.pathSegments.lastOrNull() ?: error("uri path was missing")
                ).let {
                    it.navigatingPath.apply {
                        this.uri = parsedUri
                    }
                }
            }
        }

    @Keep
    sealed class NavigatingPath {
        abstract val path: String
        abstract var uri: Uri?
    }

    @Keep
    data object ContactUsNavigationPath : NavigatingPath() {
        override val path: String
            get() = "contact_us"
        override var uri: Uri? = null

        val isRefreshRequired: Boolean
            get() {
                return uri?.getBooleanQueryParameter("refresh_required", false) ?: false
            }
    }

    @Keep
    enum class NavigatingPathType(val navigatingPath: NavigatingPath) {
        `contact_us`(ContactUsNavigationPath)
    }
}