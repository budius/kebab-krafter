package io.github.budius.kebabkrafterkmp

import io.ktor.utils.io.ByteReadChannel
import org.junit.Test
import java.io.InputStream
import kotlin.test.assertIs

class GetPetIdPdfResponseTest {

    @Test
    fun generated_for_android_are_InputStream() {
        val response = io.github.budius.kebabkrafterkmp.gen.android.petstore.services.v1.GetPetIdPdfResponse(
            headerContentDisposition = null,
            body = byteArrayOf().inputStream(),
        )

        assertIs<InputStream>(response.body)
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
