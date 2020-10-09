package com.example.myapplicationcomposesample.challenges

import com.example.myapplicationcomposesample.R

object ChallengeMockSource {
    fun getChallenges():List<Challenge> {
        return ArrayList<Challenge>().apply {
            add(Challenge(1, 1, "Ship", 0, R.drawable.lego_ship, completed = false, disabled = false))
            add(Challenge(2, 2, "Dinosaur", 0, R.drawable.lego_dinosaur, completed = false, disabled = false))
            add(Challenge(3, 3, "Airplane", 0, R.drawable.lego_airplane, completed = false, disabled = false))
            add(Challenge(4, 4, "Tank", 0, R.drawable.lego_tank, completed = false, disabled = false))
            add(Challenge(5, 10, "Eiffel Tower", 0, R.drawable.padlock, completed = false, disabled = true))
            add(Challenge(5, 10, "Empire State", 0, R.drawable.padlock_icon_vector, completed = false, disabled = true))

        }
    }
}