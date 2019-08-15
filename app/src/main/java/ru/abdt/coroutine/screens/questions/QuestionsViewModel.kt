package ru.abdt.coroutine.screens.questions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.abdt.coroutine.screens.questions.view.QuestionViewModel
import ru.abdt.coroutine.utils.BottomSheetHelper
import ru.abdt.coroutine.utils.CustomViewModel
import timber.log.Timber

class QuestionsViewModel(private val repository: IQuestionsRepository,
                         private val bottomSheetHelper: BottomSheetHelper): CustomViewModel(), CoroutineScope by MainScope() {

    val onListAdapterItemsRecieved by lazy { MutableLiveData<QuestionViewModel>() }

    override fun onAttachView() {
        jobs += launch {
            val result = runCatching { repository.getQuestions() }
            result.fold( onSuccess = { onQuestionsRecevieved(it) },
                onFailure = {
                    Timber.e(it)
                })
        }
    }

    private fun onQuestionsRecevieved(model: QuestionsResponseModel) {
        if (model.items.isNullOrEmpty()) return
        for (question in model.items) {
            onListAdapterItemsRecieved.value = mappingToViewModel(question)
        }
    }

    private fun mappingToViewModel(question: QuestionResponseModel): QuestionViewModel {
        return QuestionViewModel(question.title ?: "", question.owner?.displayName ?: "",
            question.viewCount ?: "", question.answerCount ?: 0) { onQuestionClick(question) }
    }

    private fun onQuestionClick(question: QuestionResponseModel) {
        jobs += launch {
            val result = runCatching { bottomSheetHelper.showBottomSheet(question) }
            result.fold(onSuccess = {
                checkChoice(it)
            }, onFailure = {
                Timber.e(it)
            })
        }
    }

    private fun checkChoice(it: Boolean) {

    }

    class Factory(private val repository: IQuestionsRepository,
                  private val bottomSheetHelper: BottomSheetHelper) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return QuestionsViewModel(repository, bottomSheetHelper) as T
        }

    }
}
