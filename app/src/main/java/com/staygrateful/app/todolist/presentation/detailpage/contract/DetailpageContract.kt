package com.staygrateful.app.todolist.presentation.detailpage.contract

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.staygrateful.app.todolist.data.response.Todo

class DetailpageContract {

    interface View {
        fun onAutoAndroidInjector()
        fun initData(intent: Intent?)
        fun initEvent()
    }
}