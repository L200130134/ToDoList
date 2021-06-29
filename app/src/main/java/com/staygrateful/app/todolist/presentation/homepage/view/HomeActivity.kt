package com.staygrateful.app.todolist.presentation.homepage.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.staygrateful.app.todolist.R
import com.staygrateful.app.todolist.data.response.Todo
import com.staygrateful.app.todolist.databinding.ActivityHomeBinding
import com.staygrateful.app.todolist.external.adapter.ToDoAdapter
import com.staygrateful.app.todolist.external.extension.willDo
import com.staygrateful.app.todolist.presentation.detailpage.view.DetailPageActivity
import com.staygrateful.app.todolist.presentation.homepage.contract.HomepageContract
import com.staygrateful.app.todolist.presentation.homepage.viewmodel.HomeViewModel
import com.staygrateful.developers.filesid.ext.showToast
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomepageContract.View, View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var mBinding: ActivityHomeBinding

    @Inject
    lateinit var mViewModel: HomeViewModel

    private lateinit var mTodoAdapter: ToDoAdapter

    var mGroupType = TYPE_GROUP_USER

    override fun onCreate(savedInstanceState: Bundle?) {
        this.onAutoAndroidInjector()
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        updateGroupType(TYPE_GROUP_USER)
        setupAdapter()
        initData()
        initEvent()
    }

    override fun onAutoAndroidInjector() {
        AndroidInjection.inject(this)
    }

    override fun initData() {
        mViewModel.getAllTodo()
    }

    override fun initEvent() {
        mBinding.iconMore.setOnClickListener(this)
        mBinding.swipeRefresh.setOnRefreshListener(this)
    }

    override fun setupAdapter() {
        mTodoAdapter = ToDoAdapter(this)
        mBinding.todoList.layoutManager = LinearLayoutManager(this)
        mBinding.todoList.setHasFixedSize(true)
        mBinding.todoList.adapter = mTodoAdapter
    }

    override fun submitList(list: List<Todo.Data>) {
        mTodoAdapter.submit(list)
    }

    override fun onClickItemAdapter(view: View, data: Todo.Data?) {
        if (data != null) {
            val intent = Intent(this, DetailPageActivity::class.java)
            intent.putExtra(DetailPageActivity.KEY_DATA, data)
            startActivity(intent)
        }
    }

    override fun showError(message: String?) {
        showToast("Error with message : $message")
    }

    override fun showProgressBar() {
        mBinding.swipeRefresh.post {
            mBinding.swipeRefresh.isRefreshing = true
        }
    }

    override fun showPopup(view: View) {
        PopupMenu(this, view).apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_group_status -> {
                        mViewModel.updateGroupType(TYPE_GROUP_STATUS)
                        true
                    }
                    R.id.item_group_user -> {
                        mViewModel.updateGroupType(TYPE_GROUP_USER)
                        true
                    }
                    R.id.item_exit -> {
                        super.onBackPressed()
                        true
                    }
                    else -> false
                }
            }
            inflate(R.menu.menu_selected_group)
            menu[(mGroupType == TYPE_GROUP_USER).willDo(0, 1)].isVisible = false
            show()
        }
    }

    override fun hideProgressBar() {
        mBinding.swipeRefresh.post {
            mBinding.swipeRefresh.isRefreshing = false
        }
    }

    override fun getGroupType(): Int {
        return mGroupType
    }

    override fun updateGroupType(groupType: Int) {
        mGroupType = groupType
        mBinding.textGroup.text = (groupType == TYPE_GROUP_USER)
            .willDo("Group by user", "Group by status")
    }

    override fun getList(): MutableList<Todo.Data> {
        return mTodoAdapter.currentList
    }

    override fun onRefresh() {
        mViewModel.getAllTodo()
    }

    companion object {
        const val TYPE_GROUP_STATUS = 0
        const val TYPE_GROUP_USER = 1
    }

    override fun onClick(v: View?) {
        if (v == mBinding.iconMore) {
            showPopup(v)
        }
    }
}