package {{ cookiecutter.package_name }}.ui.main.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.databinding.ItemNetworkStateBinding
import {{ cookiecutter.package_name }}.utils.extensions.logD

class CharactersLoadingStateAdapter(
        private val adapter: CharactersPagingAdapter
): LoadStateAdapter<CharactersLoadingStateAdapter.NetworkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        logD(loadState.toString())
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): NetworkStateItemViewHolder =
            NetworkStateItemViewHolder(
                    binding = ItemNetworkStateBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false)),
                    retryCallback = { adapter.retry() }
            )

    class NetworkStateItemViewHolder(
            private val binding: ItemNetworkStateBinding,
            private val retryCallback: () -> Unit
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                tvErrorMsg.isVisible =
                        !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                tvErrorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}