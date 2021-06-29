package com.staygrateful.app.todolist.presentation.homepage.contract

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.staygrateful.app.todolist.data.response.Todo

class HomepageContract {

    interface View {
        fun onAutoAndroidInjector()
        fun initData()
        fun initEvent()
        fun setupAdapter()
        fun submitList(list: List<Todo.Data>)
        fun onClickItemAdapter(view: android.view.View, data: Todo.Data?)
        fun showError(message: String?)
        fun showProgressBar()
        fun showPopup(view: android.view.View)
        fun hideProgressBar()
        fun getGroupType() : Int
        fun updateGroupType(groupType: Int)
        fun getList() : MutableList<Todo.Data>
    }

    interface UserActionListener {
        fun getAllTodo()
        fun submitList(responses: MutableList<Todo.Response>?)
        fun updateGroupType(groupType: Int)
    }
}