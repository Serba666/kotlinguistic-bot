package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.channel.googleactions.actions
import com.justai.jaicf.model.scenario.Scenario

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