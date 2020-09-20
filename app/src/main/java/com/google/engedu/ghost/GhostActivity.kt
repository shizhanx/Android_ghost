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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import java.util.*

class GhostActivity : AppCompatActivity() {
    private val dictionary: GhostDictionary? = null
    private var userTurn = false
    private val random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ghost)
        val assetManager = assets
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
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
     * @return true
     */
    fun onStart(view: View?): Boolean {
        userTurn = random.nextBoolean()
        val text = findViewById<View>(R.id.ghostText) as TextView
        text.text = ""
        val label = findViewById<View>(R.id.gameStatus) as TextView
        if (userTurn) {
            label.text = USER_TURN
        } else {
            label.text = COMPUTER_TURN
            computerTurn()
        }
        return true
    }

    private fun computerTurn() {
        val label = findViewById<View>(R.id.gameStatus) as TextView
        // Do computer turn stuff then make it the user's turn again
        userTurn = true
        label.text = USER_TURN
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
        return super.onKeyUp(keyCode, event)
    }

    companion object {
        private const val COMPUTER_TURN = "Computer's turn"
        private const val USER_TURN = "Your turn"
    }
}