package jobs;

import play.jobs.Job;
import play.jobs.Every;
import play.libs.WS;

@Every("1min")
public class PollingJob extends Job {
	
	public void doJob() {
		String url = System.getenv("POLLING_URL");
System.out.println("URL: " + url);
		if (url != null) {
			try {
				WS.url(url).get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}
