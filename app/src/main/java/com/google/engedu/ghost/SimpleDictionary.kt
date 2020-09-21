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

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.random.Random

class SimpleDictionary(wordListStream: InputStream?) : GhostDictionary {
    private val words: MutableList<String>
    override fun isWord(word: String): Boolean {
        return words.contains(word)
    }

    override fun getAnyWordStartingWith(prefix: String): String? {
        var l = 0
        var r = words.size - 1
        while (l < r) {
            val m = l + (r - l) / 2
            with(words[m]) {
                if (this.length > prefix.length && this.substring(0, prefix.length) == prefix) return this
                if (this < prefix) l = m + 1
                else r = m - 1
            }
        }
        return null
    }

    override fun getGoodWordStartingWith(prefix: String): String? {
        if (prefix == "") return words.random()
        getAnyWordStartingWith(prefix)?.let {
            val index = words.indexOf(it)
            val goodWords = mutableListOf<String>()
            for (i in index until words.size) {
                if (words[i].length>prefix.length && words[i].substring(0, prefix.length) != prefix)
                    break
                if (words[i].length>prefix.length && (words[i].length - prefix.length) % 2 == 0)
                    goodWords.add(words[i])
            }
            for (i in index - 1 downTo 0) {
                if (words[i].length>prefix.length && words[i].substring(0, prefix.length) != prefix)
                    break
                if (words[i].length>prefix.length && (words[i].length - prefix.length) % 2 == 0)
                    goodWords.add(words[i])
            }
            if (goodWords.isEmpty()) return it
            return goodWords.random()
        }
        return null
    }

    init {
        val `in` = BufferedReader(InputStreamReader(wordListStream))
        words = mutableListOf()
        var line: String?
        while (`in`.readLine().also { line = it } != null) {
            val word = line!!.trim { it <= ' ' }
            if (word.length >= GhostDictionary.MIN_WORD_LENGTH) words.add(word)
        }
    }
}