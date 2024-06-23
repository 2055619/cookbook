package quixotic.projects.cookbook.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.ContactDTO;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private final static String FROM = "quixotics317@gmail.com";

	public void sendEmail(ContactDTO contactDTO) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(FROM);
			helper.setTo(FROM);
			helper.setSubject(contactDTO.getName() + " - " + contactDTO.getEmail() + " - " + contactDTO.getPhoneNumber());
			helper.setText(getString(contactDTO), true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (MailSendException | MailAuthenticationException e){
			System.out.println(e.getMessage());
		}
    }

	private static String getString(ContactDTO contactDTO) {
		return "<html>" +
				"<head>" +
				"<title>Cookbook support</title>" +
				"</head>" +
				"<body>" +
				"<h1>Cookbook</h1>" +
				"<h2>" + contactDTO.getName() + "</h2>" +
				"<div>" +
				"<p>Une question/Commentaire</p>" +
				"<p>" + contactDTO.getMessage() + "</p>" +
				"<p>Rejoindre en utilisant le courriel: " + contactDTO.getEmail() + " ou le numéro de téléphone: " + contactDTO.getPhoneNumber() + "</p>" +
				"</div>" +
				"</body>" +
				"</html>";
	}

}
