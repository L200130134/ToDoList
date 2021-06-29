package com.staygrateful.app.todolist.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

sealed class Todo {

    @Parcelize
    data class Response(
        @SerializedName("userId") var userId : Int,
        @SerializedName("id") var id : Int,
        @SerializedName("title") var title : String,
        @SerializedName("completed") var completed : Boolean
    ) : Parcelable

    @Parcelize
    data class Data(
        var data : Response? = null,
        var header : Boolean = false,
        var headerType : Int = 0,
        var headerTitle : String? = null,
    ) : Parcelable {
        fun sameItem(responseOther: Data?): Boolean {
            if (responseOther != null) {
                val thisData = data
                val dataOther = responseOther.data
                if (thisData != null && dataOther != null) {
                    return header == header &&
                            headerTitle == headerTitle &&
                            //thisData.id == dataOther.id &&
                            thisData.title == dataOther.title &&
                            thisData.userId == dataOther.userId &&
                            thisData.completed == dataOther.completed
                }
            }
            return false
        }

        override fun hashCode(): Int {
            return data.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            if (other is Data) {
                val thisData = data
                val dataOther = other.data
                if (thisData != null && dataOther != null) {
                    return header == header &&
                            headerTitle == headerTitle &&
                            thisData.id == dataOther.id &&
                            thisData.title == dataOther.title &&
                            thisData.userId == dataOther.userId &&
                            thisData.completed == dataOther.completed
                }
            }
            return false
        }
    }
}