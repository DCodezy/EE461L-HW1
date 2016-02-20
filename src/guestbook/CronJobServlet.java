package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
	private static Date lastDate = null;
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String msgBody = "test";
		List<Subscribe> subscriptionList = ObjectifyService.ofy().load().type(Subscribe.class).list();   
		
		ObjectifyService.register(Greeting.class);
		List<Greeting> tempGreetings = ObjectifyService.ofy().load().type(Greeting.class).list();   
		Collections.sort(tempGreetings); 
		List<Greeting> greetings = new ArrayList<Greeting>();
		if(lastDate == null){
			greetings = tempGreetings;
		} else{
			boolean foundOne = false;
			for(Greeting indivGreeting : tempGreetings){
				if(indivGreeting.getDate().after(lastDate)){
					greetings.add(indivGreeting);
					foundOne = true;
				}
			}
			if(foundOne){
				lastDate = new Date();
			}
		}
		
		
		String newUpdates = "";
		if(greetings.size() == 0){
			newUpdates = "No new updates... :(";
		} else{
			for(Greeting indivGreeting : greetings){
				newUpdates = newUpdates + indivGreeting.getUser().getNickname() + "\n" + indivGreeting.getContent() + "\n\n";
			}
		}
		
		
		
		try {
			if(subscriptionList.size() != 0)
			{
				for(Subscribe sub : subscriptionList)
				{
					_logger.info("Cron Job has been executed");
				    Message msg = new MimeMessage(session);
				    msg.setFrom(new InternetAddress("xxx@cool-mile-122702.appspotmail.com", "Example.com Admin")); //change this once we know actual email
				    msg.addRecipient(Message.RecipientType.TO,
				     new InternetAddress(sub.getUser().getEmail(), sub.getUser().getEmail()));
				    msg.setSubject("Daily Subscription Digest!");
				    msg.setText(newUpdates);		//put digest info here
				    Transport.send(msg);
				}
			}
		
		} catch (AddressException e) {
		    // ...
		} catch (MessagingException e) {
		    // ...
		}
		catch (Exception ex) 
		{
		}
        resp.sendRedirect("/ofyguestbook.jsp");
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException 
	{
		doGet(req, resp);
	}
}