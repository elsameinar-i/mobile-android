package {{ cookiecutter.package_name }}.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import {{ cookiecutter.package_name }}.core.BaseEntity

@Entity(tableName = "characters")
data class Character(
        @PrimaryKey
        val id: Int,
        val created: String,
        val gender: String,
        val image: String,
        val name: String,
        val species: String,
        val status: String,
        val type: String,
        val url: String,
): BaseEntity<Int>() {

        override fun getPrimaryKey(): Int = this.id

        override fun contentEquals(other: Any?): Boolean {
                return when {
                        other == null -> false
                        other::class == Int::class -> this.id == other
                        other::class == Character::class -> {
                                val o = other as Character
                                return with (this) {
                                        id == o.id &&
                                                created == o.created &&
                                                gender == o.gender &&
                                                image == o.image &&
                                                name == o.name &&
                                                species == o.species &&
                                                status == o.status &&
                                                type == o.type &&
                                                url == o.url
                                }
                        }
                        else -> false
                }
        }
}
