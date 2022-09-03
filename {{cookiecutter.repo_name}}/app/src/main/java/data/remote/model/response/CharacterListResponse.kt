package {{ cookiecutter.package_name }}.data.remote.model.response

import {{ cookiecutter.package_name }}.data.local.entities.Character
import {{ cookiecutter.package_name }}.data.remote.model.Info

data class CharacterListResponse(
        val info: Info,
        val results: List<Character>
)
