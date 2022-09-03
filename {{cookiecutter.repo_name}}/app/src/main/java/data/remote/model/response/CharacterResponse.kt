package {{ cookiecutter.package_name }}.data.remote.model.response

import {{ cookiecutter.package_name }}.data.local.entities.Character

data class CharacterResponse(
        val id: Int,
        val created: String,
        val gender: String,
        val image: String,
        val name: String,
        val species: String,
        val status: String,
        val type: String,
        val url: String,
) {
    fun toEntity(): Character {
        return Character(id, created, gender, image, name, species, status, type, url)
    }
}
