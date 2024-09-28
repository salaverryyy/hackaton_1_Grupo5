package dbp.hackathon.email;

import dbp.hackathon.EmailTemplate;
import dbp.hackathon.HelloEmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailListener {

    @Autowired
    private EmailTemplate emailTemplate;

    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event) {
        // Extraer los valores del evento
        String to = event.getEmail();
        String nombre = event.getNombre();
        String nombrePelicula = event.getNombrePelicula();
        LocalDateTime fechaFuncion = event.getFechaFuncion();
        int cantidadEntradas = event.getCantidadEntradas();
        String qr = event.getQr();

        // Preparar el asunto y el contenido del correo
        String subject = "Compra realizada con Éxito en el mejor cine del mundo!";
        String text = emailTemplate.generarCorreo(nombre, nombrePelicula, fechaFuncion, cantidadEntradas, qr);

        // Enviar el correo
        emailService.sendMessage(to, subject, text);
    }
}
