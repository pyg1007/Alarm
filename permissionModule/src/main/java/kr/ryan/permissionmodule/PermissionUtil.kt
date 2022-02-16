package kr.ryan.permissionmodule

import android.content.Context
import com.gun0912.tedpermission.coroutine.TedPermission

/**
 * WeatherAlarm
 * Class: PermissionUtil
 * Created by pyg10.
 * Created On 2022-02-14.
 * Description:
 */


suspend fun requestPermission(
    grant: suspend () -> Unit,
    denied: suspend (MutableList<String>?) -> Unit,
    vararg permissions: String
) {
    val result = TedPermission
        .create()
        .setPermissions(*permissions)
        .check()

    if (result.isGranted)
        grant()
    else
        denied(result.deniedPermissions)
}