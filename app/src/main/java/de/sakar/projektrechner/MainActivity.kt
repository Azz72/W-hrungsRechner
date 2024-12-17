package de.sakar.projektrechner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.sakar.projektrechner.ui.theme.ProjektRechnerTheme
import de.sakar.projektrechner.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektRechnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterApp(modifier: Modifier = Modifier) {
    var startCurrency by remember { mutableStateOf("EUR") }
    var targetCurrency by remember { mutableStateOf("USD") }
    var amount by remember { mutableStateOf("") }
    var conversionRate by remember { mutableStateOf(1.1) }
    var result by remember { mutableStateOf<Double?>(null) }

    val currencies = listOf("EUR", "USD", "GBP")
    val exchangeRates = mapOf(
        "EUR to USD" to 1.1,
        "USD to EUR" to 0.9,
        "EUR to GBP" to 0.85,
        "GBP to EUR" to 1.18,
        "USD to GBP" to 0.75,
        "GBP to USD" to 1.33
    )


    fun updateConversionRate() {
        val key = "$startCurrency to $targetCurrency"
        conversionRate = exchangeRates[key] ?: 1.0
    }


    LaunchedEffect(startCurrency, targetCurrency, amount) {
        if (amount.isNotEmpty()) {
            val amountDouble = amount.toDoubleOrNull()
            result = if (amountDouble != null) {
                amountDouble * conversionRate
            } else {
                null
            }
        } else {
            result = null
        }
    }


    LaunchedEffect(startCurrency, targetCurrency) {
        updateConversionRate()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Währungsrechner", style = MaterialTheme.typography.titleLarge)
            Image(
                painter = painterResource(id = R.drawable.geld), // Bild "geld.jpg"
                contentDescription = "App Logo",
                modifier = Modifier.size(48.dp)
            )
        }

        Text(text = "Startwährung", style = MaterialTheme.typography.bodyLarge)
        DropdownMenuComponent(selected = startCurrency, options = currencies) { selected ->
            startCurrency = selected
        }

        BasicTextField(
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(Modifier.padding(8.dp)) {
                    if (amount.isEmpty()) {
                        Text("Betrag eingeben", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    }
                    innerTextField()
                }
            }
        )

        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)

        Text(text = "Zielwährung", style = MaterialTheme.typography.bodyLarge)
        DropdownMenuComponent(selected = targetCurrency, options = currencies) { selected ->
            targetCurrency = selected
        }

        Text(text = "Livekurs: $conversionRate", style = MaterialTheme.typography.bodyLarge)


        result?.let {
            Text(
                text = "Ergebnis: $it $targetCurrency",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun DropdownMenuComponent(selected: String, options: List<String>, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(onClick = { expanded = true }) {
            Text(text = selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onSelected(option)
                        expanded = false
                    },
                    text = {
                        Text(option)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    ProjektRechnerTheme {
        CurrencyConverterApp()
    }
}