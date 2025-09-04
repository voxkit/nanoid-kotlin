package io.voxkit.nanoid

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
 * - Apple (iOS/macOS/watchOS): Uses `SecRandomCopyBytes`.
 * - JS/WasmJS: Uses `crypto.getRandomValues`.
 * - Linux: Reads from `/dev/urandom`.
 * - Windows: Uses `BCryptGenRandom` from `Bcrypt.dll`.
 */
public expect fun platformRandom(): Random
