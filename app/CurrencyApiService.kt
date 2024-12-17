package de.sakar.projektrechner

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    // API-Endpunkt für die aktuellen Wechselkurse
    @GET("latest")
    fun getExchangeRates(
        @Query("base") baseCurrency: String,   // Basiswährung
        @Query("symbols") symbols: String      // Zielwährung(en)
    ): Call<ExchangeRateResponse>  // Rückgabewert ist eine Call-Instanz für die Antwort
}