package org.jboss.as.quickstarts.ejbtimer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 * 
 * This EJB 3.1 Timer Service checks the availability of the url "http://localhost:8080" every 5 seconds.
 * 
 * @author Serge Pagop (spagop@redhat.com)
 *
 */
@Stateless
public class MyTimerService {

	private static final String URLName = "http://localhost:8080";

	@SuppressWarnings("unused")
	@Schedule(dayOfWeek = "0-5", hour = "*", minute = "*", second = "*/5", persistent = false)
	private void checkURLAvailability() {
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			Logger.getLogger("").info("");
			Logger.getLogger("")
					.info("HTTP Request to "
							+ URLName
							+ " has a HTTP Reponse with the status code HTTP/1.0 "
							+ con.getResponseCode());
			Logger.getLogger("").info("");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}