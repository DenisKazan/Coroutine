package ru.abdt.coroutine.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import java.io.IOException
import java.io.InputStream

interface ResourcesProvider {

    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String

    fun getStringArray(@ArrayRes arrayId: Int): Array<String>

    fun getQuantityString(@PluralsRes id: Int, quantity: Int): String

    fun getDrawable(@DrawableRes id: Int): Drawable?

    fun getPermissionLocalizedName(permissionName: String): CharSequence

    fun getStream(uri: Uri): InputStream?

    fun getColor(@ColorRes id: Int): Int

    class AndroidResourcesProvider(private val context: Context) : ResourcesProvider {

        override fun getStringArray(arrayId: Int): Array<String> {
            return context.resources.getStringArray(arrayId)
        }

        override fun getDrawable(id: Int): Drawable? {
            return AppCompatResources.getDrawable(context, id)
        }

        override fun getString(id: Int): String {
            return context.getString(id)
        }

        override fun getString(id: Int, vararg formatArgs: Any): String {
            return context.getString(id, *formatArgs)
        }

        override fun getPermissionLocalizedName(permissionName: String): CharSequence =
            context.packageManager.getPermissionGroupInfo(permissionName, 0)
                .loadLabel(context.packageManager)

        override fun getQuantityString(id: Int, quantity: Int): String {
            return context.resources.getQuantityString(id, quantity, quantity)
        }

        @Throws(IOException::class)
        override fun getStream(uri: Uri): InputStream? = context.contentResolver.openInputStream(uri)

        override fun getColor(id: Int): Int {
            return ContextCompat.getColor(context, id)
        }
    }
}
