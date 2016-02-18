<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="guestbook.Greeting" %>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 

<html>

	<head>
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
	</head>

	<div class="container">

	<%

    String guestbookName = request.getParameter("guestbookName");

    if (guestbookName == null) {

        guestbookName = "default";

    }

    pageContext.setAttribute("guestbookName", guestbookName);
    
    // Run an ancestor query to ensure we see the most up-to-date

    // view of the Greetings belonging to the selected Guestbook.

    //Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);

    ObjectifyService.register(Greeting.class);

	List<Greeting> greetings = ObjectifyService.ofy().load().type(Greeting.class).list();   

	Collections.sort(greetings); 

    if (greetings.isEmpty()) {

        %>

        <p class="blog-paragraph">No Current Blog Posts :(</p>

        <%

    } else {

        %>

        <h1 class="page-headers">All Posts</h1>

        <%
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, 'at' hh:mm a zzz");
        for(int i = greetings.size() - 1; i >= 0; i--) {
        	Greeting greeting = greetings.get(i);
            pageContext.setAttribute("greeting_content", greeting.getContent());
            pageContext.setAttribute("greeting_user", greeting.getUser());
            pageContext.setAttribute("greeting_date", dateFormat.format(greeting.getDate()));
                %>
                <p class="blog-paragraph"><b>${fn:escapeXml(greeting_user.nickname)}</b> on <span class="post-date">${fn:escapeXml(greeting_date)}</span></p>
            	<blockquote class="blog-subparagraph">${fn:escapeXml(greeting_content)}</blockquote>
            <%
        }
    }

%>
	<hr class="section-separator">
	<a href="/ofyguestbook.jsp" id="home-page">&lt;Back</a>
	</div>
  </body>

</html>