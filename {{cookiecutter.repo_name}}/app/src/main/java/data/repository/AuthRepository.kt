package {{ cookiecutter.package_name }}.data.repository

import {{ cookiecutter.package_name }}.data.remote.AuthRemoteDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) {

    suspend fun doLogin(username: String, password: String) = remoteDataSource.getCharacter()
}