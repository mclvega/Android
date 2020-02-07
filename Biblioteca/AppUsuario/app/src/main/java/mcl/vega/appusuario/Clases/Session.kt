package mcl.vega.appusuario.Clases

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import mcl.vega.appusuario.Login

import kotlin.jvm.internal.MagicApiIntrinsics
class Session {
    lateinit var pref: SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATE_MODE:Int=0

    constructor(con:Context){
        this.con=con
        pref=con.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor=pref.edit()
    }

    companion object{
        val PREF_NAME:String="AppAdm"
        val IS_LOGIN="isLoggedIn"
        val KEY_USER="user"
        val KEY_PASS="pass"
    }

    fun createLoginSession(user:String,pass:String){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString(KEY_USER,user)
        editor.putString(KEY_PASS,pass)
        editor.commit()
    }
    fun checkLogin()
    {
        if(!this.isLoggedIn()){
            var i: Intent = Intent(con, Login::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(i)
        }
    }
    fun getUserDetails():HashMap<String,String>{
        var user:Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(KEY_USER,pref.getString(KEY_USER,null))
        (user as HashMap).put(KEY_PASS,pref.getString(KEY_PASS,null))
        return user
    }
    fun LogoutUser(){
        editor.clear()
        editor.commit()
        var i=Intent(con,Login::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        con.startActivity(i)
    }
    fun isLoggedIn():Boolean{
        return pref.getBoolean(IS_LOGIN,false)
    }
}