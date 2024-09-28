package dbp.hackathon;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class HelloEmailEvent extends ApplicationEvent {
    private final String email;
    private final String nombre;
    private final String nombrePelicula;
    private final LocalDateTime fechaFuncion;
    private final int cantidadEntradas;
    private final String qr;

    // Constructor actualizado con todos los parámetros
    public HelloEmailEvent(String email, String nombre, String nombrePelicula, LocalDateTime fechaFuncion, int cantidadEntradas, String qr) {
        super(email);
        this.email = email;
        this.nombre = nombre;
        this.nombrePelicula = nombrePelicula;
        this.fechaFuncion = fechaFuncion;
        this.cantidadEntradas = cantidadEntradas;
        this.qr = qr;
    }
}
