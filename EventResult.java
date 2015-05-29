# Java_EventPage

package gwangju.universiade2015;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi")
public class EventResult extends Activity {

	String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectDiskReads().detectDiskWrites().detectNetwork()
		.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		.penaltyLog().penaltyDeath().build());

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.eventresult);
		
		Management M = (Management) getApplication();
		id=M.GetId();
		

		ImageButton backac = (ImageButton) findViewById(R.id.back);
		backac.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onBackPressed();
				overridePendingTransition(R.anim.backleftout, R.anim.backleftin);
			}
		});
		
		
		String urlStr = "http://guniversiade.url.ph/present_list.php";

		TextView tv1 = (TextView) findViewById(R.id.textView3);

		StringBuilder sb = new StringBuilder();

		try {
			URL url = new URL(urlStr + "?" + "id="
					+ URLEncoder.encode(id, "UTF-8"));

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));

					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line + "\n");
					}
					br.close();
				}
				conn.disconnect();
			}

		} catch (Exception e) {

		}

		String jsonString = sb.toString();

		try {
			String res = "";
			JSONArray ja = new JSONArray(jsonString);
			JSONObject jo;

			for (int i = 0; i < ja.length(); i++) {
				jo = ja.getJSONObject(i);
				res += jo.getString("present") + "\n\n";
			}

			tv1.setText(res);
		}

		catch (JSONException e) {
			tv1.setText("상품 당첨 내역이 없습니다.");
		}
	}
}
