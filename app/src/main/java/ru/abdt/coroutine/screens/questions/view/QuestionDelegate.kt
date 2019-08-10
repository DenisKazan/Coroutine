package ru.abdt.coroutine.screens.questions.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.abdt.coroutine.ui.QuestionView
import ru.abdt.coroutine.utils.adapter.DelegateAdapter

class QuestionDelegate : DelegateAdapter.Delegate<QuestionViewModel, QuestionViewHolder>() {
    override fun createViewHolder(parent: ViewGroup): QuestionViewHolder {
        val view = QuestionView(parent.context)
        view.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return QuestionViewHolder(view)
    }

    override fun bind(viewHolder: QuestionViewHolder, model: QuestionViewModel) {
        viewHolder.bind(model)
    }
}

class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(model: QuestionViewModel) {
        val view = itemView as QuestionView
        view.title = model.title
        view.answer = model.answerCount.toString()
        view.owner = model.ownerName
        view.view = model.viewCount
        view.setOnClickListener { model.listemer.invoke() }
    }
}
