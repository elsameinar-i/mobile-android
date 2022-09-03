package {{ cookiecutter.package_name }}.data.remote

import {{ cookiecutter.package_name }}.data.remote.model.response.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthService {
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterResponse>
}