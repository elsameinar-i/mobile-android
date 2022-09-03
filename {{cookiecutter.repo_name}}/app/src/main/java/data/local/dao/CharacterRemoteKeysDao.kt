package {{ cookiecutter.package_name }}.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import {{ cookiecutter.package_name }}.data.local.entities.CharacterRemoteKeys

@Dao
interface CharacterRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CharacterRemoteKeys>)

    @Query("SELECT * FROM characters_remote_key WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): CharacterRemoteKeys?

    @Query("DELETE FROM characters_remote_key")
    suspend fun clearRemoteKeys()

}