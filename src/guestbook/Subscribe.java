package guestbook;
import java.util.Date;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity

public class Subscribe implements Comparable<Greeting> {

    @Id Long id;
    User user;
    
    private Subscribe() {}
    
    public Subscribe(User user) {

        this.user = user;
    }

    public User getUser() {
        return user;
    }

	@Override
	public int compareTo(Greeting o) {
		// TODO Auto-generated method stub
		return 0;
	}


}