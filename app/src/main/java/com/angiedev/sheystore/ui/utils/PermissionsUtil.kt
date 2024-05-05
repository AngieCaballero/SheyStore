package com.angiedev.sheystore.ui.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

private const val TAG = "PermissionUtil"

/**
 *
 * @return `true` if permission have been granted and `false` otherwise
 */
fun checkPermission(context: Context, permission: String) = arePermissionsGranted(context, permission)

/**
 * Checks, if all specified permissions were granted.
 *
 * @param context Android context
 * @param permissions permissions to check
 * @return `true` if every permission is already granted by the system, and `false` otherwise
 */
private fun arePermissionsGranted(
    context: Context,
    permissions: String
): Boolean {
    return ActivityCompat.checkSelfPermission(context, permissions) == PackageManager.PERMISSION_GRANTED
}