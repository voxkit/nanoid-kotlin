package io.voxkit.kotlin.nanoid

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.usePinned
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fread

@OptIn(ExperimentalForeignApi::class)
public actual fun platformRandom(): Random = object : Random {
    override fun nextBytes(buffer: ByteArray) {
        if (buffer.isEmpty()) return

        buffer.usePinned { pin ->
            val file = fopen("/dev/urandom", "rb")
            if (file == null) return

            fread(
                __ptr = pin.addressOf(0),
                __size = 1.convert(),
                __n = buffer.size.convert(),
                __stream = file,
            )
            fclose(file)
        }
    }
}