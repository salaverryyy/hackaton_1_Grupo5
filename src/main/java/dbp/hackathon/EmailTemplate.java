package dbp.hackathon;

import java.time.LocalDateTime;

public class EmailTemplate {

    public String generarCorreo(String nombre, String nombrePelicula, LocalDateTime fechaFuncion, int cantidadEntradas, double precioTotal, String qr) {
        String plantilla = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Gracias por tu compra!</title>
            </head>
            <body>
                <h1>Gracias por tu compra!</h1>
                <p>¡Hola {{nombre}}! Te informamos que tu compra ha sido exitosa. A continuación, te presentamos los detalles:</p>
                <ul>
                    <li>Nombre de la película: {{nombrePelicula}}</li>
                    <li>Fecha de la función: {{fechaFuncion}}</li>
                    <li>Cantidad de entradas: {{cantidadEntradas}}</li>
                    <li>Precio total: {{precioTotal}}</li>
                    <li>Código QR: <img src="{{qr}}"/></li>
                </ul>
                <p>No olvides llevar tu código QR impreso o en tu dispositivo móvil para poder ingresar a la función.</p>
            </body>
            </html>
        """;

        // Reemplazar los valores dinámicos
        plantilla = plantilla.replace("{{nombre}}", nombre);
        plantilla = plantilla.replace("{{nombrePelicula}}", nombrePelicula);
        plantilla = plantilla.replace("{{fechaFuncion}}", fechaFuncion.toString());
        plantilla = plantilla.replace("{{cantidadEntradas}}", String.valueOf(cantidadEntradas));
        plantilla = plantilla.replace("{{precioTotal}}", String.valueOf(precioTotal));
        plantilla = plantilla.replace("{{qr}}", qr);

        return plantilla;
    }
}
