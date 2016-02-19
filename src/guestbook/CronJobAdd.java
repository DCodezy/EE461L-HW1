//http://tutorial-2-1208.appspot.com/ofyguestbook.jsp

package guestbook;

 
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.googlecode.objectify.ObjectifyService.*;

 
public class CronJobAdd extends HttpServlet {
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)

                throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        Subscribe subscribe = new Subscribe(user);
        ofy().save().entity(subscribe).now();
        resp.sendRedirect("/ofyguestbook.jsp");
    }
}