package io.github.budius.kebabkrafterkmp

import android.os.Parcelable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.reflect.full.isSubclassOf

class GeneratedIsParcelableTest {

	@Test
	fun android_module_can_generated_parcelable() {
		listOf(
			io.github.budius.kebabkrafterkmp.gen.android.petstore.models.v1.common.Location::class,
			io.github.budius.kebabkrafterkmp.gen.android.petstore.models.v1.error.Error1::class,
			io.github.budius.kebabkrafterkmp.gen.android.petstore.models.v1.error.Error2::class,
			io.github.budius.kebabkrafterkmp.gen.android.petstore.models.v1.Pet::class,
			io.github.budius.kebabkrafterkmp.gen.android.petstore.models.v1.PetsResponse::class,
			io.github.budius.kebabkrafterkmp.gen.android.petstore.models.v1.PostPetRequest::class,
		).forEach {
			assertTrue(it.isSubclassOf(Parcelable::class))
		}
	}

	@Test
	fun shared_module_can_not_generated_parcelable() {
		listOf(
			io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.common.Location::class,
			io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.error.Error1::class,
			io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.error.Error2::class,
			io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.Pet::class,
			io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.PetsResponse::class,
			io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.PostPetRequest::class,
		).forEach {
			assertFalse(it.isSubclassOf(Parcelable::class))
		}
	}
}
