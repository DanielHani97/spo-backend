package my.com.fotia.osdec.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

@Controller
public class EmailController {
	
	private static final Pattern IMG_PATTERN = Pattern.compile("<img(\\s+.*?)(?:src\\s*=\\s*(?:'|\")(.*?)(?:'|\"))(.*?)/>", Pattern.DOTALL|Pattern.CASE_INSENSITIVE);

	public void sendEmail(String emailContent, String emailSubject, String emailTo, String emailCC){
			
			try 
			{
				/*String smtpUsername = "postmaster@sandboxe80990cab48944a9b6025d1a39dc82e5.mailgun.org";
				String smtpPassword = "22db6eb264a2ae7b88d3d0dea8ffc8ee";
				String smtpHost = "smtp.mailgun.org";*/
				String smtpUsername = "spo@osdec.gov.my";
				String smtpPassword = "29TdX598gz";
				String smtpHost = "10.29.16.222";
				int smtpPort = 25;
				String emailFrom = "SistemPengurusanOSDeC";
				
				JavaMailSenderImpl sender = new JavaMailSenderImpl();
				sender.setHost(smtpHost);
				sender.setPort(smtpPort);
				sender.setUsername(smtpUsername);
				sender.setPassword(smtpPassword);
				Properties prop = sender.getJavaMailProperties();
				prop.put("mail.smtp.socketFactory.port", "465");
				prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				prop.put("mail.smtp.auth", "true");
				//prop.put("mail.smtp.starttls.enable", "true");//for gunmail setting
				prop.put("mail.smtp.connectiontimeout", 10000);
				prop.put("mail.smtp.timeout", 10000);
				prop.put("mail.debug", "true");
				
				MimeMessage message = sender.createMimeMessage();
				MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
	            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	            CommandMap.setDefaultCommandMap(mc);
	            
				MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
				helper.setFrom(smtpUsername);
				helper.setSubject(emailSubject);
				helper.setTo(InternetAddress.parse(emailTo));
				if(emailCC != null && !"".equals(emailCC)){
					helper.setCc(emailCC);
				}
				
				Matcher m = IMG_PATTERN.matcher(emailContent);
				int count = 0;
				Map<String, String> mapSrc = new HashMap<String, String>();
				List<String> listImageString = new ArrayList();
				while (m.find()) { //Find if emailContent has <img>
				    for (int i = 1; i < m.groupCount() + 1; i++) {
						if (i == 2) { //get Image src (data for Base64)
							String imageData = m.group(i);
							imageData = imageData.replaceFirst("^data:image/[^;]*;base64,?","");
							String imageDataBytes = imageData.substring(imageData.indexOf(",")+1);
							mapSrc.put("image"+count, imageDataBytes);
						}
				    }
				    listImageString.add("image"+count);
				    //Replace emailContent <img with <img src='cid
				    emailContent = emailContent.replace(m.group(0), "<img src='cid:image"+count+"'>");
				    count ++;
				}
				helper.setText(emailContent, true);
				
				count=0;
				for(String imageKey : listImageString){
					String imageDataBytes = mapSrc.get(imageKey);
					byte[] imageByteArr = Base64.decodeBase64(imageDataBytes.getBytes());//Decode Base64
					ByteArrayDataSource imgDS = new ByteArrayDataSource( imageByteArr, "image/png");
					helper.addInline("image"+count, imgDS);//Add Image to emailContent
					count ++;
				}
	
				sender.send(message);
				
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
