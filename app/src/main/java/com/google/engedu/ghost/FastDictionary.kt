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

class FastDictionary(wordListStream: InputStream?) : GhostDictionary {
    private val root: TrieNode
    override fun isWord(word: String): Boolean {
        return root.isWord(word)
    }

    override fun getAnyWordStartingWith(prefix: String): String? {
        return root.getAnyWordStartingWith(prefix)
    }

    override fun getGoodWordStartingWith(prefix: String): String? {
        return root.getGoodWordStartingWith(prefix)
    }

    init {
        val `in` = BufferedReader(InputStreamReader(wordListStream))
        root = TrieNode()
        var line: String?
        while (`in`.readLine().also { line = it } != null) {
            val word = line!!.trim { it <= ' ' }
            if (word.length >= GhostDictionary.MIN_WORD_LENGTH) root.add(word)
        }
    }
}