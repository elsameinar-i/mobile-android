package {{ cookiecutter.package_name }}.core

abstract class BaseRemoteKeys {

    abstract fun getPrev(): Int?

    abstract fun getNext(): Int?

}