package io.voxkit.kotlin.nanoid

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NanoIdTest {
    @Test
    fun testGenerateNanoId() {
        val id1 = NanoId.generate()
        val id2 = NanoId.generate()

        println("Generated NanoId 1: $id1")
        println("Generated NanoId 2: $id2")

        assertEquals(NanoId.DEFAULT_SIZE, id1.length, "NanoId length should be 21")
        assertEquals(NanoId.DEFAULT_SIZE, id2.length, "NanoId length should be 21")
        assertNotEquals(id1, id2, "Generated NanoIds should be unique")
    }
}
