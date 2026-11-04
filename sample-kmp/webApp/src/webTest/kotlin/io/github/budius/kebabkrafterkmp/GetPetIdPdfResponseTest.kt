package io.github.budius.kebabkrafterkmp

import io.ktor.utils.io.ByteReadChannel
import kotlin.test.Test
import kotlin.test.assertIs

class GetPetIdPdfResponseTest {

    @Test
    fun generated_for_web_are_ByteReadChannel() {
        val response = io.github.budius.kebabkrafterkmp.gen.web.petstore.services.v1.GetPetIdPdfResponse(
            headerContentDisposition = null,
            body = ByteReadChannel(byteArrayOf()),
        )

        assertIs<ByteReadChannel>(response.body)
    }

    @Test
    fun generated_for_shared_are_ByteReadChannel() {
        val response = io.github.budius.kebabkrafterkmp.gen.shared.petstore.services.v1.GetPetIdPdfResponse(
            headerContentDisposition = null,
            body = ByteReadChannel(byteArrayOf()),
        )

        assertIs<ByteReadChannel>(response.body)
    }
}
