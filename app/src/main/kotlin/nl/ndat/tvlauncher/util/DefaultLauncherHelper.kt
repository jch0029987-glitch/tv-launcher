package nl.ndat.tvlauncher.util

import android.annotation.SuppressLint
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Build

class DefaultLauncherHelper(
    private val context: Context,
) {
    private val roleManager by lazy { 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.getSystemService(Context.ROLE_SERVICE) as? RoleManager
        } else {
            null
        }
    }

    private val isCompatible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && roleManager != null

    @SuppressLint("NewApi")
    fun isDefaultLauncher(): Boolean {
        return if (isCompatible) {
            roleManager?.isRoleHeld(RoleManager.ROLE_HOME) ?: false
        } else {
            // Fallback for older versions: check if our package is the preferred home activity
            val intent = Intent(Intent.ACTION_MAIN).apply { addCategory(Intent.CATEGORY_HOME) }
            val resolveInfo = context.packageManager.resolveActivity(intent, 0)
            resolveInfo?.activityInfo?.packageName == context.packageName
        }
    }

    @SuppressLint("NewApi")
    fun canRequestDefaultLauncher(): Boolean =
        isCompatible && roleManager?.isRoleAvailable(RoleManager.ROLE_HOME) == true

    @SuppressLint("NewApi")
    fun requestDefaultLauncherIntent(): Intent? =
        if (isCompatible) {
            roleManager?.createRequestRoleIntent(RoleManager.ROLE_HOME)
        } else {
            // For older versions, we just open the home settings
            Intent(android.provider.Settings.ACTION_HOME_SETTINGS)
        }
}
