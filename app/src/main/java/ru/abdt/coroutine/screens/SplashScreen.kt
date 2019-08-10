package ru.abdt.coroutine.screens

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.abdt.coroutine.R
import ru.abdt.coroutine.screens.questions.QuestionsActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ openListActivity() }, DELAY)
    }

    private fun openListActivity() {
        startActivity(QuestionsActivity.getInstance(this))
    }

    companion object {
        private const val DELAY = 1500L
    }
}
