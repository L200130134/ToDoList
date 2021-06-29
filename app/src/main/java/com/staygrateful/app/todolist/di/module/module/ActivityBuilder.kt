package com.staygrateful.app.todolist.di.module.module

import com.staygrateful.app.todolist.presentation.detailpage.module.DetailPageModule
import com.staygrateful.app.todolist.presentation.detailpage.view.DetailPageActivity
import com.staygrateful.app.todolist.presentation.homepage.view.HomeActivity
import com.staygrateful.app.todolist.presentation.homepage.module.HomepageModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import learn.clean_architecture.android.di.scope.ActivityScope

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [(HomepageModule::class)])
    internal abstract fun bindHomePageActivity(): HomeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [(DetailPageModule::class)])
    internal abstract fun bindDetailPageActivity(): DetailPageActivity

}