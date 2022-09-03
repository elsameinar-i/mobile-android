package {{ cookiecutter.package_name }}.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import {{ cookiecutter.package_name }}.ui.main.MainActivity
{% if cookiecutter.login_page == "y" %}
import {{ cookiecutter.package_name }}.ui.login.LoginActivity
{% endif %}

class SplashActivity : AppCompatActivity() {
    private val withLogin = '{{ cookiecutter.login_page }}'
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        {% if cookiecutter.login_page == "y" %}
        startActivity(Intent(this, LoginActivity::class.java))
        {% else %}
        startActivity(Intent(this, MainActivity::class.java))
        {% endif %}
        finish()
    }
}