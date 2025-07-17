package com.nativedevps.myapplication.utility

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Extension function to replace or add a Fragment in a container view.
 *
 * @param containerViewId The resource ID of the container view to replace or add the fragment.
 * @param fragment The fragment to be replaced or added.
 * @param addToBackStack True if the transaction should be added to the back stack; otherwise, false.
 * @param tag Optional tag name for the fragment.
 * @param allowStateLoss True to allow state loss; otherwise, false.
 */
fun FragmentManager.openFragment(
    @IdRes containerViewId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false,
    tag: String? = null,
    allowStateLoss: Boolean = false
) {
    val transaction = beginTransaction()
    transaction.replace(containerViewId, fragment, tag)
    if (addToBackStack) {
        transaction.addToBackStack(tag)
    }
    if (!isStateSaved) {
        transaction.commit()
    } else if (allowStateLoss) {
        transaction.commitAllowingStateLoss()
    }
}