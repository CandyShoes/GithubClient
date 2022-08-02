package com.moyahong.githubclient.main

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moyahong.githubclient.R
import com.moyahong.githubclient.base.ISearchContract
import com.moyahong.githubclient.model.SearchResult
import com.moyahong.githubclient.model.User
import es.dmoral.toasty.Toasty


class SearchActivity : AppCompatActivity(), ISearchContract.View {

    private var searchEt: EditText? = null
    private var searchBt: Button? = null
    private var searchRv: RecyclerView? = null
    val searchPresent = SearchPresenter()
    val userListAdapter = UserListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchPresent.attachView(this)
        searchBt = findViewById(R.id.bt_search)
        searchRv = findViewById(R.id.recycler_view)
        searchEt = findViewById(R.id.et_search)
        searchBt?.setOnClickListener {
            searchEt?.text?.let {
                searchPresent.search(it.toString())
            }
            //跳转搜索列表页，发搜索服务
        }
        searchRv?.adapter = userListAdapter
        val layoutManager = LinearLayoutManager(this)
        searchRv?.layoutManager = layoutManager
    }

    override fun onSearchSuccess(result: SearchResult<User>) {
        userListAdapter.data = result.items
        userListAdapter.notifyDataSetChanged()
    }

    override fun onSearchError(errorMsg: String) {
        Toasty.normal(this, "search failed")

    }


    override fun showProgressDialog(msg: String) {
        Toasty.normal(this, msg)
    }

    override fun dismissProgressDialog() {
    }
}