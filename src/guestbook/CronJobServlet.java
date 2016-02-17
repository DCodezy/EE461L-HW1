package guestbook;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CronJobServlet extends HttpServlet 
{
	private static final Logger _logger = Logger.getLogger(CronJobServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
	
		try 
		{
		_logger.info("Cron Job has been executed");
		
		//Put your logic here

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