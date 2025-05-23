package io.voxkit.kotlin.nanoid

/**
 * Platform-agnostic interface for generating random bytes.
 *
 * Implementations provide cryptographically secure random data
 * using platform-specific APIs.
 */
public interface Random {
    /**
     * Fills the given [buffer] with random bytes.
     */
    public fun nextBytes(buffer: ByteArray)
}

/**
 * Returns a platform-specific implementation of [Random].
 *
 * - JVM/Android: Uses `java.security.SecureRandom`.
 * - JS/WasmJS: Uses `crypto.getRandomValues`.
 * - Apple (iOS/macOS/watchOS): Uses `SecRandomCopyBytes`.
 * - Linux: Reads from `/dev/urandom`.
 */
public expect fun platformRandom(): Random
