package com.example.recepcion

import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class JsonMesas {
    class ObtenerListaD(var json:String,
                       var taskCompleted:OntaskCompletedD): AsyncTask<String, Int, Boolean>()
    {
        interface OntaskCompletedD{
            fun onTaskCompleted(response: String){

            }
        }

        override fun doInBackground(vararg params: String?): Boolean {
            var inputStream: InputStream?=null
            try {
                var url = URL(params[0])
                var con = url.openConnection() as HttpURLConnection
                con.readTimeout = 1000
                con.connectTimeout = 15000
                con.requestMethod = "GET"
                con.doInput = true
                var respuesta: Int = con.responseCode
                Log.d("Servidor", respuesta.toString())
                inputStream = con.inputStream
                json = Scanner(inputStream).useDelimiter("\\A").next()
                Log.i("Contenido", json)
            }
            catch (e: IOException){
                Log.e("Error ",e.message.toString())
                return false
            }
            catch (e:Exception){
                Log.e("Error ",e.message.toString())
                return false
            }
            finally {
                if(inputStream!=null){
                    try{
                        inputStream.close()
                    }
                    catch (e: IOException){
                        Log.e("Error ",e.message.toString())
                    }
                    catch (e:Exception){
                        Log.e("Error ",e.message.toString())
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
    class ObtenerListaO(var json:String,
                        var taskCompleted:OntaskCompletedO): AsyncTask<String, Int, Boolean>()
    {
        interface OntaskCompletedO{
            fun onTaskCompletedO(response: String){

            }
        }

        override fun doInBackground(vararg params: String?): Boolean {
            var inputStream: InputStream?=null
            try {
                var url = URL(params[0])
                var con = url.openConnection() as HttpURLConnection
                con.readTimeout = 1000
                con.connectTimeout = 15000
                con.requestMethod = "GET"
                con.doInput = true
                var respuesta: Int = con.responseCode
                Log.d("Servidor", respuesta.toString())
                inputStream = con.inputStream
                json = Scanner(inputStream).useDelimiter("\\A").next()
                Log.i("Contenido", json)
            }
            catch (e: IOException){
                Log.e("Error ",e.message.toString())
                return false
            }
            catch (e:Exception){
                Log.e("Error ",e.message.toString())
                return false
            }
            finally {
                if(inputStream!=null){
                    try{
                        inputStream.close()
                    }
                    catch (e: IOException){
                        Log.e("Error ",e.message.toString())
                    }
                    catch (e:Exception){
                        Log.e("Error ",e.message.toString())
                    }
                }
            }
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if(result==true){
                taskCompleted.onTaskCompletedO(json)
            }
            else{
                taskCompleted.onTaskCompletedO("Error")
            }
        }
    }

}