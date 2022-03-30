package com.ankit.squadgame3.model

import com.ankit.squadgame3.R


val members = mutableListOf<Member>()
val squads = listOf<Squad>(
    sigma,
    qubit,
    nucleus,
    photon,
    electrons,
    momentum,
    ozone,
    delta,
    quantum
)


fun buildData() {
    val teamMap = HashMap<String, String>().apply {
        this["Omar"] = "Sigma"
        this["Prashant"] = "Nucleus"
        this["Sanchit"] = "Qubit"
        this["Sanjeev"] = "Electrons"
        this["Ankit"] = "Sigma"
        this["Abhishek"] = "Photon"
        this["Faheem"] = "Photon"
        this["Ankit Raj"] = "Momentum"
        this["Sakshi Pruthi"] = "Qubit"
        this["Aditya Mathur"] = "Nucleus"
        this["Moghira"] = "Momentum"
        this["Abhilash"] = "Delta"
        this["Gauri Advani"] = "Quantum"
        this["Gunjit"] = "Sigma"
        this["Nitin Bhatia"] = "Sigma"
        this["Vaibhav"] = "Sigma"
        this["Yogesh"] = "Qubit"
        this["Ricky"] = "Nucleus"
        this["Tushar"] = "Sigma"
    }

    for(entry in teamMap) {
        members.add(Member(squad = getSquad(entry.value), name = entry.key, picture = getDrawable(entry.key)))
    }
}

fun getSquad(name: String): Squad {
    for(squad in squads) {
        if(name == squad.name) {
            return squad
        }
    }
    return sigma
}

fun getDrawable(name: String): Int {
    return when(name) {
        "Omar" -> {R.drawable.placeholder}
        "Prashant" -> {R.drawable.prashant}
        "Sanchit" -> {R.drawable.sanchit}
        "Sanjeev" -> {R.drawable.sanjeev}
        "Ankit" -> {R.drawable.ankit_kumar}
        "Abhishek" -> {R.drawable.placeholder}
        "Faheem" -> {R.drawable.faheem}
        "Ankit Raj" -> {R.drawable.ankitraj}
        "Sakshi Pruthi" -> {R.drawable.placeholder}
        "Aditya Mathur" -> {R.drawable.aditya}
        "Moghira" -> {R.drawable.placeholder}
        "Abhilash" -> {R.drawable.abhilash}
        "Gauri Advani" -> {R.drawable.placeholder}
        "Gunjit" -> {R.drawable.gunjit}
        "Nitin Bhatia" -> {R.drawable.nitin}
        "Vaibhav" -> {R.drawable.vaibhav}
        "Yogesh" -> {R.drawable.placeholder}
        "Ricky" -> {R.drawable.placeholder}
        "Tushar" -> {R.drawable.tushar}
        else -> {R.drawable.placeholder}
    }
}