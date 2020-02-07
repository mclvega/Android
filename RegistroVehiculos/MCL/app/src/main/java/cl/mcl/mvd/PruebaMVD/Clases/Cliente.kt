package cl.mcl.mvd.PruebaMVD.Clases

 class Cliente(var run:String,
               var nombre:String,
               var fecha_nacimiento: String,
               var direccion:String,
               var telefono:Int,
               var genero:String,
               val lista: ArrayList<Vehiculos>) {

    //abstract var listaServicios: ArrayList<Vehiculos>

}