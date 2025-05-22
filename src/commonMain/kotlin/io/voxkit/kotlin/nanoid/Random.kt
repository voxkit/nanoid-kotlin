package io.voxkit.kotlin.nanoid

public interface Random {
    public fun nextBytes(buffer: ByteArray)
}

public expect fun platformRandom(): Random