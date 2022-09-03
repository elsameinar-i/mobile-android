package {{ cookiecutter.package_name }}.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import {{ cookiecutter.package_name }}.data.local.entities.Character
import {{ cookiecutter.package_name }}.data.remote.CharacterService
import {{ cookiecutter.package_name }}.utils.extensions.logD
import kotlinx.coroutines.delay

class CharacterPagingDataSource(val service: CharacterService): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        delay(3000L)
        val pageNumber = params.key ?: 1
        return try {
            val response = service.getCharactersPagination(pageNumber).body()
            val pagedResponse = response?.info
            logD(pagedResponse.toString())
            val data = response?.results
            logD(data.toString())

            var nextPageNumber: Int? = null
            if (pagedResponse != null) {
                val uri = Uri.parse(pagedResponse.next)
                val nextQueryPage = uri.getQueryParameter("page")
                nextPageNumber = nextQueryPage?.toInt()
            }

            LoadResult.Page(
                    data = data.orEmpty(),
                    prevKey = null,
                    nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}