package de.sakar.projektrechner

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("latest")
    fun getExchangeRates(
        @Query("base") baseCurrency: String,
        @Query("symbols") symbols: String
    ): Call<ExchangeRateResponse>
}

