package {{ cookiecutter.package_name }}.di.component

import android.content.Context
import android.content.SharedPreferences
import dagger.Component
import {{ cookiecutter.package_name }}.App
import {{ cookiecutter.package_name }}.di.module.*
import {{ cookiecutter.package_name }}.ui.main.MainActivityViewModel
{% if cookiecutter.login_page == "y" %}
import {{ cookiecutter.package_name }}.ui.login.LoginViewModel
{% endif %}
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ApplicationModule::class,
    InterceptorModule::class,
    NetModule::class,
    DatabaseModule::class,
    RepositoryModule::class
])
interface ApplicationComponent {

    fun app(): App

    fun context(): Context

    fun preferences(): SharedPreferences

    fun inject(mainActivityViewModel: MainActivityViewModel)

    {% if cookiecutter.login_page == "y" %}
    fun inject(loginViewModel: LoginViewModel)
    {% endif %}
}
