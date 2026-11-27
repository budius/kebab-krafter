package io.github.budius.kebabkrafterkmp

import io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.Pet
import io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.common.Location
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun sayHello(to: String): String = "Hello, $to!"

fun String.capitalise(): String =
    this.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

fun prettyDateOfBirth(dateOfBirth: Instant): String {
    val d = dateOfBirth.toLocalDateTime(TimeZone.currentSystemDefault()).date
    return "🎂 Born ${d.day.toString().padStart(2, '0')}/${
        d.month.number.toString().padStart(2, '0')
    }/${d.year}"
}

fun prettyLocation(loc: Location) = "📍 ${loc.latitude}, ${loc.longitude}"

fun prettyVisualInfo(cat: Pet.Cat) = "${cat.color.name.capitalise()} • ${cat.weight} kg"
