package com.example.restaurantsigloxxi

class Carrito(
              val Nombre:String,
              val Precio:Int) {
    class CarritoJson(val Id:Int,val Receta:Short,
                  val Nombre:String,
                  val Precio:Int,val Estado_Orden:Byte){}

}