package {{ cookiecutter.package_name }}.data.remote

import {{ cookiecutter.package_name }}.core.BaseDataSource
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
        private val characterService: CharacterService
): BaseDataSource() {

    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getCharactersPagination(page: Int) = getResult { characterService.getCharactersPagination(page) }
    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
}