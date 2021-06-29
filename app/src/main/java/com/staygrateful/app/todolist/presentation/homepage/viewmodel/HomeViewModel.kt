package com.staygrateful.app.todolist.presentation.homepage.viewmodel

import androidx.lifecycle.ViewModel
import com.staygrateful.app.todolist.R
import com.staygrateful.app.todolist.data.response.Todo
import com.staygrateful.app.todolist.domain.usecase.HomepageUseCase
import com.staygrateful.app.todolist.external.extension.willDo
import com.staygrateful.app.todolist.external.helper.Helper
import com.staygrateful.app.todolist.presentation.homepage.contract.HomepageContract
import com.staygrateful.app.todolist.presentation.homepage.view.HomeActivity
import com.staygrateful.developers.filesid.ext.postToast
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val view: HomepageContract.View,
    val mUseCase: HomepageUseCase
) : ViewModel(), HomepageContract.UserActionListener {

    private var mCompositeDisposable = CompositeDisposable()

    override fun getAllTodo() {
        view.showProgressBar()
        mCompositeDisposable.add(
            mUseCase.getAllTodos().subscribe({ response ->
                view.hideProgressBar()
                submitList(response)
            }, {
                view.hideProgressBar()
                view.showError(it.localizedMessage)
                it.printStackTrace()
            })
        )
    }

    override fun submitList(responses: MutableList<Todo.Response>?) {
        if (responses != null) {
            val result: MutableList<Todo.Data> = mutableListOf()
            val groupType = view.getGroupType()
            val groupByUser = groupType == HomeActivity.TYPE_GROUP_USER
            if (groupByUser) {
                responses.sortBy { it.userId }
            } else {
                responses.sortBy { !it.completed }
            }
            var lastContent: Any? = null
            for (response in responses) {
                val currentContent = groupByUser.willDo(response.userId, response.completed)
                if (lastContent == null || lastContent != currentContent) {
                    val dataHeader = Todo.Data()
                    dataHeader.header = true
                    dataHeader.headerTitle = groupByUser.willDo(
                        "User Id ${response.userId}",
                        response.completed.willDo(
                            Helper.getStringRes(idRes = R.string.text_completed),
                            Helper.getStringRes(idRes = R.string.text_not_completed)
                        )
                    )
                    result.add(dataHeader)
                }
                val data = Todo.Data(response)
                result.add(data)
                lastContent = groupByUser.willDo(response.userId, response.completed)
            }
            view.submitList(result)
        }
    }

    override fun updateGroupType(groupType: Int) {
        view.updateGroupType(groupType)
        val dataList = view.getList()
        val responseList: MutableList<Todo.Response> = mutableListOf()
        for (data in dataList) {
            if (!data.header) {
                val response = data.data
                if (response != null) {
                    responseList.add(response)
                }
            }
        }
        submitList(responseList)
    }

}