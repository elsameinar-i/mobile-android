package {{ cookiecutter.package_name }}.ui.main.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.core.BaseFragment
import {{ cookiecutter.package_name }}.databinding.FragmentDetailCharacterBinding
import {{ cookiecutter.package_name }}.ui.main.MainActivityViewModel
import {{ cookiecutter.package_name }}.utils.extensions.logD
import {{ cookiecutter.package_name }}.utils.service.Resource

class CharacterDetailFragment : BaseFragment<MainActivityViewModel, FragmentDetailCharacterBinding>(MainActivityViewModel::class.java) {
    override fun getLayoutRes(): Int = R.layout.fragment_detail_character

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("id")?.let { viewModel.getCharacter(it) }
        viewModel.character.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    logD(it.data.toString())
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.clCharacter.visibility = View.VISIBLE
                    mBinding.character = it.data
                }
                Resource.Status.ERROR -> {
                    mBinding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Failed to parse character", Toast.LENGTH_SHORT).show()
                    navigateToCharactersFragment()
                }
                Resource.Status.LOADING -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.clCharacter.visibility = View.GONE
                }
            }
        })
    }

    private fun navigateToCharactersFragment() {
        findNavController().navigate(R.id.action_characterDetailFragment_to_charactersFragment)
    }
}