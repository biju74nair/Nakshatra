package com.ak2006.Nakshatra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class NakshatraFinder {

	public static void main(String[] args) throws Exception {

		String url = "http://www.prokerala.com/astrology/nakshatra-finder/";

		String from = "T073000";
		String to = "T083000";
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);

		FileWriter fileWriter = new FileWriter(new File(
				"/Users/binair/Downloads/mool.html"));

		fileWriter.write("</body>");
		while (true) {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("Host", "www.prokerala.com");
			post.setHeader("Connection", "keep-alive");
			post.setHeader("Cache-Control", "max-age=0");
			post.setHeader("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			post.setHeader("Origin", "http//www.prokerala.com");
			post.setHeader("Upgrade-Insecure-Requests", "1");
			post.setHeader(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.125 Safari/537.36");
			post.setHeader("Content-Type", "application/x-www-form-urlencoded");
			post.setHeader("Referer",
					"http//www.prokerala.com/astrology/nakshatra-finder/");
			post.setHeader("Accept-Encoding", "gzip, deflate");
			post.setHeader("Accept-Language", "en-US,en;q=0.8");

			// year=2015&month=8&day=1&hour=8&min=6&apm=am&location=Pleasanton&loc=5383777&ayanamsa=1&la=en&p=1

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

			urlParameters.add(new BasicNameValuePair("year", String.valueOf(cal
					.get(Calendar.YEAR))));
			urlParameters.add(new BasicNameValuePair("month", String
					.valueOf(cal.get(Calendar.MONTH) + 1)));
			
			System.out.println("Request Date: "+cal.getTime());
			urlParameters.add(new BasicNameValuePair("day", String.valueOf(cal
					.get(Calendar.DATE))));

			// urlParameters.add(new BasicNameValuePair("year", "2015"));
			// urlParameters.add(new BasicNameValuePair("month", "5"));
			// urlParameters.add(new BasicNameValuePair("day", "7"));

			urlParameters.add(new BasicNameValuePair("hour", "8"));
			urlParameters.add(new BasicNameValuePair("min", "6"));
			urlParameters.add(new BasicNameValuePair("apm", "am"));
			urlParameters.add(new BasicNameValuePair("location", "Pleasanton"));
			urlParameters.add(new BasicNameValuePair("loc", "5383777"));
			urlParameters.add(new BasicNameValuePair("ayanamsa", "1"));
			urlParameters.add(new BasicNameValuePair("la", "en"));
			urlParameters.add(new BasicNameValuePair("p", "1"));

			UrlEncodedFormEntity u = new UrlEncodedFormEntity(urlParameters);

			post.setEntity(u);

			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {

				BufferedReader rd = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));

				String line = "";
				while ((line = rd.readLine()) != null) {
					if(line.contains("class=\"content col-xs-3 nakshatra-name\"")){
						// System.out.println(cal.getTime() + "==>   " + line);
						if (line.indexOf("Moola") != -1) {
							
							String date = dateFormat.format(cal.getTime());

							String html = String
									.format("\n<br><a href='https://www.google.com/calendar/render?action=TEMPLATE&hl=en&text=Temple&dates=%s/%s&location=&ctz=US/Pacific&details='>%s</a>",
											date + from, date + to, cal
													.getTime().toString());

							fileWriter.write(html);
							System.out.println(html);
							cal.add(Calendar.DATE, 25);
							break;
						}
					}
				}
				rd.close();
			}
			cal.add(Calendar.DATE, 1);
			client.getConnectionManager().shutdown();
			if (cal.get(Calendar.YEAR) == 2019 && cal.get(Calendar.MONTH) == 5) {
				break;
			}

		}
		fileWriter.write("</body>");
		fileWriter.close();

		System.out.println("Done");

	}
}

// <span class="content col-xs-3"><a
// href="/astrology/nakshatra/dhanishta-nakshatra.php">Dhanishta</a>(Andhaksha)</span>
