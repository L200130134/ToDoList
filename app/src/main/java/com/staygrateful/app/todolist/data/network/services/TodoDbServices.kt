package com.staygrateful.app.todolist.data.network.services

import com.staygrateful.app.todolist.data.response.Todo
import com.staygrateful.app.todolist.external.constant.RestConstant
import io.reactivex.Observable
import retrofit2.http.GET

interface TodoDbServices {

    @GET(RestConstant.TodoDB.getAllTodos)
    fun getAllTodos(): Observable<MutableList<Todo.Response>>
}