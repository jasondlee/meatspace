package io.meatspace.uis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MeatspaceApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFF8F2E7),
                                Color(0xFFE7F0E8),
                            ),
                        ),
                    )
                    .padding(28.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text("Meatspace", style = MaterialTheme.typography.displayMedium)
                Text(
                    "Plan public and private community gatherings across web and mobile clients.",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    "This starter UI targets Web and Android by default. iOS targets are enabled automatically on macOS.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}
