@file:OptIn(ExperimentalForeignApi::class)
@file:Suppress("ktlint:standard:no-wildcard-imports")

package io.voxkit.kotlin.nanoid

import kotlinx.cinterop.*
import platform.windows.*

public actual fun platformRandom(): Random =
    object : Random {
        override fun nextBytes(buffer: ByteArray) {
            if (buffer.isEmpty()) return

            buffer.usePinned { pin ->
                val ptr = pin.addressOf(0)
                val status =
                    bcryptGenRandom(
                        null,
                        ptr.reinterpret(),
                        buffer.size.convert(),
                        BCRYPT_USE_SYSTEM_PREFERRED_RNG.convert(),
                    )
                check(status == 0) { "BCryptGenRandom failed with status $status" }
            }
        }
    }

private val bcrypt by lazy { LoadLibraryA("Bcrypt.dll") }

private val bcryptGenRandom by lazy {
    GetProcAddress(bcrypt, "BCryptGenRandom")
        ?.reinterpret<CFunction<(BCRYPT_ALG_HANDLE?, PUCHAR?, ULONG, ULONG) -> Int>>()
        ?: error("No BCryptGenRandom function found in Bcrypt.dll")
}
