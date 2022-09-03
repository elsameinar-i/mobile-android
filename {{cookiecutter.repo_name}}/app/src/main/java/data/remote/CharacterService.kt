package {{ cookiecutter.package_name }}.data.remote

import {{ cookiecutter.package_name }}.data.remote.model.response.CharacterListResponse
import {{ cookiecutter.package_name }}.data.remote.model.response.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {
    @GET("character")
    suspend fun getAllCharacters(): Response<CharacterListResponse>

    @GET("character/")
    suspend fun getCharactersPagination(@Query("page") page: Int): Response<CharacterListResponse>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterResponse>
}