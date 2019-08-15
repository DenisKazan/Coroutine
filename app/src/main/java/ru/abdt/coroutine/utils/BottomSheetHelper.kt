package ru.abdt.coroutine.utils

import androidx.appcompat.app.AppCompatActivity
import ru.abdt.coroutine.screens.questions.QuestionResponseModel

class BottomSheetHelper(private val appCompatActivity: AppCompatActivity) {

    suspend fun showBottomSheet(questionsResponseModel: QuestionResponseModel) =
        BottomSheetSuspend.openBottomSheetFragment(appCompatActivity, questionsResponseModel)
}
