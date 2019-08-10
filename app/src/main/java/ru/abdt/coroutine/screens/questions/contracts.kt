package ru.abdt.coroutine.screens.questions

import com.google.gson.annotations.SerializedName
import ru.abdt.coroutine.screens.questions.QueryConstants.ASC
import ru.abdt.coroutine.screens.questions.QueryConstants.PAGE_SIZE
import ru.abdt.coroutine.screens.questions.QueryConstants.SITE
import ru.abdt.coroutine.screens.questions.QueryConstants.VOTES

interface IQuestionsRepository {

    suspend fun getQuestions(pageSize: Int = PAGE_SIZE, order: String = ASC, sort: String = VOTES, site: String = SITE): QuestionsResponseModel
}

object QueryConstants {

    const val PAGE_SIZE = 20
    const val ASC = "asc"
    const val VOTES = "votes"
    const val SITE = "stackoverflow"
}

class QuestionsResponseModel(
    @SerializedName("items")
    val items: MutableList<QuestionResponseModel>? = null
)

class QuestionResponseModel(
    @SerializedName("tags")
    val tags: MutableList<String>? = null,
    @SerializedName("owner")
    val owner: Ownwer? = null,
    @SerializedName("is_answered")
    val isAnswered: Boolean? = null,
    @SerializedName("view_count")
    val viewCount: String? = null,
    @SerializedName("answer_count")
    val answerCount: Int? = null,
    @SerializedName("score")
    val score: Int? = null,
    @SerializedName("title")
    val title: String? = null
)

class Ownwer(
    @SerializedName("reputation")
    val reputation: Int? = null,
    @SerializedName("user_id")
    val userId: String? = null,
    @SerializedName("display_name")
    val displayName: String? = null,
    @SerializedName("link")
    val link: String? = null
)

