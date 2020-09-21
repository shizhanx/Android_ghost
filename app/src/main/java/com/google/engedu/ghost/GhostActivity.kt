/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.engedu.ghost

import android.icu.text.UnicodeMatcher
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import java.util.*

class GhostActivity : AppCompatActivity() {
    private var dictionary: GhostDictionary? = null
    private var userTurn = false
    private val random = Random()
    private var text: TextView? = null
    private var label: TextView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ghost)
        val assetManager = assets
        dictionary = SimpleDictionary(assetManager.open("words.txt"))
        text = findViewById(R.id.ghostText)
        label = findViewById(R.id.gameStatus)
        onStart(null)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_ghost, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     */
    fun onStart(view: View?) {
        userTurn = random.nextBoolean()
        text!!.text = ""
        if (userTurn) {
            label!!.text = USER_TURN
        } else {
            label!!.text = COMPUTER_TURN
            computerTurn()
        }
    }

    fun onChallenge(view: View?) {
        userTurn = false
        val word = text!!.text.toString()
        if (dictionary!!.isWord(word)) label!!.text = "This is a word. You Lose"
        else if (dictionary!!.getAnyWordStartingWith(word) == null) label!!.text = "No such word. You WIN"
        else label!!.text = "There is such a word. You Lose"
    }

    private fun computerTurn() {
        val word = text!!.text.toString()
        if (dictionary!!.isWord(word)) {
            label!!.text = "This is a word. You Lose"
        } else {
            val fullWord = dictionary!!.getAnyWordStartingWith(word)
            if (fullWord == null)
                label!!.text = "No such word. You Lose"
            else {
                text!!.text = fullWord.substring(0, word.length + 1)
                userTurn = true
                label!!.text = USER_TURN
            }
        }
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        val word = text!!.text.toString()
        return if (event.unicodeChar in 97..122 && userTurn) {
            text!!.text = word + event.unicodeChar.toChar()
//            Log.d("sb", word)
            findViewById<TextView>(R.id.gameStatus).text = COMPUTER_TURN
            computerTurn()
            true
        } else super.onKeyUp(keyCode, event)
    }

    companion object {
        private const val COMPUTER_TURN = "Computer's turn"
        private const val USER_TURN = "Your turn"
    }
}