package HealthSync.healthsync

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

suspend fun enviarCorreo(receptor: String, sujeto: String, mensaje: String) = withContext(
    Dispatchers.IO) {
    // Configuración del servidor SMTP con TLS actualizado
    val props = Properties().apply {
        put("mail.smtp.host", "smtp.gmail.com")
        put("mail.smtp.port", "587") // Puerto TLS estándar
        put("mail.smtp.auth", "true")
        // Habilitar STARTTLS
        put("mail.smtp.starttls.enable", "true")
        // Especificar versión de TLS
        put("mail.smtp.ssl.protocols", "TLSv1.2")
        put("mail.smtp.ssl.trust", "smtp.gmail.com")
    }

    val session = Session.getInstance(props, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("rjmj.007.009@gmail.com", "rtfv czke wvdh ocpu")
        }
    })

    try {
        val message = MimeMessage(session).apply {
            setFrom(InternetAddress("somos.healthsync@gmail.com"))
            addRecipient(Message.RecipientType.TO, InternetAddress(receptor))
            subject = sujeto
            setContent(mensaje, "text/html; charset=utf-8")
        }

        Transport.send(message)
        println("Correo enviado satisfactoriamente")
    } catch (e: MessagingException) {
        e.printStackTrace()
        println("CORREO NO ENVIADO EXE")
        // Opcional: Agregar más detalles del error
        println("Error: ${e.message}")
    }
}