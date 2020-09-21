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

import java.util.*

class TrieNode {
    val children: HashMap<Char, TrieNode> = HashMap()
    private var isWord: Boolean = false
    fun add(s: String) {
        if (s.isEmpty()) isWord = true
        else {
            children.putIfAbsent(s[0], TrieNode())
            children[s[0]]!!.add(s.substring(1))
        }
    }

    fun isWord(s: String): Boolean {
        if (s.isEmpty()) return isWord
        if (!children.containsKey(s[0])) return false
        return children.getValue(s[0]).isWord(s.substring(1))
    }

    fun getAnyWordStartingWith(s: String): String? {
        if (s.isNotEmpty()) {
            if (children.containsKey(s[0])){
                children.getValue(s[0]).getAnyWordStartingWith(s.substring(1)).apply {
                    return if (this == null) null else s[0] + this
                }
            } else return null
        } else {
            return if (children.isEmpty()) null else children.keys.random().toString()
        }
    }

    fun getGoodWordStartingWith(s: String): String? {
        return null
    }

}