package dbp.hackathon.email;

import dbp.hackathon.HelloEmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
//https://myaccount.google.com/apppasswords
@Component
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @EventListener
    @Async
    public void handleHelloEmailEvent(HelloEmailEvent event) {
        String to = event.getEmail();
        String subject = "Producto Sin Stock";
        String text = "Este es un correo siiii.";
        emailService.sendSimpleMessage(to, subject, text);
    }
}