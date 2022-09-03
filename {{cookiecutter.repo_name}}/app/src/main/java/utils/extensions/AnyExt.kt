package {{ cookiecutter.package_name }}.utils.extensions

import timber.log.Timber

fun Any.logE(message: String) = Timber.tag(this::class.java.simpleName).e(message)

fun Any.logD(message: String) = Timber.tag(this::class.java.simpleName).d(message)

fun Any.logV(message: String) = Timber.tag(this::class.java.simpleName).v(message)

fun Any.logW(message: String) = Timber.tag(this::class.java.simpleName).w(message)

fun Any.logI(message: String) = Timber.tag(this::class.java.simpleName).i(message)

fun Any.emptyString() = ""