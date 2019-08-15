package ru.abdt.coroutine.screens.questions.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.question_detail_view.*
import ru.abdt.coroutine.R
import ru.abdt.coroutine.screens.questions.QuestionResponseModel

class DetailQuestionFragment: BottomSheetDialogFragment() {

    private lateinit var listener: (Boolean) -> Unit
    private lateinit var dismissListener: () -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!isAdded) return super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.question_detail_view, null)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                ?: return@setOnShowListener

            bottomSheet.background = null

            val bottomSheetBehavior = BottomSheetBehavior.from<FrameLayout>(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.setSkipCollapsed(true)
        }
        return  dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData(arguments?.getParcelable(KEY_MODEL))
    }

    private fun setData(model: QuestionResponseModel?) {
        if (model == null) return
        titleView.text = model.title
        scoreView.text = "Score: ${model.score}"
        authorView.text = "by ${model.owner?.displayName}"
    }

    override fun dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss()
        dismissListener.invoke()
    }

    companion object {

        private const val KEY_MODEL = "model"

        fun getInstance(model: QuestionResponseModel, listener: (Boolean) -> Unit, dismissListener: () -> Unit) =
                DetailQuestionFragment().apply {
                    this.listener = listener
                    this.dismissListener = dismissListener
                    arguments = Bundle().apply {
                        putParcelable(KEY_MODEL, model)
                    }
                }
    }
}
