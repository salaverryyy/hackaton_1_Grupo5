package dbp.hackathon;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class HelloEmailEvent extends ApplicationEvent {
    private final String email;

    public HelloEmailEvent(String email, String nombre, String nombrePelicula, LocalDateTime fechaFuncion, int cantidadEntradas, String qr) {
        super(email);
        this.email = email;
    }

}