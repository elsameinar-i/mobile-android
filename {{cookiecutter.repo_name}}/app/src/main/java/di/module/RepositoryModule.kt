package {{ cookiecutter.package_name }}.di.module

import dagger.Module
import dagger.Provides
{% if cookiecutter.with_list == 'y' %}
import {{ cookiecutter.package_name }}.data.local.dao.CharacterDao
import {{ cookiecutter.package_name }}.data.local.dao.CharacterRemoteKeysDao
import {{ cookiecutter.package_name }}.data.remote.CharacterRemoteDataSource
import {{ cookiecutter.package_name }}.data.repository.CharacterRepository
{% endif %}
{% if cookiecutter.login_page == 'y' %}
import {{ cookiecutter.package_name }}.data.remote.AuthRemoteDataSource
import {{ cookiecutter.package_name }}.data.repository.AuthRepository
{% endif %}
import javax.inject.Singleton

@Module
class RepositoryModule {
    {% if cookiecutter.with_list == 'y' %}
    @Singleton
    @Provides
    fun provideCharacterRepository(remoteDataSource: CharacterRemoteDataSource,
                                   remoteKeysDataSource: CharacterRemoteKeysDao,
                                   localDataSource: CharacterDao) =
        CharacterRepository(remoteDataSource, remoteKeysDataSource, localDataSource)
    {% endif %}
    {% if cookiecutter.login_page == 'y' %}
    @Singleton
    @Provides
    fun provideAuthRepository(remoteDataSource: AuthRemoteDataSource) =
        AuthRepository(remoteDataSource)
    {% endif %}
}