package donor.services

import org.springframework.mail.SimpleMailMessage

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service


@Service
class EmailService(
    private val emailSender: JavaMailSender? = null
) {
    fun sendSimpleMessage(to: String?, subject: String?, text: String?) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.subject = subject
        message.text = text
        emailSender!!.send(message)
    }
}