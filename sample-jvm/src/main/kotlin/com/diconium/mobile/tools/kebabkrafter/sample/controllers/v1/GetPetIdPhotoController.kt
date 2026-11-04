package com.diconium.mobile.tools.kebabkrafter.sample.controllers.v1

import com.diconium.mobile.tools.kebabkrafter.sample.CallScope
import com.diconium.mobile.tools.kebabkrafter.sample.PetStoreException
import com.diconium.mobile.tools.kebabkrafter.sample.gen.server.petstore.controllers.v1.GetPetIdPhoto
import io.ktor.http.*
import io.ktor.utils.io.ByteReadChannel

class GetPetIdPhotoController : GetPetIdPhoto {
    override suspend fun CallScope.execute(id: String): ByteReadChannel {
        val bytes = this::class.java.classLoader.getResourceAsStream("petstore/cat.png")
            ?.use { it.readBytes() }
            ?: throw PetStoreException(HttpStatusCode.NotFound)
        return ByteReadChannel(bytes)
    }
}
