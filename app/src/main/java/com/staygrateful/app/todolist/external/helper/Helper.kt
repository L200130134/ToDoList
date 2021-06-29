package com.staygrateful.app.todolist.external.helper

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.staygrateful.app.todolist.MainApp
import com.staygrateful.app.todolist.R
import com.staygrateful.app.todolist.external.extension.willDo
import com.staygrateful.developers.filesid.ext.getContextBy

object Helper {

    fun getStringRes(context: Context = MainApp.instance, idRes: Int) : String {
        return context.getString(idRes)
    }

    fun getColorStatus(context: Context, completed: Boolean): Int {
        return ContextCompat.getColor(context,
            completed.willDo(R.color.green, R.color.red)
        )
    }

    @ColorInt
    fun getUserColor(userId: Int) : Int {
        val id = (userId >= 10 || userId < 0).willDo(0, userId - 1)
        return getColorList()[id]
    }

    @ColorInt
    fun getColorList() : Array<Int> {
        return arrayOf(
            Color.parseColor("#FF9D3C"),
            Color.parseColor("#009688"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#8BC34A"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#FFEB3B"),
            Color.parseColor("#03A9F4"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#FF9800")
        )
    }
}