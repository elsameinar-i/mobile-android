package {{ cookiecutter.package_name }}.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import {{ cookiecutter.package_name }}.data.local.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    {% if cookiecutter.with_list == "y" %}
    @Singleton
    @Provides
    fun provideDatabase(context: Context) = AppDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Singleton
    @Provides
    fun provideCharacterRemoteKeysDao(db: AppDatabase) = db.characterRemoteKeysDao()
    {% endif %}
}