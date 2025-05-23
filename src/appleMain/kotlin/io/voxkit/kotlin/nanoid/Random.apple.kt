package io.voxkit.kotlin.nanoid

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.UnsafeNumber
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.Security.SecRandomCopyBytes
import platform.Security.errSecSuccess
import platform.Security.kSecRandomDefault

@OptIn(ExperimentalForeignApi::class, UnsafeNumber::class)
public actual fun platformRandom(): Random = object : Random {
    override fun nextBytes(buffer: ByteArray) {
        if (buffer.isEmpty()) return

        buffer.usePinned { pin ->
            val status = SecRandomCopyBytes(
                rnd = kSecRandomDefault,
                count = buffer.size.convert(),
                bytes = pin.addressOf(0),
            )

            if (status != errSecSuccess) {
                error("Failed to generate random bytes. Error code: $status")
            }
        }
    }
}
