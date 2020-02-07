package mcl.vega.appadm
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import mcl.vega.appadm.Clases.Libro
import mcl.vega.appadm.Clases.Usuario
class BDD(val context: Context, val name:String, val factory: SQLiteDatabase.CursorFactory?,
          val version:Int): SQLiteOpenHelper(context,name,factory,version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val libro="CREATE TABLE libro (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT(30) NOT NULL," +
                "stock INTEGER(4) NOT NULL," +
                "activo INTEGER(1) NOT NULL)"
        val usuario="CREATE TABLE usuario (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "run TEXT(10) NOT NULL," +
                "nombre TEXT(30) NOT NULL," +
                "user TEXT(30) NOT NULL," +
                "pass TEXT(30) NOT NULL," +
                "activo INTEGER(1) NOT NULL," +
                "tipo INTEGER(1) NOT NULL)"

        val prestamos="CREATE TABLE prestamos (" +
                "codigo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "usuario INTEGER NOT NULL," +
                "libro INTEGER NOT NULL," +
                "fecha_prestamo Date NOT NULL," +
                "fecha_devolucion Date NOT NULL)"

        db?.execSQL(libro)
        db?.execSQL(usuario)
        //db?.execSQL(prestamos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun InsertarUsuario(usuario: Usuario){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("run",usuario.run)
            cv.put("nombre",usuario.nombre)
            cv.put("user",usuario.user)
            cv.put("pass",usuario.pass)
            cv.put("activo",usuario.activo)
            cv.put("tipo",usuario.tipo)

            val resultado = db.insert("usuario",null,cv)
            db.close()
            if(resultado==-1L){
                Toast.makeText(context,"Usuario no agregado", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context,"Bienvenido a la App", Toast.LENGTH_SHORT).show()
            }
        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message)
        }

    }

    fun InsertarLibro(libro: Libro){
        try{
            val db=this.writableDatabase
            val cv= ContentValues()
            cv.put("nombre",libro.nombre)
            cv.put("stock",libro.stock)
            cv.put("activo",libro.activo)

            val resultado = db.insert("libro",null,cv)
            db.close()
            if(resultado==-1L){
                Toast.makeText(context,"Libro no agregado", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(context,"Libro agregado", Toast.LENGTH_SHORT).show()
            }
        }
        catch(e: SQLException){
            Toast.makeText(context,"Error al insertar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlInsertar",e.message)
        }

    }

    fun listarLibros():ArrayList<Libro>{

        var lista = ArrayList<Libro>()
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from libro",null)
            if(cursor?.moveToFirst()==true){
                do{
                    val codigo=cursor.getInt(0)
                    val nombre = cursor.getString(1)
                    val stock=cursor.getInt(2)
                    val activo=cursor.getInt(3)
                    val registro= Libro(codigo,nombre,stock,activo)
                    lista.add(registro)
                }while (cursor.moveToNext())

            }
            return lista

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al listar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar",e.message)
            return lista
        }


    }

    fun BuscarUsuario(ru:String):Usuario?{

        var r:Usuario?=null
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from usuario",null)

            if(cursor?.moveToFirst()==true){
                do{
                    val codigo=cursor.getInt(0)
                    val run = cursor.getString(1)
                    val nombre = cursor.getString(2)
                    val usua = cursor.getString(3)
                    val passw = cursor.getString(4)
                    val activo = cursor.getInt(5)
                    val tipo = cursor.getInt(6)
                    if(run==run){
                        Toast.makeText(context,"Bienvenido $nombre", Toast.LENGTH_SHORT).show()
                        r= Usuario(codigo,run,nombre,usua,passw,activo,tipo)

                    }
                    else{

                        Toast.makeText(context,"Error al ingresar credenciales", Toast.LENGTH_SHORT).show()
                    }
                }while (cursor.moveToNext())
                return r
            }

            return r

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al Iniciar Sesion ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar",e.message)
            return r
        }


    }
    fun Login(user:String, pass:String):Usuario?{

        var r:Usuario?=null
        try{
            val db=this.writableDatabase
            var cursor: Cursor?
            cursor = db.rawQuery("select * from usuario",null)

            if(cursor?.moveToFirst()==true){
                do{
                    val codigo=cursor.getInt(0)
                    val run = cursor.getString(1)
                    val nombre = cursor.getString(2)
                    val usua = cursor.getString(3)
                    val passw = cursor.getString(4)
                    val activo = cursor.getInt(5)
                    val tipo = cursor.getInt(6)
                    if(usua==user && passw==pass){
                        Toast.makeText(context,"Bienvenido $user", Toast.LENGTH_SHORT).show()
                        r= Usuario(codigo,run,nombre,usua,passw,activo,tipo)

                    }
                    else{

                        Toast.makeText(context,"Error al ingresar credenciales", Toast.LENGTH_SHORT).show()
                    }
                }while (cursor.moveToNext())
                return r
            }

            return r

        }
        catch (e:Exception){
            Toast.makeText(context,"Error al Iniciar Sesion ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlListar",e.message)
            return r
        }


    }

    fun Eliminar(codigo:Int){

        try{

            val db=this.writableDatabase
            val args=arrayOf(codigo.toString())
            val resultado=db.delete("libro","codigo = ?",args)
            if(resultado==0){

                Toast.makeText(context,"libro no eliminado",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context,"libro eliminado",Toast.LENGTH_SHORT).show()

            }
        }
        catch (e:Exception){
            Toast.makeText(context,"Error al eliminar ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("sqlEliminar",e.message)

        }
    }

}