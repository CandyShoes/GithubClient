package com.moyahong.githubclient

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.login.LoginActivity
import com.moyahong.githubclient.main.SearchActivity
import com.moyahong.githubclient.profile.PersonActivity


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyStoragePermissions(this)
        findViewById<View>(R.id.personal).setOnClickListener {
            gotoPersonal()
        }
        findViewById<View>(R.id.search).setOnClickListener {
            gotoSearch()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_title_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.person -> {
                gotoPersonal()
            }
            R.id.search -> {
                gotoSearch()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotoSearch() {
        this.startActivity(Intent(this, SearchActivity::class.java))
    }

    private fun gotoPersonal() {
        if (AppData.isLogin()) {
            val intent = Intent(this, PersonActivity::class.java)
            intent.putExtra(AppConfig.INTENT_USER, AppData.loggedUser?.login)
            this.startActivity(intent)
        } else {
            this.startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )


    fun verifyStoragePermissions(activity: Activity) {
        try {
            //检测是否有写的权限
            val permission: Int = ActivityCompat.checkSelfPermission(
                activity,
                "android.permission.WRITE_EXTERNAL_STORAGE"
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}