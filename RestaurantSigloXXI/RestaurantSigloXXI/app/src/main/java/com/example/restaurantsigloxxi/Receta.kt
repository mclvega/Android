package com.example.restaurantsigloxxi

import java.util.*

class Receta(val Id:Short,
             val Nombre:String,
             val Precio:Int,
             val Dificultad:Byte,
             val Estado:Boolean
             ) {
    class Recetajson(val Id:Short,
                      val Nombre:String,
                      val Precio:Int,
                      val Estado:Boolean
                      ,val Tipo:Byte){

    }
}