package ru.abdt.coroutine.screens.questions

import ru.gildor.coroutines.retrofit.await
import javax.inject.Inject

class QuestionsRepository
@Inject constructor(private val api: QuestionsApi): IQuestionsRepository {

    override suspend fun getQuestions(pageSize: Int, order: String, sort: String, site: String): QuestionsResponseModel {
        return api.getQuestions(pageSize, order, sort, site).await()
    }
}
