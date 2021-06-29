package com.staygrateful.app.todolist.domain.usecase

import com.staygrateful.app.todolist.data.response.Todo
import io.reactivex.Observable

interface HomepageUseCase {
    fun getAllTodos() : Observable<MutableList<Todo.Response>>
}