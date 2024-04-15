package com.example.newpokedexapp.util

import android.graphics.Color
import com.example.newpokedexapp.data.remote.responses.Stat
import com.example.newpokedexapp.data.remote.responses.Type
import com.example.newpokedexapp.ui.theme.AtkColor
import com.example.newpokedexapp.ui.theme.DefColor
import com.example.newpokedexapp.ui.theme.HPColor
import com.example.newpokedexapp.ui.theme.SpAtkColor
import com.example.newpokedexapp.ui.theme.SpDefColor
import com.example.newpokedexapp.ui.theme.SpdColor
import com.example.newpokedexapp.ui.theme.TypeBug
import com.example.newpokedexapp.ui.theme.TypeDark
import com.example.newpokedexapp.ui.theme.TypeDragon
import com.example.newpokedexapp.ui.theme.TypeElectric
import com.example.newpokedexapp.ui.theme.TypeFairy
import com.example.newpokedexapp.ui.theme.TypeFighting
import com.example.newpokedexapp.ui.theme.TypeFire
import com.example.newpokedexapp.ui.theme.TypeFlying
import com.example.newpokedexapp.ui.theme.TypeGhost
import com.example.newpokedexapp.ui.theme.TypeGrass
import com.example.newpokedexapp.ui.theme.TypeGround
import com.example.newpokedexapp.ui.theme.TypeIce
import com.example.newpokedexapp.ui.theme.TypeNormal
import com.example.newpokedexapp.ui.theme.TypePoison
import com.example.newpokedexapp.ui.theme.TypePsychic
import com.example.newpokedexapp.ui.theme.TypeRock
import com.example.newpokedexapp.ui.theme.TypeSteel
import com.example.newpokedexapp.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): androidx.compose.ui.graphics.Color {
    return when(type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> androidx.compose.ui.graphics.Color.Black
    }
}

fun parseStatToColor(stat: Stat): androidx.compose.ui.graphics.Color {
    return when(stat.stat.name.lowercase()) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> androidx.compose.ui.graphics.Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when(stat.stat.name.lowercase()) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}