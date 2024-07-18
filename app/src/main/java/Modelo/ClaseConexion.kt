package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
        fun CadenaConexion(): Connection? {
            try{
                val url = "jdbc:oracle:thin:@192.168.0.6:1521:xe"
                val usuario = "HealthSync_Developer2"
                val contraseña = "healthsync20242"

                val conection = DriverManager.getConnection(url, usuario, contraseña)
                return conection
            }
            catch (e:Exception){
                println("ERROR : $e")
                return null
            }
        }
}