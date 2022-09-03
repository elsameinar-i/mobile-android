package {{ cookiecutter.package_name }}.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.room.Transaction
import {{ cookiecutter.package_name }}.core.BaseRemoteMediator
import {{ cookiecutter.package_name }}.data.local.dao.CharacterDao
import {{ cookiecutter.package_name }}.data.local.dao.CharacterRemoteKeysDao
import {{ cookiecutter.package_name }}.data.local.entities.Character
import {{ cookiecutter.package_name }}.data.local.entities.CharacterRemoteKeys
import {{ cookiecutter.package_name }}.data.remote.CharacterRemoteDataSource
import {{ cookiecutter.package_name }}.utils.service.Resource
import javax.inject.Inject

@ExperimentalPagingApi
class CharacterRemoteMediator @Inject constructor(
        private val remoteDataSource: CharacterRemoteDataSource,
        private val remoteKeysDataSource: CharacterRemoteKeysDao,
        private val localDataSource: CharacterDao
): BaseRemoteMediator<Int, Character, CharacterRemoteKeys>(remoteKeysDataSource::remoteKeysRepoId) {

    @Transaction
    override suspend fun loadPagination(loadType: LoadType, state: PagingState<Int, Character>, page: Int): MediatorResult {
        val response = remoteDataSource.getCharactersPagination(page)
        if (response.status != Resource.Status.SUCCESS) {
            return MediatorResult.Error(Exception(response.message))
        }
        val data = response.data ?:
            return MediatorResult.Error(Exception("Response is null"))
        val results = data.results

        val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
        val nextKey = if (results.isEmpty()) null else page + 1
        val keys = results.map { CharacterRemoteKeys(
                repoId = it.id.toLong(),
                prevKey = prevKey,
                nextKey = nextKey)
        }
        // clear all
        if (loadType == LoadType.REFRESH) {
            localDataSource.clearCharacters()
            remoteKeysDataSource.clearRemoteKeys()
        }
        localDataSource.insertAllCharacters(results)
        remoteKeysDataSource.insertAll(keys)
        return MediatorResult.Success(nextKey == null)
    }
}