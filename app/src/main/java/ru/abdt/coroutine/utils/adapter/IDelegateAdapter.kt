package ru.abdt.coroutine.utils.adapter

interface IDelegateAdapter {

    val items: List<Any>

    fun getItem(position: Int): Any

    fun getItemPosition(item: Any): Int

    fun setItems(items: List<*>)

    fun addItem(item: Any)

    fun clearItems()

    fun indexOf(item: Any?): Int

    fun removeItem(item: Any)

    fun removeItemByPosition(pos: Int)

    fun addItem(pos: Int, item: Any)

    fun removeItems(items: List<*>)

    fun changeItem(item: Any)

    fun changeItemByPosition(pos: Int, item: Any)

    fun addItems(items: List<*>)

    fun addItems(pos: Int, items: List<*>)
}
