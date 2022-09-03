package {{ cookiecutter.package_name }}

import android.content.Context
import androidx.multidex.MultiDex
import {{ cookiecutter.package_name }}.di.component.DaggerApplicationComponent
import {{ cookiecutter.package_name }}.di.module.ApplicationModule
import timber.log.Timber

class App : android.app.Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    val component by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

