package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;

public class ObjetoEnviaEmail {

	private String userName = "jejsantosce@gmail.com";
	private String password = "santos@12*";

	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoDoCorpoEmail = "";

	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail, String textoDoCorpoEmail) {

		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoDoCorpoEmail = textoDoCorpoEmail;
	}

	public void enviarEmail(boolean envioHtml) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*"); // Autenticação SSL
		properties.put("mail.smtp.auth", "true"); /* Autorização */
		properties.put("mail.smtp.starttsl", "true"); /* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor gmail Google */
		properties.put("mail.smtp.port", "465"); /* Porta do Servidor */
		properties.put("mail.smtp.socketFactory.Port", "465"); /* Expecifica a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao SMTP */

		// Objeto de Sessao para retornar uma sessão autenticada, do servidor de email
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios); // Endereços de
																		// e-mail que
																		// iram receber
																		// o e-mail
		Message message = new MimeMessage(session); // conexão ao servidor de e-mail
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // Quem esta enviando(Remetente)
		message.setRecipients(Message.RecipientType.TO, toUser); // Email de destino
		message.setSubject(assuntoEmail); // Assunto do E-mail
		message.setText(textoDoCorpoEmail); // Corpo do Email

		/* Parte 1 do e-mail que é texto e descrição do e-mail */
		MimeBodyPart corpoEmail = new MimeBodyPart();
		if (envioHtml) {

			corpoEmail.setContent(textoDoCorpoEmail, "text/html; charset=utf-8");
		} else {

			corpoEmail.setText(textoDoCorpoEmail);
		}

		/* Parte 2 do e-mail que são os anexos em pdf */

		MimeBodyPart anexoEmail = new MimeBodyPart();
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(SimuladorDePdf(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
		Multipart multpart =new MimeMultipart();
		multpart.addBodyPart(corpoEmail);
		multpart.addBodyPart(anexoEmail);
					
		message.setContent(multpart);
		
		
		Transport.send(message); /* ENVIANDO O E-MAIL */
	}

	
	
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*"); // Autenticação SSL
		properties.put("mail.smtp.auth", "true"); /* Autorização */
		properties.put("mail.smtp.starttsl", "true"); /* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor gmail Google */
		properties.put("mail.smtp.port", "465"); /* Porta do Servidor */
		properties.put("mail.smtp.socketFactory.Port", "465"); /* Expecifica a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao SMTP */

		// Objeto de Sessao para retornar uma sessão autenticada, do servidor de email
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios); // Endereços de
																		// e-mail que
																		// iram receber
																		// o e-mail
		Message message = new MimeMessage(session); // conexão ao servidor de e-mail
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // Quem esta enviando(Remetente)
		message.setRecipients(Message.RecipientType.TO, toUser); // Email de destino
		message.setSubject(assuntoEmail); // Assunto do E-mail
		message.setText(textoDoCorpoEmail); // Corpo do Email

		/* Parte 1 do e-mail que é texto e descrição do e-mail */
		MimeBodyPart corpoEmail = new MimeBodyPart();
		if (envioHtml) {

			corpoEmail.setContent(textoDoCorpoEmail, "text/html; charset=utf-8");
		} else {

			corpoEmail.setText(textoDoCorpoEmail);
		}

		/* Parte 2 do e-mail que são os anexos em pdf */

		MimeBodyPart anexoEmail = new MimeBodyPart();
		anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(SimuladorDePdf(), "application/pdf")));
		anexoEmail.setFileName("anexoemail.pdf");
		
		Multipart multpart =new MimeMultipart();
		multpart.addBodyPart(corpoEmail);
		multpart.addBodyPart(anexoEmail);
					
		message.setContent(multpart);
		
		
		Transport.send(message); /* ENVIANDO O E-MAIL */
	}
		
	/**
	 * Esse método simula o pdf ou qualquer arquivo que possa ser enviado por email.
	 * Voce pode pegar o arquivo na sua base de dados base64, byte[], Stream de
	 * arquivos. Pode esta em um banco de dados, ou e uma pasta. Retorna um pdf em
	 * branco com o texto do paragrafo, Exemplo
	 */
	private FileInputStream SimuladorDePdf() throws Exception {

		Document document = new Document();
		File file = new File("fileAnexoPdf ");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Este  conteudo é do Pdf, testando pdf, em java mail! "));
		document.close();

		return new FileInputStream(file);

	}
}
