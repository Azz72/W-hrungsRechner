package de.sakar.projektrechner


data class ExchangeRateResponse(
    val rates: Map<String, Double>,
    val base: String,
    val date: String
)