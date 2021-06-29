package com.staygrateful.app.todolist.data.network.repository

import com.staygrateful.app.todolist.data.response.Todo
import io.reactivex.Observable

interface TodoRepository {
    fun getAllTodos(): Observable<MutableList<Todo.Response>>
}