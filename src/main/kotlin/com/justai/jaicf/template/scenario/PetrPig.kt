package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario

object PetrPig : Scenario() {

    init {
        state("Petr") {
            activators {
                regex("pig")
            }
            action {
                reactions.run {
                    image("http://s02.yapfiles.ru/files/15392/Zak_petri.gif")
                    say("Приветствую тебя, мой дорогой путешественник! Я помогу определиться с направлением путешествия в зависимости от количества деревянных в твоём кармане.")
                    say("Итак, какой суммой располагаешь?")
                }
            }
        }
    }
}