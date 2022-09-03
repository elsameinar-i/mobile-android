package {{ cookiecutter.package_name }}.ui.main

import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.core.BaseActivity
import {{ cookiecutter.package_name }}.databinding.ActivityMainBinding
import androidx.databinding.DataBindingUtil
{% if cookiecutter.with_menu == 'y' %}
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
{% endif %}

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(MainActivityViewModel::class.java) {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun initViewDataBinding(viewModel: MainActivityViewModel) {
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.viewModel = viewModel

        binding.toolbar.title = getString(R.string.label_main)

        {% if cookiecutter.with_menu == 'y' %}
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        binding.bottomnavMenu.setupWithNavController(navHostFragment.navController)
        {% endif %}
    }
}
