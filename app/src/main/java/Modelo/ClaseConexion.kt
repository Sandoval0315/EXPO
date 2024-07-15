package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
        fun CadenaConexion(): Connection? {
            try{
                val url = "jdbc:oracle:thin:@192.168.56.1:1521:xe"
                val usuario = "system"
                val contraseña = "sandoval"

                val conection = DriverManager.getConnection(url, usuario, contraseña)
                return conection
            }
            catch (e:Exception){
                println("ERROR : $e")
                return null
            }
        }
}