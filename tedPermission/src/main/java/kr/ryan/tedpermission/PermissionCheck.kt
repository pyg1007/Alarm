package kr.ryan.tedpermission

import android.content.Context
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

/**
 * Alarm
 * Class: PermissionCheck
 * Created by pyg10.
 * Created On 2021-08-20.
 * Description:
 */

fun Context.checkTedPermission(
    grant: () -> Unit,
    denied: () -> Unit,
    rationaleTitle: String?,
    rationaleMessage: String?,
    deniedTitle: String?,
    deniedMessage: String?,
    vararg permissions: String) {

    val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            grant()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            denied()
        }
    }

    TedPermission.with(this)
        .setPermissionListener(permissionListener)
        .setRationaleTitle(rationaleTitle)
        .setRationaleMessage(rationaleMessage)
        .setDeniedTitle(deniedTitle)
        .setDeniedMessage(deniedMessage)
        .setGotoSettingButton(true)
        .setPermissions(*permissions)
        .check()

}

fun Context.checkTedPermission(
    grant: () -> Unit,
    denied: () -> Unit,
    rationaleTitle: String?,
    rationaleMessage: String?,
    rationaleConfirmText: String?,
    deniedTitle: String?,
    deniedMessage: String?,
    vararg permissions: String) {

    val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            grant()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            denied()
        }
    }

    TedPermission.with(this)
        .setPermissionListener(permissionListener)
        .setRationaleTitle(rationaleTitle)
        .setRationaleMessage(rationaleMessage)
        .setRationaleConfirmText(rationaleConfirmText)
        .setDeniedTitle(deniedTitle)
        .setDeniedMessage(deniedMessage)
        .setGotoSettingButton(true)
        .setPermissions(*permissions)
        .check()

}

fun Context.checkTedPermission(
    grant: () -> Unit,
    denied: () -> Unit,
    rationaleTitle: String?,
    rationaleMessage: String?,
    rationaleConfirmText: String?,
    deniedTitle: String?,
    deniedMessage: String?,
    deniedCloseText: String?,
    vararg permissions: String) {

    val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            grant()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            denied()
        }
    }

    TedPermission.with(this)
        .setPermissionListener(permissionListener)
        .setRationaleTitle(rationaleTitle)
        .setRationaleMessage(rationaleMessage)
        .setRationaleConfirmText(rationaleConfirmText)
        .setDeniedTitle(deniedTitle)
        .setDeniedMessage(deniedMessage)
        .setDeniedCloseButtonText(deniedCloseText)
        .setGotoSettingButton(true)
        .setPermissions(*permissions)
        .check()

}