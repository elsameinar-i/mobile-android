package {{ cookiecutter.package_name }}.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import {{ cookiecutter.package_name }}.App
import {{ cookiecutter.package_name }}.core.BaseViewModel
import {{ cookiecutter.package_name }}.data.remote.model.response.CharacterResponse
import {{ cookiecutter.package_name }}.data.repository.AuthRepository
import {{ cookiecutter.package_name }}.utils.service.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(app: Application) : BaseViewModel(app) {
    @Inject
    lateinit var repository: AuthRepository

    init {
        (app as? App)?.component?.inject(this)
    }

    private val _loginResult = MutableLiveData<Resource<CharacterResponse>>()
    val loginResult: LiveData<Resource<CharacterResponse>> = _loginResult

    fun doLogin(username: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.loading()

            val responseStatus = repository.doLogin(username, password)
            if (responseStatus.status == Resource.Status.SUCCESS) {
                _loginResult.value = Resource.success(responseStatus.data!!)
            } else if (responseStatus.status == Resource.Status.ERROR) {
                _loginResult.value = Resource.error(responseStatus.message!!)
            }
        }
    }
}