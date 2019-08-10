package ru.abdt.coroutine.screens.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.abdt.coroutine.R
import ru.abdt.coroutine.dagger.ComponentInjector
import ru.abdt.coroutine.dagger.ComponentProvider
import ru.abdt.coroutine.screens.questions.di.QuestionsComponent
import ru.abdt.coroutine.screens.questions.view.QuestionDelegate
import ru.abdt.coroutine.screens.questions.view.QuestionViewModel
import ru.abdt.coroutine.utils.adapter.DelegateAdapter
import ru.abdt.coroutine.utils.getViewModel
import ru.abdt.coroutine.utils.safeObserve
import javax.inject.Inject

class QuestionsActivity : AppCompatActivity(), ComponentProvider<QuestionsComponent> {

    override val component: QuestionsComponent by lazy {
        QuestionsComponent.Initializer.init(this)
    }

    @Inject
    lateinit var factory: QuestionsViewModel.Factory

    private val vm by lazy { getViewModel<QuestionsViewModel>(factory) }

    private val adapter = DelegateAdapter.Builder()
        .addDelegate(QuestionViewModel::class.java, QuestionDelegate())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initRecycler()
        vm.onAttachView()
        setListeners()
    }

    private fun setListeners() {
        vm.onListAdapterItemsRecieved.safeObserve(this) {
            adapter.addItem(it)
        }
    }

    private fun initRecycler() {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
    }

    companion object {

        fun getInstance(context: Context): Intent = Intent(context, QuestionsActivity::class.java)
    }
}
