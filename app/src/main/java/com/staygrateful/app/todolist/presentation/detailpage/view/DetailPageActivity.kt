package com.staygrateful.app.todolist.presentation.detailpage.view

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.staygrateful.app.todolist.R
import com.staygrateful.app.todolist.data.response.Todo
import com.staygrateful.app.todolist.databinding.ActivityDetailPageBinding
import com.staygrateful.app.todolist.external.extension.willDo
import com.staygrateful.app.todolist.external.helper.Helper
import com.staygrateful.app.todolist.presentation.detailpage.contract.DetailpageContract
import dagger.android.AndroidInjection

class DetailPageActivity : AppCompatActivity(), DetailpageContract.View, View.OnClickListener {

    private lateinit var mBinding: ActivityDetailPageBinding
    var todo: Todo.Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.onAutoAndroidInjector()
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initData(intent)
        initEvent()
    }

    companion object {
        const val KEY_DATA = "key_data"
    }

    override fun onAutoAndroidInjector() {
        AndroidInjection.inject(this)
    }

    override fun initData(intent: Intent?) {
        if (intent != null) {
            this.todo = intent.getParcelableExtra(KEY_DATA)
        }

        if (todo == null) {
            return finish()
        }

        val response = todo!!.data!!

        mBinding.layoutId.textTitle.text = Helper.getStringRes(this, R.string.text_title_id)
        mBinding.layoutUserId.textTitle.text = Helper.getStringRes(this, R.string.text_title_user_id)
        mBinding.layoutTitle.textTitle.text = Helper.getStringRes(this, R.string.text_title_title)
        mBinding.layoutStatus.textTitle.text = Helper.getStringRes(this, R.string.text_title_status)

        mBinding.layoutId.textValue.text = response.id.toString()
        mBinding.layoutUserId.textValue.text = response.userId.toString()
        mBinding.layoutTitle.textValue.text = response.title.replaceFirstChar(Char::uppercase)
        mBinding.layoutStatus.textValue.text = response.completed.willDo(
            Helper.getStringRes(this, R.string.text_completed),
            Helper.getStringRes(this, R.string.text_not_completed)
        )

        val colorStatus = Helper.getColorStatus(this, response.completed)
        val bg = mBinding.imageUser.background.mutate()
        if (bg is GradientDrawable) {
            bg.setColor(Helper.getUserColor(response.userId))
        }

        mBinding.layoutStatus.iconCompleted.visibility = View.VISIBLE
        mBinding.layoutStatus.divider.visibility = View.INVISIBLE

        mBinding.layoutStatus.textValue.setTextColor(colorStatus)
        mBinding.layoutStatus.iconCompleted.setColorFilter(colorStatus)
    }

    override fun initEvent() {
        mBinding.iconBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == mBinding.iconBack) {
            super.onBackPressed()
        }
    }
}