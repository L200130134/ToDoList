package com.staygrateful.app.todolist.di.component

import android.app.Application
import com.staygrateful.app.todolist.MainApp
import com.staygrateful.app.todolist.di.module.module.ActivityBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import learn.clean_architecture.android.di.module.AppModule
import learn.clean_architecture.android.di.module.NetworkModule
import learn.clean_architecture.android.di.scope.AppScope

@AppScope
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AppModule::class),
        (NetworkModule::class),
        (ActivityBuilder::class)
    ]
)

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }

    fun inject(app: MainApp)
}