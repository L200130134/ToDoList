package learn.clean_architecture.android.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import learn.clean_architecture.android.di.scope.AppScope
import learn.clean_architecture.android.external.scheduler.AppSchedulerProvider

@Module
class AppModule {

    @Provides
    @AppScope
    fun context(application: Application): Context = application

    @Provides
    @AppScope
    fun gson() = Gson()

    @Provides
    @AppScope
    fun provideSchedulerProvider() = AppSchedulerProvider()
}