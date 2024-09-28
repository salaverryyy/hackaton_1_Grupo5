package dbp.hackathon.email;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            Context context = new Context();
            context.setVariable("message", text);
            String contentHTML = templateEngine.process("EmailTemplates", context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(contentHTML, true);

            mailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}

