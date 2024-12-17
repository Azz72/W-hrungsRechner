package de.sakar.projektrechner

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.exchangerate-api.com/v4/"  // Beispiel-URL, kannst du durch eine echte API ersetzen

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Gson-Konverter hinzufügen
            .build()
    }

    val apiService: CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}