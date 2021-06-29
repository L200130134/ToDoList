package com.staygrateful.app.todolist.presentation.homepage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.staygrateful.app.todolist.domain.usecase.HomepageUseCase
import com.staygrateful.app.todolist.presentation.homepage.contract.HomepageContract

class HomeViewModelFactory(
    val view: HomepageContract.View,
    val mUseCase: HomepageUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(view, mUseCase) as T
    }
}