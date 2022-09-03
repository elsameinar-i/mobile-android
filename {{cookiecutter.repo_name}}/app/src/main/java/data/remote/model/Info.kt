package {{ cookiecutter.package_name }}.data.remote.model

data class Info(
        val count: Int,
        val next: String?,
        val pages: Int,
        val prev: String?
)
