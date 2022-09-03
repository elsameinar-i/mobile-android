package {{ cookiecutter.package_name }}.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@ExperimentalPagingApi
abstract class BaseRemoteMediator<K: Any, V: BaseEntity<K>, R: BaseRemoteKeys>(
        private val findRepoId: suspend (repoId: K) -> R?
): RemoteMediator<K, V>() {
    val STARTING_PAGE_INDEX = 1

    /**
     * Launch remote refresh as soon as paging starts and do not trigger remote prepend or
     * append until refresh has succeeded. In cases where we don't mind showing out-of-date,
     * cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
     * triggering remote refresh.
     */
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    /**
     * Load remote mediator
     */
    override suspend fun load(loadType: LoadType, state: PagingState<K, V>): MediatorResult {
        val (page, remoteKeys) = findPage(loadType, state)
        if (page == null) {
            return MediatorResult.Success(remoteKeys != null)
        }
        return loadPagination(loadType, state, page)
    }

    /**
     * Implementation of other remote mediator
     */
    abstract suspend fun loadPagination(loadType: LoadType, state: PagingState<K, V>, page: Int): MediatorResult

    /**
     * find current page state
     * @see LoadType.PREPEND ->
     *      If remoteKeys is null, that means the refresh result is not in the database yet.
     *      We can return Success with `endOfPaginationReached = false` because Paging
     *      will call this method again if RemoteKeys becomes non-null.
     *      If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
     *      the end of pagination for prepend.
     * @see LoadType.APPEND ->
     *      If remoteKeys is null, that means the refresh result is not in the database yet.
     *      We can return Success with `endOfPaginationReached = false` because Paging
     *      will call this method again if RemoteKeys becomes non-null.
     *      If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
     *      the end of pagination for append.
     * @see LoadType.REFRESH ->
     *      refresh page index
     */
    private suspend fun findPage(loadType: LoadType, state: PagingState<K, V>): Pair<Int?, R?> {
        return when (loadType) {
            LoadType.PREPEND -> getRemoteKeyForFirstItem(state).run { this?.getPrev() to this }
            LoadType.APPEND -> getRemoteKeyForLastItem(state).run { this?.getNext() to this }
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state).run {
                (this?.getNext()?.minus(1) ?: STARTING_PAGE_INDEX) to this
            }
        }
    }

    /**
     * Get the last page that was retrieved, that contained items.
     * From that last page, get the last item
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<K, V>): R? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            // Get the remote keys of the last item retrieved
            findRepoId.invoke(repo.getPrimaryKey())
        }
    }

    /**
     * Get the first page that was retrieved, that contained items.
     * From that first page, get the first item
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<K, V>): R? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
            // Get the remote keys of the first items retrieved
            findRepoId.invoke(repo.getPrimaryKey())
        }
    }

    /**
     * The paging library is trying to load data after the anchor position
     * Get the item closest to the anchor position
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<K, V>): R? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.getPrimaryKey()?.let { repoId ->
                findRepoId.invoke(repoId)
            }
        }
    }

}