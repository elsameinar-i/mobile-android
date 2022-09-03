package {{ cookiecutter.package_name }}.data.remote

import {{ cookiecutter.package_name }}.core.BaseDataSource
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val authService: AuthService
): BaseDataSource() {

    suspend fun getCharacter() = getResult { authService.getCharacter(1) }
}