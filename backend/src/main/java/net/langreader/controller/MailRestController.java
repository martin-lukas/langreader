package net.langreader.controller;

import net.langreader.payload.request.ContactRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/mail")
public class MailRestController {
    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public ResponseEntity<?> sendMail(@Valid @RequestBody ContactRequest contactRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("contactus@langreader.net");
        message.setTo("martinlukas24@protonmail.com");
        message.setSubject("LangReader - contact form: " + contactRequest.getSubject());
        message.setText(
                "Message from the LangReader contact form:\n\n"
                        + "Email: " + contactRequest.getEmail() + "\n"
                        + "Subject: " + contactRequest.getSubject() + "\n"
                        + "Text:\n\n" + contactRequest.getText());
        mailSender.send(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
