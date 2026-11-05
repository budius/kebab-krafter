package com.diconium.mobile.tools.kebabkrafter.sample.controllers.v1

import com.diconium.mobile.tools.kebabkrafter.sample.CallScope
import com.diconium.mobile.tools.kebabkrafter.sample.PetStoreException
import com.diconium.mobile.tools.kebabkrafter.sample.gen.server.petstore.controllers.v1.GetPetIdPhoto
import io.ktor.http.*
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.toByteReadChannel

class GetPetIdPhotoController : GetPetIdPhoto {
    override suspend fun CallScope.execute(id: String): ByteReadChannel =
        this::class.java.classLoader.getResourceAsStream("petstore/cat.png")?.toByteReadChannel()
            ?: throw PetStoreException(HttpStatusCode.NotFound)
}
