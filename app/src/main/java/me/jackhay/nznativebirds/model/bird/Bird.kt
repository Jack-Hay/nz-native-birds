package me.jackhay.nznativebirds.model.bird

import java.io.Serializable

class Bird(
    var fullName: String,
    val englishName: String,
    val maoriName: String,
    val description: String,
    val imageName: String,
    val imageCredit: String
) : Serializable {

    fun setFullName() {
        fullName = when {
            englishName == "" -> maoriName
            maoriName == "" -> englishName
            else -> "$englishName/$maoriName"
        }
    }
}