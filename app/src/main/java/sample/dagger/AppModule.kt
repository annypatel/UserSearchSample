package sample.dagger

import android.app.Application
import android.content.Context
import sample.App
import dagger.Binds
import dagger.Module

/**
 * Module that injects [Application] and application [Context] into application component.
 */
@Module
abstract class AppModule {

    @Binds
    abstract fun application(app: App): Application

    @Binds
    abstract fun context(app: App): Context
}