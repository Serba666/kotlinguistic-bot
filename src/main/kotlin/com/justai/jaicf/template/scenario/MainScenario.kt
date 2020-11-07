package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario

object MainScenario : Scenario() {

    init {
        state("start") {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.run {
                    image("http://s02.yapfiles.ru/files/15392/Zak_petri.gif")
                    say("Приветствую тебя, мой дорогой путешественник! Я помогу определиться с направлением путешествия в зависимости от количества деревянных в твоём кармане.")
                    say("Итак, какой суммой располагаешь?")
                }
            }
        }

        state("bye") {
            activators {
                intent("Bye")
            }

            action {
                reactions.sayRandom(
                    "See you soon!",
                    "Bye-bye!"
                )
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