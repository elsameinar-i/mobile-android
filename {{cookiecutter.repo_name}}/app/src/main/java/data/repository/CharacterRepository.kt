package {{ cookiecutter.package_name }}.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import {{ cookiecutter.package_name }}.data.local.dao.CharacterDao
import {{ cookiecutter.package_name }}.data.local.dao.CharacterRemoteKeysDao
import {{ cookiecutter.package_name }}.data.paging.CharacterRemoteMediator
import {{ cookiecutter.package_name }}.data.remote.CharacterRemoteDataSource
import {{ cookiecutter.package_name }}.utils.extensions.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
        private val remoteDataSource: CharacterRemoteDataSource,
        private val remoteKeysDataSource: CharacterRemoteKeysDao,
        private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
            databaseQuery = { localDataSource.getCharacter(id) },
            networkCall = { remoteDataSource.getCharacter(id) },
            saveCallResult = { localDataSource.insertCharacter(it.toEntity()) }
    )

    fun getCharacters() = performGetOperation(
            databaseQuery = { localDataSource.getAllCharacters() },
            networkCall = { remoteDataSource.getCharacters() },
            saveCallResult = { localDataSource.insertAllCharacters(it.results) }
    )

    fun getCharactersPagination(page: Int) = performGetOperation(
            databaseQuery = { localDataSource.getCharactersPagination(page) },
            networkCall = { remoteDataSource.getCharactersPagination(page) },
            saveCallResult = { localDataSource.insertAllCharacters(it.results) }
    )

    @OptIn(ExperimentalPagingApi::class)
    fun getCharactersPager() = Pager(
            config = PagingConfig(20, 2, enablePlaceholders = false),
            remoteMediator = CharacterRemoteMediator(
                    remoteDataSource = remoteDataSource,
                    remoteKeysDataSource = remoteKeysDataSource,
                    localDataSource = localDataSource
            ),
            pagingSourceFactory = { localDataSource.getCharacters() }
    ).flow

}