package com.sigmasoftware.akucherenko.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import com.sigmasoftware.akucherenko.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.random.Random

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var listOfViewBoard1: MutableList<Button>
    private lateinit var listOfViewBoard2: MutableList<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val board1: LinearLayout = binding.board1
        val board2: LinearLayout = binding.board2

        listOfViewBoard1 = createListOfView(board1)
        listOfViewBoard2 = createListOfView(board2)

    }

    private fun createListOfView(vg: View): MutableList<Button> {
        var localList: MutableList<Button> = mutableListOf()
        if (vg is ViewGroup) {
            vg.children.forEach { localList += createListOfView(it) }

        } else {
            if (vg is Button) {
                localList += vg
            }
        }
        return localList
    }

    fun onClickBoard1(view: android.view.View) {
        val button: Button = view as Button
        if (button.text.toString() == "X" || button.text.toString() == "O") return
        button.text = "X"
        listOfViewBoard1.removeAt(listOfViewBoard1.indexOf(button))
        if (listOfViewBoard1.size == 0) return
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            val button = listOfViewBoard1[(Random.nextInt(0, listOfViewBoard1.size - 1))]
            withContext(Dispatchers.IO) { machineStep() }
            button.text = "O"
            listOfViewBoard1.removeAt(listOfViewBoard1.indexOf(button))
        }
    }

    private suspend fun machineStep() {
        val randomStop = Random.nextInt(0, 10) * 1000
        delay(randomStop.toLong())
    }

    fun onClickBoard2(view: android.view.View) {
        val button: Button = view as Button
        if (button.text.toString() == "X" || button.text.toString() == "O") return
        button.text = "X"
        listOfViewBoard2.removeAt(listOfViewBoard2.indexOf(button))
        if (listOfViewBoard2.size == 0) return
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            val button = listOfViewBoard2[(Random.nextInt(0, listOfViewBoard2.size - 1))]
            withContext(Dispatchers.IO) { machineStep() }
            button.text = "O"
            listOfViewBoard2.removeAt(listOfViewBoard2.indexOf(button))
        }
    }
}