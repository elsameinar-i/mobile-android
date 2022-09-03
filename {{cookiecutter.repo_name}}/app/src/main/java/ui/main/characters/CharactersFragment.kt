package {{ cookiecutter.package_name }}.ui.main.characters

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.core.BaseFragment
import {{ cookiecutter.package_name }}.databinding.FragmentCharactersBinding
import {{ cookiecutter.package_name }}.ui.main.MainActivityViewModel
import {{ cookiecutter.package_name }}.utils.extensions.logD
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment: BaseFragment<MainActivityViewModel, FragmentCharactersBinding>(MainActivityViewModel::class.java),
    CharactersPagingAdapter.CharacterItemListener {
    private lateinit var adapter: CharactersPagingAdapter

    override fun getLayoutRes(): Int = R.layout.fragment_characters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup recyclerView
        adapter = CharactersPagingAdapter(this)

        mBinding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvCharacters.adapter = adapter.withLoadStateHeaderAndFooter(
                header = CharactersLoadingStateAdapter(adapter),
                footer = CharactersLoadingStateAdapter(adapter)
        )

        // setup observers
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                logD(loadStates.refresh.toString())
                mBinding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                mBinding.rvCharacters.isVisible = loadStates.refresh !is LoadState.Loading
                if (loadStates.refresh is LoadState.Error) {
                    Toast.makeText(requireContext(), "Failed to Fetch Data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCharacters().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onClickedCharacters(characterId: Int) {
        findNavController().navigate(
                R.id.action_charactersFragment_to_characterDetailFragment,
                bundleOf("id" to characterId)
        )
    }
}