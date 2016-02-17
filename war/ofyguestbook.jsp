<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
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

 

  <body>
  	<header>
  		<div id="page-title">
  			<h1 id="main_title">Blissful Talk</h1>
  			<hr>
  			<p id="main_title_subtext">A relaxing and open blog</p>
  		</div>
  	</header>

 	<div class="container">

<%

    String guestbookName = request.getParameter("guestbookName");

    if (guestbookName == null) {

        guestbookName = "default";

    }

    pageContext.setAttribute("guestbookName", guestbookName);

    UserService userService = UserServiceFactory.getUserService();

    User user = userService.getCurrentUser();

    if (user != null) {

      pageContext.setAttribute("user", user);

%>

<p id="user-greeting">Hello, ${fn:escapeXml(user.nickname)}! (You can

<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>



<%

    } else {

%>

<p class="warning-message">

<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>

to post</p>

<%

    }

%>

 

<%

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

        <h1 class="page-headers">Latest Posts</h1>

        <%
        int greetingIndex = greetings.size() - 1;
        while(greetingIndex >= 0 && (greetings.size() - 1 - greetingIndex) < 5) {
        	Greeting greeting = greetings.get(greetingIndex);
            pageContext.setAttribute("greeting_content", greeting.getContent());
            if (greeting.getUser() == null) {
                %>
                <p class="blog-paragraph">An anonymous person wrote:</p>
                <%
            } else {
                pageContext.setAttribute("greeting_user", greeting.getUser());
                %>
                <p class="blog-paragraph"><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote:</p>
                <%
            }
            %>
            <blockquote class="blog-subparagraph">${fn:escapeXml(greeting_content)}</blockquote>
            <%
            greetingIndex -= 1;
        }
    }
    if (user != null) {

      pageContext.setAttribute("user", user);
      %>
      	<form action="/sign" method="post">
      		<div><textarea name="content" rows="10" cols="100"></textarea></div>
      		<div><input type="submit" value="Post Greeting" /></div>
      		<input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
    	</form>
      <%
      
   }

%>

	</div>
  </body>

</html>