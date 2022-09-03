package {{ cookiecutter.package_name }}.core

abstract class BaseEntity<T: Any> {
    /**
     * Get primary key from BaseEntity
     */
    abstract fun getPrimaryKey(): T

    /**
     * Check entity content equals
     */
    abstract fun contentEquals(other: Any?): Boolean

    override fun equals(other: Any?): Boolean {
        return contentEquals(other)
    }
}