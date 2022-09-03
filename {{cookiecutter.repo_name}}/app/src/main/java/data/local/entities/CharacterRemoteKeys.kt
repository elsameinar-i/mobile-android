package {{ cookiecutter.package_name }}.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import {{ cookiecutter.package_name }}.core.BaseRemoteKeys

@Entity(tableName = "characters_remote_key")
data class CharacterRemoteKeys(
        @PrimaryKey
        val repoId: Long,
        val prevKey: Int?,
        val nextKey: Int?
): BaseRemoteKeys() {
        override fun getPrev(): Int? = prevKey

        override fun getNext(): Int? = nextKey
}