package ru.abdt.coroutine.screens.questions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_main.*
import ru.abdt.coroutine.R

class QuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    companion object {

        fun getInstance(context: Context): Intent = Intent(context, QuestionsActivity::class.java)
    }
}
