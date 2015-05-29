# Java_EventPage

package gwangju.universiade2015;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EventGiftbox extends Activity {
	int s_watermelon;	
	String id;
	
	@SuppressLint("HandlerLeak")
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
		setContentView(R.layout.event_giftbox);
		
		Management M = (Management) getApplication();
		id=M.GetId();
		
		
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

			JSONArray count_ja = new JSONArray(count_jsonString);
			JSONObject count_jo = count_ja.getJSONObject(0);

			s_watermelon = Integer.parseInt(count_jo.getString("watermelon"));
			if (s_watermelon == 0) {
				Toast.makeText(getApplicationContext(), "수박이 없습니다 (ㅠ.ㅠ)",
						Toast.LENGTH_SHORT).show();
				
				super.onBackPressed();
				
			}
			else if(s_watermelon > 0)
			{
				ImageView Event_box = (ImageView) findViewById(R.id.gift_box);
				Event_box.setImageResource(R.drawable.event_box);
				Animation animation = AnimationUtils.loadAnimation(this,
						R.layout.event_rotate);

				animation.setFillAfter(true);
				animation.setRepeatMode(Animation.RESTART);
				Event_box.startAnimation(animation);
				animation.setRepeatCount(Animation.INFINITE);

				Handler handler = new Handler() {
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						startActivity(new Intent(EventGiftbox.this, EventGift.class));
						finish();
					}
				};
				handler.sendEmptyMessageDelayed(0, 1500);
			}

		} catch (JSONException e) {

		}

		
	}
}
