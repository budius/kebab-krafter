package io.github.budius.kebabkrafterkmp

import android.os.Bundle
import io.github.budius.kebabkrafterkmp.gen.android.petstore.services.v1.deletePetId
import io.github.budius.kebabkrafterkmp.gen.android.petstore.services.v1.getPetId
import io.ktor.client.HttpClient

suspend fun checkGeneratedParcelable(client: HttpClient) {
	val bundle = Bundle()
	val pet = client.getPetId("123")
	bundle.putParcelable("pet", pet.getOrThrow().body)

	val deletePet = client.deletePetId
	deletePet("123")
}
