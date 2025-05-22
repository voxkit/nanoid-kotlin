package io.voxkit.kotlin.nanoid

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NanoIdTest {
    @Test
    fun testGenerateNanoId() {
        val id1 = NanoId.generate()
        val id2 = NanoId.generate()
        assertEquals(21, id1.length, "NanoId length should be 21")
        assertEquals(21, id2.length, "NanoId length should be 21")
        assertNotEquals(id1, id2, "Generated NanoIds should be unique")
    }
}