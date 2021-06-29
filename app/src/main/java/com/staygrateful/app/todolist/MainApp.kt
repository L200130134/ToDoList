package com.staygrateful.app.todolist

import android.app.Activity
import android.app.Application
import android.content.Context
import com.staygrateful.app.todolist.di.component.AppComponent
import com.staygrateful.app.todolist.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import learn.clean_architecture.android.di.module.NetworkModule
import javax.inject.Inject

class MainApp : Application(), HasActivityInjector {

    companion object {
        @JvmStatic
        lateinit var instance: MainApp
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = createComponent()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    /**
     * Initialize Dependency Injection With Dagger
     * Level DI Application
     * */
    private fun createComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .application(this)
            .networkModule(NetworkModule(this))
            .build()
    }

}