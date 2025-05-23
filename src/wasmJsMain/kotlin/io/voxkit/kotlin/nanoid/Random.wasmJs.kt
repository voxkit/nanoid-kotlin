package io.voxkit.kotlin.nanoid

import org.khronos.webgl.Int8Array
import org.khronos.webgl.get

public actual fun platformRandom(): Random =
    object : Random {
        override fun nextBytes(buffer: ByteArray) {
            val jsArray = Int8Array(buffer.size)
            getRandomValues(jsArray)
            repeat(buffer.size) { buffer[it] = jsArray[it] }
        }
    }

@JsFun("(array) => { globalThis.crypto.getRandomValues(array) }")
private external fun getRandomValues(bytes: Int8Array)
