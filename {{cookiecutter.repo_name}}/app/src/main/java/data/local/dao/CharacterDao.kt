package {{ cookiecutter.package_name }}.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import {{ cookiecutter.package_name }}.data.local.entities.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM characters LIMIT :page * 20,20")
    fun getCharactersPagination(page: Int): LiveData<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): LiveData<Character>

    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<Character>)

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Update
    suspend fun updateCharacter(character: Character)

    @Delete
    suspend fun deleteCharacter(character: Character)
}