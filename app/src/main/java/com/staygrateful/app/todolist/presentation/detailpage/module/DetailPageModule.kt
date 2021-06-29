package com.staygrateful.app.todolist.presentation.detailpage.module

import com.staygrateful.app.todolist.presentation.detailpage.contract.DetailpageContract
import com.staygrateful.app.todolist.presentation.detailpage.view.DetailPageActivity
import com.staygrateful.app.todolist.presentation.homepage.contract.HomepageContract
import com.staygrateful.app.todolist.presentation.homepage.view.HomeActivity
import dagger.Module
import dagger.Provides
import learn.clean_architecture.android.di.scope.ActivityScope

@Module
class DetailPageModule {

    @Provides
    @ActivityScope
    internal fun provideActvity(detailPageActivity: DetailPageActivity): DetailpageContract.View {
        return detailPageActivity
    }
}