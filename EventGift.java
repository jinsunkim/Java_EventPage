# Java_EventPage

package gwangju.universiade2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EventGift extends Activity {
	int s_watermelon = 0;
	int s_seed = 0;
	
	String path;
	File file;
	String id;
	
	

	public static final int randomimage[] = { R.drawable.gift1,
			R.drawable.gift2, R.drawable.gift3, R.drawable.gift4,
			R.drawable.gift5 }; // 경품이미지


	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event_gift);
		
		Management M = (Management) getApplication();
		id=M.GetId();

		Random index = new Random();
		int randomnumber = index.nextInt(10);

		if (randomnumber > 8) {
			randomnumber = 0;
		} else if (randomnumber > 6) {
			randomnumber = 1;
		} else if (randomnumber > 4) {
			randomnumber = 2;
		} else if (randomnumber > 2) {
			randomnumber = 3;
		} else
			randomnumber = 4;

		int gif = randomimage[randomnumber];

		ImageView event_gift = (ImageView) findViewById(R.id.event_gift);
		event_gift.setImageResource(gif);

		// /////////////////////////////////////////////////////////////////////////////

		String present = null;
		if (randomnumber == 0)
			present = "씨앗 20개";
		else if (randomnumber == 1)
			present = "주유상품권";
		else if (randomnumber == 2)
			present = "외식상품권";
		else if (randomnumber == 3)
			present = "호텔숙박권";
		else if (randomnumber == 4)
			present = "씨앗10개";

		String urlstr1 = "http://guniversiade.url.ph/event_present.php";
		StringBuilder sb1 = new StringBuilder();
		try {
			URL url = new URL(urlstr1 + "?" + "id=" + id + "&present="
					+ URLEncoder.encode(present, "UTF-8"));
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
						sb1.append(line + "\n");
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (Exception e) {

		}

		Toast.makeText(getApplicationContext(), "받은 선물은 마이페이지에서 확인할 수 있습니다!",
				Toast.LENGTH_SHORT).show();
		
		// ////////////////////////////////////////////////////////////////////////////

		String count_url = "http://guniversiade.url.ph/event_w_s_count.php";
		StringBuilder count_sb = new StringBuilder();

		try {

			URL c_url = new URL(count_url + "?" + "id=" + id);
			HttpURLConnection conn = (HttpURLConnection) c_url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader count_br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));

					while (true) {
						String line = count_br.readLine();
						if (line == null)
							break;
						count_sb.append(line + "\n");
					}
					count_br.close();

				} else {

				}
				conn.disconnect();
			}
		} catch (Exception e) {
		}

		String count_jsonString = count_sb.toString();

		try {

			s_watermelon = 0;
			s_seed = 0;

			JSONArray count_ja = new JSONArray(count_jsonString);
			JSONObject count_jo = count_ja.getJSONObject(0);

			s_watermelon = Integer.parseInt(count_jo.getString("watermelon"));
			s_seed = Integer.parseInt(count_jo.getString("seed"));
			
			s_watermelon = s_watermelon - 1;

			if (randomnumber == 0) {
				s_seed = s_seed + 20;
			} else if (randomnumber == 4) {
				s_seed = s_seed + 10;
			}

			if (s_seed >= 30) {
				s_seed = s_seed - 30;
				s_watermelon = s_watermelon + 1;
			}

		} catch (JSONException e) {

		}

		// ///////////////////////////////////////////////////////////////////////////////////////

		String w_s_urlstr1 = "http://guniversiade.url.ph/event_w_s_update.php";
		StringBuilder w_s_sb1 = new StringBuilder();
		try {
			URL url = new URL(w_s_urlstr1 + "?" + "id=" + id + "&watermelon=" + s_watermelon
					+ "&seed=" + s_seed);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader w_s_br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));

					while (true) {
						String line = w_s_br.readLine();
						if (line == null)
							break;
						w_s_sb1.append(line + "\n");
					}
					w_s_br.close();
				}
				conn.disconnect();
			}
		} catch (Exception e) {

		}
		Toast.makeText(getApplicationContext(), "받은 선물은 마이페이지에서 확인할 수 있습니다!",
				Toast.LENGTH_SHORT).show();
	}
}
