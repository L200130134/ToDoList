package com.staygrateful.app.todolist.presentation.homepage.module

import androidx.lifecycle.ViewModelProvider
import com.staygrateful.app.todolist.data.network.repository.TodoDataStore
import com.staygrateful.app.todolist.data.network.repository.TodoRepository
import com.staygrateful.app.todolist.data.network.services.TodoDbServices
import com.staygrateful.app.todolist.databinding.ActivityHomeBinding
import com.staygrateful.app.todolist.domain.usecase.HomepageInteractor
import com.staygrateful.app.todolist.domain.usecase.HomepageUseCase
import com.staygrateful.app.todolist.presentation.homepage.contract.HomepageContract
import com.staygrateful.app.todolist.presentation.homepage.view.HomeActivity
import com.staygrateful.app.todolist.presentation.homepage.viewmodel.HomeViewModel
import com.staygrateful.app.todolist.presentation.homepage.viewmodel.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import learn.clean_architecture.android.di.scope.ActivityScope
import learn.clean_architecture.android.external.scheduler.AppSchedulerProvider

@Module
class HomepageModule {

    @Provides
    @ActivityScope
    internal fun provideActvity(homeActivity: HomeActivity): HomepageContract.View {
        return homeActivity
    }

    @Provides
    @ActivityScope
    internal fun provideRepository(
        mService: TodoDbServices,
        mScheduler: AppSchedulerProvider
    ): TodoRepository = TodoDataStore(mService, mScheduler)

    @Provides
    @ActivityScope
    internal fun provideUsecase(repository: TodoRepository): HomepageUseCase =
        HomepageInteractor(repository)

    @Provides
    @ActivityScope
    internal fun provideBinding(activity: HomeActivity): ActivityHomeBinding =
        ActivityHomeBinding.inflate(activity.layoutInflater)

    @Provides
    @ActivityScope
    internal fun provideViewModel(
        activity: HomeActivity,
        view: HomepageContract.View,
        useCase: HomepageUseCase
    ) = ViewModelProvider(activity, HomeViewModelFactory(view, useCase))
        .get(HomeViewModel::class.java)
}