package {{ cookiecutter.package_name }}.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.databinding.DataBindingUtil
import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.core.BaseActivity
import {{ cookiecutter.package_name }}.databinding.ActivityLoginBinding
import {{ cookiecutter.package_name }}.ui.main.MainActivity
import {{ cookiecutter.package_name }}.utils.service.Resource
import java.util.*

class LoginActivity :
    BaseActivity<LoginViewModel, ActivityLoginBinding>(LoginViewModel::class.java) {

    override fun getLayoutRes(): Int = R.layout.activity_login

    @SuppressLint("SetTextI18n")
    override fun initViewDataBinding(viewModel: LoginViewModel) {
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.viewModel = viewModel

        viewModel.loginResult.observe(this) {
            if (it != null) {
                if (it.status == Resource.Status.SUCCESS) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        // Set text copyright
        binding.tvCopyright.text = "©1953 - " + Calendar.getInstance().get(Calendar.YEAR) + " Bank Indonesia"
    }
}