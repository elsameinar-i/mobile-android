package {{ cookiecutter.package_name }}.ui.main.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import {{ cookiecutter.package_name }}.R
import {{ cookiecutter.package_name }}.core.BaseDiffCallback
import {{ cookiecutter.package_name }}.core.BasePagingAdapter
import {{ cookiecutter.package_name }}.data.local.entities.Character
import {{ cookiecutter.package_name }}.databinding.ItemCharacterBinding

class CharactersPagingAdapter(val listener: CharacterItemListener): BasePagingAdapter<Character>(BaseDiffCallback()) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding =
            DataBindingUtil.inflate<ItemCharacterBinding>(LayoutInflater.from(parent.context), R.layout.item_character, parent, false)

    override fun bind(binding: ViewDataBinding, position: Int) {
        val selectedCharacter = getItem(position) ?: return

        val itemBinding = binding as ItemCharacterBinding
        itemBinding.character = selectedCharacter
        itemBinding.root.setOnClickListener {
            listener.onClickedCharacters(selectedCharacter.id)
        }
    }

    interface CharacterItemListener {
        fun onClickedCharacters(characterId: Int)
    }
}