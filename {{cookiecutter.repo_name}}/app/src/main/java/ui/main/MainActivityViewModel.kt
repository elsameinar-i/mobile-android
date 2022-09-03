package {{ cookiecutter.package_name }}.ui.main

import android.app.Application
import android.content.SharedPreferences
import {{ cookiecutter.package_name }}.App
import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.core.BaseViewModel
import {{ cookiecutter.package_name }}.utils.extensions.saveAuthToken
import javax.inject.Inject
{% if cookiecutter.with_list == "y" %}
import {{ cookiecutter.package_name }}.data.local.entities.Character
import {{ cookiecutter.package_name }}.data.remote.CharacterService
import {{ cookiecutter.package_name }}.data.repository.CharacterRepository
import {{ cookiecutter.package_name }}.utils.service.Resource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
{% endif %}


class MainActivityViewModel(app: Application) : BaseViewModel(app) {
    {% if cookiecutter.with_list == "y" %}
    @Inject
    lateinit var repository: CharacterRepository

    @Inject
    lateinit var characterService: CharacterService
    {% endif %}
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    {% if cookiecutter.with_list == "y" %}
    // character detail fragment
    private val _id = MutableLiveData<Int>()
    private val _character = _id.switchMap { id ->
        repository.getCharacter(id)
    }
    val character: LiveData<Resource<Character>> = _character

    private var currentCharacterResult: Flow<PagingData<Character>>? = null
    {% endif %}
    init {
        (app as? App)?.component?.inject(this)
        sharedPreferences.saveAuthToken("example_token")
    }
    {% if cookiecutter.with_list == "y" %}
    fun getCharacters(): Flow<PagingData<Character>> {
        val lastResult = currentCharacterResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Character>> = repository.getCharactersPager()
                .cachedIn(viewModelScope)
        currentCharacterResult = newResult
        return newResult
    }
    fun getCharacter(id: Int) {
        _id.value = id
    }
    {% endif %}
    fun getAppName() = getApplication<Application>().resources.getString(R.string.app_name)
}