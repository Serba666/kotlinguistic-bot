package com.justai.jaicf.template.scenario;

import com.justai.jaicf.activator.caila.client.CailaKtorClient
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val client = HttpClient(CIO) {
    expectSuccess = true
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.BODY
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
}

fun getTickets(price: Int, fromAirport: String): List<JsonElement> {

    val data = runBlocking {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val curDate = current.format(formatter)
        client.get<JsonArray>("http://map.aviasales.ru/prices.json?origin_iata=${fromAirport}&period=${curDate}:season&direct=true&one_way=true&locale=ru")
    }

    val suitablePrice = data.filter { e ->
        val strPrice = e.jsonObject["value"]!!.content
        val intPrice = strPrice.substring(0, strPrice.length - 2).toInt()
        intPrice <= price
    }

    return suitablePrice
}

