package ru.abdt.coroutine.utils

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.abdt.coroutine.screens.questions.QuestionResponseModel
import ru.abdt.coroutine.screens.questions.bottomsheet.DetailQuestionFragment
import kotlin.coroutines.resume

object BottomSheetSuspend {

    class UserThrowable(message: String?) : Throwable(message)

    suspend fun openBottomSheetFragment(activity: AppCompatActivity, model: QuestionResponseModel): Boolean =
            suspendCancellableCoroutine { coroutine ->
                val fragment = DetailQuestionFragment.getInstance(model, { coroutine.resume(it) })
                { coroutine.cancel(UserThrowable("Пользователь свернул экран деталей")) }
                show(activity, fragment)
            }

    private fun show(activity: AppCompatActivity, fragment: BottomSheetDialogFragment) {
        activity.supportFragmentManager.beginTransaction()
            .add(fragment, fragment::class.java.simpleName)
            .commitAllowingStateLoss()
    }
}
