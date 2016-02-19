package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



@SuppressWarnings("serial")
public class CronJobServlet extends HttpServlet 
{
	private static final Logger _logger = Logger.getLogger(CronJobServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String msgBody = "...";
		
		try {
			//in here, do a for loop with all the subscription email addresses.
			
			
			_logger.info("Cron Job has been executed");
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("user@example.com", "Mr. User"));
		    msg.setSubject("Your Example.com account has been activated");
		    msg.setText(msgBody);
		    Transport.send(msg);
		
		} catch (AddressException e) {
		    // ...
		} catch (MessagingException e) {
		    // ...
		}
		catch (Exception ex) 
		{
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException 
	{
		doGet(req, resp);
	}
}