package com.example.myapplicationcomposesample.challenges

import androidx.annotation.DrawableRes
import com.example.myapplicationcomposesample.R

data class Challenge(
    var id:Int,
    var difficulty:Int,
    var name:String,
    var score:Int,
    @DrawableRes val imageResource: Int,
    var completed:Boolean,
    var disabled:Boolean
) {
    fun getImage(): Int {
        if (this.disabled) {
            return R.drawable.padlock
        }

        return this.imageResource
    }
}