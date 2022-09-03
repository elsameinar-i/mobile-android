package {{ cookiecutter.package_name }}.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
{% if cookiecutter.with_list == "y" %}
import {{ cookiecutter.package_name }}.data.local.dao.CharacterDao
import {{ cookiecutter.package_name }}.data.local.dao.CharacterRemoteKeysDao
import {{ cookiecutter.package_name }}.data.local.entities.Character
import {{ cookiecutter.package_name }}.data.local.entities.CharacterRemoteKeys

@Database(entities = [
    Character::class,
    CharacterRemoteKeys::class
], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "example-db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
{% else %}
abstract class AppDatabase : RoomDatabase() {}
{% endif %}
