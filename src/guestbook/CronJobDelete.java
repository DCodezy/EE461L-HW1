//http://tutorial-2-1208.appspot.com/ofyguestbook.jsp

package guestbook;

 
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.googlecode.objectify.ObjectifyService.*;

 
public class CronJobDelete extends HttpServlet {
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        Subscribe conversion = new Subscribe (user);		
        List<Subscribe> subscriptionList = ObjectifyService.ofy().load().type(Subscribe.class).list();   
		
		/*Finds user and deletes from list*/
		Iterator<Subscribe> i = subscriptionList.iterator();
		while (i.hasNext()) 
		{
		   Subscribe s = i.next(); 
		   if(s.getUser().equals(user))
		   {
		        ofy().delete().entity(conversion).now();
		   }
		}
        resp.sendRedirect("/ofyguestbook.jsp");
    }
}