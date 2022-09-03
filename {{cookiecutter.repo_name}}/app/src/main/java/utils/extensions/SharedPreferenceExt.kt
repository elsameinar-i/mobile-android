package {{ cookiecutter.package_name }}.utils.extensions

import android.content.SharedPreferences
import {{ cookiecutter.package_name }}.utils.Constants


/**
 * Function to save auth token
 */
fun SharedPreferences.saveAuthToken(token: String) {
    val editor = this.edit()
    editor.putString(Constants.KEY_AUTH_TOKEN, token)
    editor.apply()
}

/**
 * Function to fetch auth token
 */
fun SharedPreferences.fetchAuthToken(): String? {
    return this.getString(Constants.KEY_AUTH_TOKEN, null)
}

/**
 * Function to clear sharedPreferences
 */
fun SharedPreferences.clear() {
    val editor = this.edit()
    editor.clear()
    editor.apply()
}