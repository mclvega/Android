package mcl.vega.appadm.Clases

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class JsonPrestamos {

    class ObtenerPrestamos(var json:String,var taskCompleted:OnTaskCompleted):
        AsyncTask<String, Int, Boolean>()
    {

        interface OnTaskCompleted {

            fun onTaskCompleted(response:String){


            }
        }

        override fun doInBackground(vararg params: String?): Boolean {
            var inputStream: InputStream?=null
            try{
                var url= URL(params[0])
                var con=url.openConnection() as HttpURLConnection
                con.readTimeout=500
                con.connectTimeout=5000
                con.requestMethod="GET"
                con.doInput=true
                var respuesta:Int=con.responseCode
                Log.d("Servidor",respuesta.toString())
                inputStream=con.inputStream
                json= Scanner(inputStream).useDelimiter("\\A").next()
                Log.i("Contenido",json)

            }
            catch (e: IOException){
                Log.e("Error ",e.message)
                return false
            }
            catch (e:Exception){
                Log.e("Error ",e.message)
                return false
            }
            finally {
                if(inputStream!=null){
                    try{
                        inputStream.close()
                    }
                    catch (e: IOException){
                        Log.e("Error ",e.message)
                    }
                    catch (e:Exception){
                        Log.e("Error ",e.message)
                    }
                }
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if(result==true){
                taskCompleted.onTaskCompleted(json)
            }
            else{
                taskCompleted.onTaskCompleted("Error")
            }
        }

    }
}