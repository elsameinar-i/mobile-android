package {{ cookiecutter.package_name }}.utils.extensions

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun loadImage(view: AppCompatImageView, url: String?) {
    if (url.isNullOrBlank()) return
    Picasso.get().load(url).into(view)
}