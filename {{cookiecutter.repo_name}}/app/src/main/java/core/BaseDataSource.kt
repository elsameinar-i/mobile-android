package {{ cookiecutter.package_name }}.core

import {{ cookiecutter.package_name }}.utils.extensions.logD
import {{ cookiecutter.package_name }}.utils.service.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            logD(response.toString())
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        logD(message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}