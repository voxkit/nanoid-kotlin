package io.voxkit.kotlin.nanoid

import java.security.SecureRandom

public actual fun platformRandom(): Random = object : Random {
    private val random = SecureRandom()

    override fun nextBytes(buffer: ByteArray) = random.nextBytes(buffer)
}