package ru.abdt.coroutine.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.question_row.view.*
import ru.abdt.coroutine.R

class QuestionView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(getContext(), R.layout.question_row, this)
    }

    var title: CharSequence?
        get() = titleView.text
        set(value) {
            titleView.text = value
        }

    var owner: CharSequence?
        get() = ownerName.text
        set(value) {
            ownerName.text = value
        }

    var view: CharSequence?
        get() = viewCount.text
        set(value) {
            viewCount.text = value
        }

    var answer: CharSequence?
        get() = answerCount.text
        set(value) {
            answerCount.text = value
        }
}
