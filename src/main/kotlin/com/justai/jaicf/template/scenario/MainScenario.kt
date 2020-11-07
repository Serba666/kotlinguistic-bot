package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.channel.googleactions.actions
import com.justai.jaicf.model.scenario.Scenario
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.nio.file.Paths
import java.nio.file.Files

 fun getInfoFromCode(context: BotContext, airportCode: String) {
     val CSV_File_Path = "src/main/kotlin/com/justai/jaicf/template/dictionaries/IATA.csv"
     // read the file
     val reader = Files.newBufferedReader(Paths.get(CSV_File_Path))
     // parse the file into csv values
     val csvParser = CSVParser(reader, CSVFormat.DEFAULT)
     for (csvRecord in csvParser) {
         // Accessing Values by Column Index
         val code = csvRecord.get(0)
         if (code == airportCode) {
             println (csvRecord.get(1))
             println (csvRecord.get(2))
             println (csvRecord.get(3))
             context.session["airport"] = csvRecord.get(1)
             context.session["country"]= csvRecord.get(2)
             context.session["city"] = csvRecord.get(3)
             }
         }
 }
 fun getAirportsFromCity(context: BotContext, city: String) {
     val CSV_File_Path = "src/main/kotlin/com/justai/jaicf/template/dictionaries/IATA.csv"
     // read the file
     val reader = Files.newBufferedReader(Paths.get(CSV_File_Path))
     // parse the file into csv values
     val csvParser = CSVParser(reader, CSVFormat.DEFAULT)
     for (csvRecord in csvParser) {
         // Accessing Values by Column Index
         val cities = csvRecord.get(3)
         var airports = emptyArray<String>()
         if (cities == city) {
             airports += csvRecord.get(1)
             }
         }
     return airports
 }

object MainScenario : Scenario(
    dependencies = listOf(PetrPig)
) {

    init {
        state("start") {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.run {
                    say("Приветствую тебя, мой дорогой путешественник!")
                    say("Я помогу определиться с направлением путешествия в зависимости от количества деревянных в твоём кармане.")
                    say("Итак, какой суммой располагаешь?")
                    /*getInfoFromCode(context)
                    val airport = context.session["airport"]
                    val country = context.session["country"]
                    val city = context.session["city"]
                    say(
                    "code : AAD\n airport: $airport \n country: $country \n city: $city "
                    )*/
                }
            }
        }

        state("bye") {
            activators {
                intent("Bye")
            }

            action {
                reactions.run {
                    say("Пока-пока!")
                    actions?.endConversation()
                }
            }
        }

        state("smalltalk", noContext = true) {
            activators {
                anyIntent()
            }

            action {
                activator.caila?.topIntent?.answer?.let {
                    reactions.say(it)
                }
            }
        }

        fallback {
            reactions.say("Извините, я на тракторе, вас плохо слышно. Полетим куда-нибудь?")
        }
    }
}
