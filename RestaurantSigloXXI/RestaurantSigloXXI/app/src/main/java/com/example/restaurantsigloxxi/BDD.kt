package com.example.restaurantsigloxxi

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.view.*
import java.sql.SQLException

class BDD(val context: Context, val name:String, val factory: SQLiteDatabase.CursorFactory?,
          val version:Int):SQLiteOpenHelper(context,name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val carrito="CREATE TABLE Carrito (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Receta SmallInt NOT NULL," +
                "Nombre Text(30) NOT NULL," +
                "Estado_Orden  TinyInt NOT NULL," +
                "Precio INTEGER NOT NULL)"

        val carritoFinal="CREATE TABLE CarritoFinal (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Receta SmallInt NOT NULL," +
                "Nombre Text(30) NOT NULL," +
                "Estado_Orden  TinyInt NOT NULL," +
                "Precio INTEGER NOT NULL)"
        val Mesa="CREATE TABLE Mesa (" +
                "Numero INTEGER)"

        db?.execSQL(carrito)
        db?.execSQL(carritoFinal)
        db?.execSQL(Mesa)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    fun InsertarCarrito(carrito: Carrito.CarritoJson){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("Receta",carrito.Receta)
            cv.put("Nombre",carrito.Nombre)
            cv.put("Precio",carrito.Precio)
            cv.put("Estado_Orden",carrito.Estado_Orden)

            val resultado = db.insert("Carrito",null,cv)
            db.close()
            if(resultado==-1L){
                Toast.makeText(context,"Receta no agregada", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context,"Receta agregada al Carrito", Toast.LENGTH_SHORT).show()




            }
        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message!!)
        }

    }

    fun InsertarCarritoFinal(carrito: Carrito.CarritoJson){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("Receta",carrito.Receta)
            cv.put("Nombre",carrito.Nombre)
            cv.put("Precio",carrito.Precio)

            cv.put("Estado_Orden",carrito.Estado_Orden)

            val resultado = db.insert("CarritoFinal",null,cv)
            db.close()

        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message!!)
        }

    }

    fun InsertaMesa(numero:Int){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("Numero",numero)

            val resultado = db.insert("Mesa",null,cv)
            db.close()
        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message!!)
        }

    }
    fun QuitarCarrito(carrito:Carrito.CarritoJson){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("Id",carrito.Id)
            cv.put("Receta",carrito.Receta)
            cv.put("Nombre",carrito.Nombre)
            cv.put("Precio",carrito.Precio)

            cv.put("Estado_Orden",carrito.Estado_Orden)

            val resultado = db.execSQL("Delete from Carrito where Id=${carrito.Id}")

            db.close()

        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message!!)
        }

    }
    fun QuitarCarritoFinal(carrito:Carrito.CarritoJson){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("Id",carrito.Id)
            cv.put("Receta",carrito.Receta)
            cv.put("Nombre",carrito.Nombre)
            cv.put("Precio",carrito.Precio)

            cv.put("Estado_Orden",carrito.Estado_Orden)

            val resultado = db.execSQL("Delete from CarritoFinal where Id=${carrito.Id}")

            db.close()

        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message!!)
        }

    }
    fun listarCarrito():ArrayList<Carrito.CarritoJson>{

        var lista = ArrayList<Carrito.CarritoJson>()
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from Carrito ",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val id=cursor.getInt(0)
                    val receta=cursor.getShort(1)
                    val nombre=cursor.getString(2)
                    val precio=cursor.getInt(4)
                    val estado=cursor.getInt(3).toByte()
                    val registro=Carrito.CarritoJson(id,receta,nombre,precio,estado)
                    lista.add(registro)
                }while (cursor.moveToNext())
            }


            return lista

            db.close()

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar el carrito ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListarCarrito",e.message.toString())
            return lista
        }



    }

    fun listarCarritoFinal():ArrayList<Carrito.CarritoJson>{

        var lista = ArrayList<Carrito.CarritoJson>()
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from CarritoFinal ",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val id=cursor.getInt(0)
                    val receta=cursor.getShort(1)
                    val nombre=cursor.getString(2)
                    val precio=cursor.getInt(4)
                    val estado=cursor.getInt(3).toByte()
                    val registro=Carrito.CarritoJson(id,receta,nombre,precio,estado)
                    lista.add(registro)






                }while (cursor.moveToNext())

            }

            return lista

            db.close()

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar el carrito ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListarCarrito",e.message.toString())
            return lista
        }



    }

    fun NumeroMesa():Int{

        var t:Int=0
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from Mesa ",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val numero=cursor.getInt(0)
                    t=numero


                }while (cursor.moveToNext())

            }
            return t
            db.close()

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar el numero de la mesa ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlnumeromesa",e.message.toString())
            return 0
        }

    }
    fun EditarNumeroMesa(n:Int):Int{

        var t:Int=0
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("update Mesa set numero=${n} ",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val numero=cursor.getInt(0)
                    t=numero


                }while (cursor.moveToNext())

            }
            Toast.makeText(context,"Número de Mesa Actualizado",Toast.LENGTH_SHORT).show()
            return t
            db.close()

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar el número de la mesa ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlnumeromesa",e.message.toString())
            return 0
        }

    }


    fun total():Int{

        var t:Int=0
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from Carrito ",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val id=cursor.getInt(0)
                    val receta=cursor.getShort(1)
                    val nombre=cursor.getString(2)
                    val precio=cursor.getInt(4)
                    t=t+precio


                }while (cursor.moveToNext())

            }
            return t
            db.close()

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar el Total de la orden ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListarTotal",e.message.toString())
            return 0
        }



    }

    fun totalFinal():Int{

        var t:Int=0
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from CarritoFinal ",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val id=cursor.getInt(0)
                    val receta=cursor.getShort(1)
                    val nombre=cursor.getString(2)
                    val precio=cursor.getInt(4)
                    t=t+precio


                }while (cursor.moveToNext())

            }
            return t
            db.close()

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar el Total de la orden ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListarTotal",e.message.toString())
            return 0
        }



    }
     fun Vaciar() {

        try{

            val db = this.writableDatabase
            var cursor: Cursor?
            val resultado=db.delete("Carrito",null,null)

            db.close()
        }
        catch (e:Exception){
            Toast.makeText(context,"Error al eliminar Carrito ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar",e.message!!)

        }
    }

    fun VaciarFinal() {

        try{

            val db = this.writableDatabase
            var cursor: Cursor?
            val resultado=db.delete("CarritoFinal",null,null)

            db.close()
        }
        catch (e:Exception){
            Toast.makeText(context,"Error al eliminar Carrito ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar",e.message!!)

        }
    }
}