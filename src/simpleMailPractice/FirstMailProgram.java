package simpleMailPractice;

import java.io.File;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class FirstMailProgram 
{
	public static void main(String[] args) 
	{
		final String username="abhijeetkharkar@gmail.com";
		final String password="abhi#11690";
		Properties properties=new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session=Session.getDefaultInstance(properties, new Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});
		
		try
		{
			//String addressList[]={"abhijeetkharkar@gmail.com","Abhijeet.Kharkar@cognizant.com","anupakharkar@gmail.com","sanikakharkar@gmail.com","anushaaa.arjunan@cognizant.com"};
			String addressList[]={"abhijeetkharkar@gmail.com"};
			for(int i=0;i<addressList.length;i++)
			{
				Message message=new MimeMessage(session);
				message.setFrom(new InternetAddress("abhijeetkharkar@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(addressList[i]));
				message.setSubject("Testing Subject");
				message.setText("Dear Mail Crawler,"+ "\n\n This is a multiple-message-sending-with-the-use-of-java-code check \n\n\nRegards,\nAbhijeet Kharkar.");
				
				BodyPart bodypart=new MimeBodyPart();
				bodypart.setDataHandler(new DataHandler(new FileDataSource("C:\\Users\\Abhijeet\\Desktop\\ahm250_l19.pdf")));
				bodypart.setFileName("Attachment.pdf");
				
				Multipart multi=new MimeMultipart();
				multi.addBodyPart(bodypart);
				
				message.setContent(multi);
				
				Transport.send(message);				
				
			}
			System.out.println("Done");			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
