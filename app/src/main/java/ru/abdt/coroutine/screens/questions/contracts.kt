package ru.abdt.coroutine.screens.questions

import ru.abdt.coroutine.screens.questions.QueryConstants.ASC
import ru.abdt.coroutine.screens.questions.QueryConstants.PAGE_SIZE
import ru.abdt.coroutine.screens.questions.QueryConstants.SITE
import ru.abdt.coroutine.screens.questions.QueryConstants.VOTES

interface IQuestionsRepository {

    suspend fun getQuestions(pageSize: Int = PAGE_SIZE, order: String = ASC, sort: String = VOTES, site: String = SITE)
}

object QueryConstants {

    const val PAGE_SIZE = 20
    const val ASC = "asc"
    const val VOTES = "votes"
    const val SITE = "stackoverflow"
}
