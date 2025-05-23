package io.voxkit.kotlin.nanoid

@JsName("crypto")
private external object Crypto {
    fun getRandomValues(bytes: ByteArray)
}

public actual fun platformRandom(): Random =
    object : Random {
        override fun nextBytes(buffer: ByteArray) = Crypto.getRandomValues(buffer)
    }
