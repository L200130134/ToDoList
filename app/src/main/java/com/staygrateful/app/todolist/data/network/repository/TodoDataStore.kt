package com.staygrateful.app.todolist.data.network.repository

import com.staygrateful.app.todolist.data.network.services.TodoDbServices
import com.staygrateful.app.todolist.data.response.Todo
import io.reactivex.Observable
import learn.clean_architecture.android.external.scheduler.SchedulerProvider

class TodoDataStore constructor(
    val mService: TodoDbServices,
    val mScheduler: SchedulerProvider
) : TodoRepository {

    override fun getAllTodos(): Observable<MutableList<Todo.Response>> {
        return mService.getAllTodos()
            .subscribeOn(mScheduler.io())
            .observeOn(mScheduler.mainThread())
    }
}