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

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class SimpleDictionary(wordListStream: InputStream?) : GhostDictionary {
    private val words: ArrayList<String?>
    override fun isWord(word: String): Boolean {
        return words.contains(word)
    }

    override fun getAnyWordStartingWith(prefix: String): String? {
        return null
    }

    override fun getGoodWordStartingWith(prefix: String): String? {
        return null
    }

    init {
        val `in` = BufferedReader(InputStreamReader(wordListStream))
        words = ArrayList()
        var line: String? = null
        while (`in`.readLine().also { line = it } != null) {
            val word = line!!.trim { it <= ' ' }
            if (word.length >= GhostDictionary.MIN_WORD_LENGTH) words.add(line!!.trim { it <= ' ' })
        }
    }
}