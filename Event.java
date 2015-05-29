# Java_EventPage

package gwangju.universiade2015;

import java.io.BufferedReader;
import java.io.File;
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
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Event extends Activity {
	String s_watermelon = null;
	String s_seed = null;
	
	String result_0 = null;
	String result_1 = null;
	String result_2 = null;
	String result_3 = null;
	String result_4 = null;
	String result_5 = null;
	
	String path;
	File file;
	String id;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.event);
		

		Management M = (Management) getApplication();
		id=M.GetId();
		
//top		
		ImageButton homeac = (ImageButton) findViewById(R.id.home);
		homeac.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		ImageButton mypageac = (ImageButton) findViewById(R.id.mypage);
		mypageac.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(id.equalsIgnoreCase(" ")) {
					Toast.makeText(getApplicationContext(), "로그인을 한 후 이용하세요.", Toast.LENGTH_SHORT).show();
				}
				
				else {
					Intent intent = new Intent(getApplicationContext(), Mypage.class);
					startActivity(intent);
				}
			}
		});
		
		ImageButton loginac = (ImageButton) findViewById(R.id.login);
		loginac.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(id.equalsIgnoreCase(" ")) {
					Intent intent = new Intent(getApplicationContext(), Login.class);
					startActivity(intent);
				}
				
				else {
					Intent intent = new Intent(getApplicationContext(), Logout.class);
					startActivity(intent);
				}
			}
		});


//main	
		String s_event_frame = null;
		String s_result = null;

		TextView t_event_frame1 = (TextView) findViewById(R.id.event_frame1);
		TextView t_event_frame2 = (TextView) findViewById(R.id.event_frame2);
		TextView t_event_frame3 = (TextView) findViewById(R.id.event_frame3);
		TextView t_event_frame4 = (TextView) findViewById(R.id.event_frame4);
		TextView t_event_frame5 = (TextView) findViewById(R.id.event_frame5);
		TextView t_event_frame6 = (TextView) findViewById(R.id.event_frame6);

		String urlstr = "http://guniversiade.url.ph/event_main.php";
		StringBuilder sb = new StringBuilder();

		try {

			URL url = new URL(urlstr);
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

				} else {
					t_event_frame1.setText("http_not");
				}
				conn.disconnect();
			}
		} catch (Exception e) {
		}

		String jsonString = sb.toString();

		try {
			
			result_0 = "";
			result_1 = "";
			result_2 = "";
			result_3 = "";
			result_4 = "";
			result_5 = "";
			
			JSONArray ja = new JSONArray(jsonString);

			for (int i = 0; i < 6; i++) {
				
				JSONObject jo = ja.getJSONObject(i);
				
				s_event_frame = "";
				s_result = "";

				s_event_frame = " " + jo.getString("game") + " "
						+ jo.getString("country1") + " VS "
						+ jo.getString("country2");
				
				s_result = jo.getString("result");

				if (i == 0){
					if(s_result.equals(""))
						t_event_frame1.setText(s_event_frame);
					else
						t_event_frame1.setText(" " + jo.getString("game") + " " + s_result + " 승리!");
						result_0 = jo.getString("number");
				}
				else if (i == 1){
					if(s_result.equals(""))
						t_event_frame2.setText(s_event_frame);
					else
						t_event_frame2.setText(" " + jo.getString("game") + " " + s_result + " 승리!");
						result_1 = jo.getString("number");
				}
				else if (i == 2){
					if(s_result.equals(""))
						t_event_frame3.setText(s_event_frame);
					else
						t_event_frame3.setText(" " + jo.getString("game") + " " + s_result + " 승리!");
						result_2 = jo.getString("number");
				}
				else if (i == 3){
					if(s_result.equals(""))
						t_event_frame4.setText(s_event_frame);
					else
						t_event_frame4.setText(" " + jo.getString("game") + " " + s_result + " 승리!");
						result_3 = jo.getString("number");
				}
				else if (i == 4){
					if(s_result.equals(""))
						t_event_frame5.setText(s_event_frame);
					else
						t_event_frame5.setText(" " + jo.getString("game") + " " + s_result + " 승리!");
						result_4 = jo.getString("number");
				}
				else if (i == 5){
					if(s_result.equals(""))
						t_event_frame6.setText(s_event_frame);
					else
						t_event_frame6.setText(" " + jo.getString("game") + " " + s_result + " 승리!");
						result_5 = jo.getString("number");
				}
			}

		} catch (JSONException e) {

		}

		Button event_button1 = (Button) findViewById(R.id.event_button1);
		event_button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						EventGiftbox.class);
				startActivity(intent);

			}
		});

		Button event_button2 = (Button) findViewById(R.id.event_button2);
		event_button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventParticipate.class);
				startActivity(intent);

			}
		});

		Button event_parti_button6 = (Button) findViewById(R.id.event_parti_button6);
		event_parti_button6.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventCountrySelect.class);

				intent.putExtra("it_number", result_5);
				startActivity(intent);
			}
		});

		Button event_parti_button5 = (Button) findViewById(R.id.event_parti_button5);
		event_parti_button5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventCountrySelect.class);

				intent.putExtra("it_number", result_4);
				startActivity(intent);
			}
		});

		Button event_parti_button4 = (Button) findViewById(R.id.event_parti_button4);
		event_parti_button4.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventCountrySelect.class);

				intent.putExtra("it_number", result_3);
				startActivity(intent);
			}
		});

		Button event_parti_button3 = (Button) findViewById(R.id.event_parti_button3);
		event_parti_button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventCountrySelect.class);

				intent.putExtra("it_number", result_2);
				startActivity(intent);
			}
		});

		Button event_parti_button2 = (Button) findViewById(R.id.event_parti_button2);
		event_parti_button2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventCountrySelect.class);

				intent.putExtra("it_number", result_1);
				startActivity(intent);
			}
		});

		Button event_parti_button1 = (Button) findViewById(R.id.event_parti_button1);
		event_parti_button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						EventCountrySelect.class);

				intent.putExtra("it_number", result_0);
				startActivity(intent);
			}
		});
	}

	// ////////////////////////////////////////경품뽑을때 수박하나 줄고, 씨앗뽑으면 개수늘어나고, 씨앗30개면 수박1개///////////////////////////////////////
	public void onResume() {
		super.onResume();
		
		// //////////////////////////수박이랑 씨앗개수 받아와서 출력하는////////////////////////////////////////////////////////////////

		TextView t_watermelon = (TextView) findViewById(R.id.watermelon_count);
		TextView t_seed = (TextView) findViewById(R.id.seed_count);

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
			s_watermelon = "";
			s_seed = "";

			JSONArray count_ja = new JSONArray(count_jsonString);
			JSONObject count_jo = count_ja.getJSONObject(0);

			s_watermelon = count_jo.getString("watermelon") + "개";
			s_seed = count_jo.getString("seed") + "개";

			t_watermelon.setText(s_watermelon);
			t_seed.setText(s_seed);
		} catch (JSONException e) {

		}

		///////////////////////////////////////////////////////////////////////////
		
	}
}
