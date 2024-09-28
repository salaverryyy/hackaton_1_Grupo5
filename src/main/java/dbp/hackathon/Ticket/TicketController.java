package dbp.hackathon.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private JavaMailSender mailSender;


    //endpoint que valida la autenticidad del ticket escaneando el codigo QR
    @PostMapping("/validate")
    public ResponseEntity<String> validateTicket(@RequestParam String qrCodeData) {
        // Buscar el ticket por el código QR utilizando el TicketService
        Ticket ticket = ticketService.findByQrCode(qrCodeData);
        if (ticket == null) {
            return ResponseEntity.status(404).body("Ticket no encontrado o inválido.");
        }

        // Verificar si el ticket ya fue canjeado
        if (ticket.getEstado() == Estado.CANJEADO) {
            return ResponseEntity.status(400).body("El ticket ya fue canjeado.");
        }

        // Cambiar estado a CANJEADO usando el service
        ticket.setEstado(Estado.CANJEADO);
        ticketService.updateTicket(ticket);

        // Enviar correo de confirmación al usuario
        enviarCorreoConfirmacion(ticket.getEstudiante().getEmail(), ticket);

        return ResponseEntity.ok("Ticket validado y canjeado con éxito.");
    }

    private void enviarCorreoConfirmacion(String email, Ticket ticket) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Confirmación de Ticket Canjeado");
        message.setText("Su ticket con código QR " + ticket.getQr() + " ha sido canjeado exitosamente.");
        mailSender.send(message);

    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest request) {
        Ticket newTicket = ticketService.createTicket(request.getEstudianteId(), request.getFuncionId(), request.getCantidad());
        return ResponseEntity.ok(newTicket);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<Iterable<Ticket>> getTicketsByEstudianteId(@PathVariable Long estudianteId) {
        Iterable<Ticket> tickets = ticketService.findByEstudianteId(estudianteId);
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/{id}/changeState")
    public ResponseEntity<?> changeTicketState(@PathVariable Long id) {
        try {
            ticketService.changeState(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }
}
