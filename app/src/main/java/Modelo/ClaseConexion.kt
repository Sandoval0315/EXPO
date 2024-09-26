package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
        fun CadenaConexion(): Connection? {
            try{
                val url = "jdbc:oracle:thin:@10.10.2.140:1521:xe"
                val usuario = "pruebaNuevaBD"
                val contraseña = "prueba"

                val conection = DriverManager.getConnection(url, usuario, contraseña)
                return conection
            }
            catch (e:Exception){
                println("ERROR : $e")
                return null
            }
        }
}