package com.moyahong.githubclient.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.moyahong.githubclient.AppData
import com.moyahong.githubclient.HomeActivity
import com.moyahong.githubclient.R
import com.moyahong.githubclient.base.AppConfig
import com.moyahong.githubclient.base.IProfileContract
import com.moyahong.githubclient.model.User
import es.dmoral.toasty.Toasty

/**
 * author: moyahong .
 * date:   On 2022/8/2
 */
class PersonActivity : AppCompatActivity(), IProfileContract.View {
    private var avatarIv: ImageView? = null
    private var nameTv: TextView? = null
    private var logoutBt: TextView? = null
    private var followTv: TextView? = null
    private var locationTv: TextView? = null
    private var blogTv: TextView? = null
    var user: User? = null
    val mPresenter = ProfilePresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        mPresenter.attachView(this)
        initView()
        val userInfo = intent.getStringExtra(AppConfig.INTENT_USER)
        if (TextUtils.isEmpty(userInfo)) {
            user = AppData.loggedUser
        } else {
            mPresenter.queryUser(userInfo)
        }
        bindUser(user)
    }

    private fun bindUser(user: User?) {
        user?.run {
            avatarUrl.let {
                Glide.with(this@PersonActivity)
                    .load(it)
                    //            .onlyRetrieveFromCache(!PrefUtils.isLoadImageEnable())
                    .into(avatarIv)
            }
            nameTv?.text = login
            locationTv?.text = location
            followTv?.text = following.toString()
            blogTv?.text = "BLOG:$blog"

            if (AppData.loggedUser?.login == login) {
                logoutBt?.visibility = View.VISIBLE
            } else {
                logoutBt?.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun initView() {
        avatarIv = findViewById<ImageView>(R.id.iv_avatar)
        nameTv = findViewById<TextView>(R.id.tv_name)
        logoutBt = findViewById<TextView>(R.id.bt_sign_out)
        followTv = findViewById<TextView>(R.id.tv_follow)
        locationTv = findViewById<TextView>(R.id.tv_location)
        blogTv = findViewById<TextView>(R.id.tv_email)
        logoutBt?.setOnClickListener { logout() }
    }

    fun logout() {
        AlertDialog.Builder(this)
            .setCancelable(true)
            .setTitle(R.string.warning_dialog_tile)
            .setMessage(R.string.logout_warning)
            .setNegativeButton(R.string.cancel, { dialog, which -> dialog.dismiss() })
            .setPositiveButton(R.string.ok, { dialog, which -> doLogout() })
            .show()
    }

    private fun doLogout() {
        AppData.logout()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun onQueryUserSuccess(result: User) {
        user = result
        bindUser(result)
    }

    override fun onQueryUserError(errorMsg: String) {
        Toasty.normal(this, errorMsg)
    }

    override fun showProgressDialog(msg: String) {
        Toasty.normal(this, msg)
    }

    override fun dismissProgressDialog() {
    }
}