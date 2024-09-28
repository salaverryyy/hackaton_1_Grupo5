package dbp.hackathon.email;

import dbp.hackathon.EmailTemplate;
import dbp.hackathon.HelloEmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//https://myaccount.google.com/apppasswords
@Component
public class EmailListener {

    @Autowired
    private EmailTemplate emailTemplate;
    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event, String nombre, String nombrePelicula, LocalDateTime fechaFuncion, int cantidadEntradas, String qr) {
        String to = event.getEmail();
        String subject = "Compra realizada con Exito en el mejor cine del mundo!";
        String text = emailTemplate.generarCorreo(nombre, nombrePelicula, fechaFuncion, cantidadEntradas, qr);
        emailService.sendSimpleMessage(to, subject, text);
    }
}