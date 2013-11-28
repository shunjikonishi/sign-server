package jobs;

import play.jobs.Job;
import play.jobs.Every;
import play.libs.WS;

@Every("30min")
public class PollingJob {
	
	public void doJob() {
		String url = System.getenv("POLLING_URL");
		if (url != null) {
			try {
				WS.url(url).get();
			} catch (Exception e) {
			}
		}
    }
}
