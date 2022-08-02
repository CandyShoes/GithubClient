package com.moyahong.githubclient

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.annotation.NonNull
import java.util.*

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
object AppOpener {

    private val VIEW_IGNORE_PACKAGE = Arrays.asList(
        "com.gh4a", "com.fastaccess", "com.taobao.taobao"
    )

    fun openInBrowser(@NonNull context: Context, @NonNull url: String?) {
        val uri = Uri.parse(url)
        var intent = Intent(Intent.ACTION_VIEW, uri).addCategory(Intent.CATEGORY_BROWSABLE)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent = createActivityChooserIntent(
            context,
            intent,
            uri,
            VIEW_IGNORE_PACKAGE
        )
        context.startActivity(intent)
    }


    private fun createActivityChooserIntent(
        context: Context, intent: Intent,
        uri: Uri, ignorPackageList: List<String>?
    ): Intent? {
        val pm = context.packageManager
        val activities = pm.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        val chooserIntents = ArrayList<Intent>()
        val ourPackageName = context.packageName
        Collections.sort(activities, ResolveInfo.DisplayNameComparator(pm))
        for (resInfo in activities) {
            val info = resInfo.activityInfo
            if (!info.enabled || !info.exported) {
                continue
            }
            if (info.packageName == ourPackageName) {
                continue
            }
            if (ignorPackageList != null && ignorPackageList.contains(info.packageName)) {
                continue
            }
            val targetIntent = Intent(intent)
            targetIntent.setPackage(info.packageName)
            targetIntent.setDataAndType(uri, intent.type)
            chooserIntents.add(targetIntent)
        }
        if (chooserIntents.isEmpty()) {
            return null
        }
        val lastIntent = chooserIntents.removeAt(chooserIntents.size - 1)
        if (chooserIntents.isEmpty()) {
            // there was only one, no need to showImage the chooser
            return lastIntent
        }
        val chooserIntent = Intent.createChooser(lastIntent, null)
        chooserIntent.putExtra(
            Intent.EXTRA_INITIAL_INTENTS,
            chooserIntents.toTypedArray()
        )
        return chooserIntent
    }


}