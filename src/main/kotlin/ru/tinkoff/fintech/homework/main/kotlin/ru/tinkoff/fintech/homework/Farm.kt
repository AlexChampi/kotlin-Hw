package ru.tinkoff.fintech.homework

interface Animal {
    fun makeVoice()
    fun getName(): String
}

class Cow(private val name: String) : Animal {
    private val voice = "Moo"

    override fun makeVoice() {
        println(voice)
    }

    override fun getName(): String {
        return this.name
    }
}

class Pig(private val name: String, private val weight: Int = 100) : Animal {
    private val voice = "Oink"

    override fun makeVoice() {
        println(voice)
    }

    override fun getName(): String {
        return this.name
    }

    fun getWeight(): Int {
        return this.weight
    }
}

class Farm(private val array: Array<Animal>) {
    fun countAnimal(): Int {
        return array.size
    }


    fun makeVoices() {
        for (animal in array) {
            animal.makeVoice()
        }
    }

    fun showNames(): Boolean {
        for (animal in array) {
            println(animal.getName())
        }
        return true
    }

}

fun main() {
    val cow: Animal = Cow("Marusyia")
    println("Cow name is ${cow.getName()}")

    val pig: Pig = Pig("Chrusha", 150)
    println("Weight of ${pig.getName()} is ${pig.getWeight()} \n")

    val farm: Farm = Farm(arrayOf(cow, pig))
    println("Animal on the farm ${farm.countAnimal()} \n")

    println("List of animals name:")
    farm.showNames()

    println("\nVoices at farm:")
    farm.makeVoices()



}