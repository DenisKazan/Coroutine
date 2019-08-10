package ru.abdt.coroutine.screens.questions.view

class QuestionViewModel(val title: String,
                        val ownerName: String,
                        val viewCount: String,
                        val answerCount: Int,
                        val listemer: () -> Unit)
