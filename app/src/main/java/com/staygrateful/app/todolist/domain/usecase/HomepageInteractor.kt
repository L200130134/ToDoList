package com.staygrateful.app.todolist.domain.usecase

import com.staygrateful.app.todolist.data.network.repository.TodoRepository
import com.staygrateful.app.todolist.data.response.Todo
import io.reactivex.Observable
import javax.inject.Inject

class HomepageInteractor @Inject constructor(val mRepository: TodoRepository) : HomepageUseCase {

    override fun getAllTodos(): Observable<MutableList<Todo.Response>> {
        return mRepository.getAllTodos().flatMap {
            Observable.just(it)
        }
    }
}