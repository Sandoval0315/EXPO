package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexionMonterrosa {
    fun CadenaConexion(): Connection? {
        try{
            val url = "jdbc:oracle:thin:@tu ip:1521:xe"
            val usuario = "HealthSync_Developer"
            val contraseña = "healthsync2024"

            val conection = DriverManager.getConnection(url, usuario, contraseña)
            return conection
        }
        catch (e:Exception){
            println("ERROR : $e")
            return null
        }
    }
}