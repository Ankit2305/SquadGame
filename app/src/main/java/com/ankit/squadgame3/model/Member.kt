package com.ankit.squadgame3.model

import com.ankit.squadgame3.R

data class Member(
    val squad: Squad,
    override val name: String,
    override val picture: Int
): SlotItem

