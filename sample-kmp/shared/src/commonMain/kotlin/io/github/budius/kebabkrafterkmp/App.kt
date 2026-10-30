package io.github.budius.kebabkrafterkmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.Pet
import io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.common.Color
import io.github.budius.kebabkrafterkmp.gen.shared.petstore.models.v1.common.Location
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

@Composable
@Preview
fun App() {
	MaterialTheme {
		Column(
			modifier = Modifier
				.background(MaterialTheme.colorScheme.primaryContainer)
				.safeContentPadding()
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			val cat = Pet.Cat(
				id = "0d8f4e7b1a33",
				name = "Loki",
				color = Color.MIXED,
				weight = 4.2f,
				dateOfBirth = Instant.fromEpochSeconds(1274348528),
				location = Location(latitude = 48.904, longitude = 2.306),
			)
			Card(
				colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
				elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
				shape = RoundedCornerShape(20.dp),
			) {
				Column(
					modifier = Modifier.padding(24.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.spacedBy(8.dp),
				) {
					Text("🐱", style = MaterialTheme.typography.displayMedium)
					Text(
						cat.name,
						style = MaterialTheme.typography.headlineMedium,
						fontWeight = FontWeight.Bold
					)
					Text(
						"${cat.color.name.lowercase().replace('_', ' ')} • ${cat.weight} kg",
						style = MaterialTheme.typography.titleMedium,
						color = MaterialTheme.colorScheme.primary,
					)
					cat.dateOfBirth?.let {
						val d = it.toLocalDateTime(TimeZone.currentSystemDefault()).date
						Text(
							"🎂 Born ${d.day.toString().padStart(2, '0')}/${
								d.month.number.toString().padStart(2, '0')
							}/${d.year}",
							style = MaterialTheme.typography.bodyMedium,
						)
					}
					cat.location?.let {
						Text(
							"📍 ${it.latitude}, ${it.longitude}",
							style = MaterialTheme.typography.bodyMedium
						)
					}
					Text(
						"ID ${cat.id}",
						style = MaterialTheme.typography.labelSmall,
						color = MaterialTheme.colorScheme.outline,
					)
				}
			}
			Spacer(Modifier.height(30.dp))
			val greeting = remember { Greeting().greet() }
			Text(
				"Kebab Krafter on Kotlin Multiplatform!\n\r$greeting",
				style = MaterialTheme.typography.labelSmall,
				textAlign = TextAlign.Center,
				fontStyle = FontStyle.Italic,
			)
		}
	}
}
