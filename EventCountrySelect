# Java_EventPage

package gwangju.universiade2015;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EventCountrySelect extends Activity {
	String s_watermelon = null;
	String s_seed = null;
	
	////////////////////////////////
	String s_number = null;
	String s_game = null;
	String s_date = null;
	String s_time = null;
	String s_country1 = null;
	String s_country2 = null;
	String s_result = null;
	int check = 0;
	
	String path;
	File file;
	String id;
	
	@SuppressWarnings("unused")
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
		setContentView(R.layout.event_country_select);
		
		Management M = (Management) getApplication();
		id=M.GetId();
		
		
		Intent it1 = getIntent();
		
		TextView t_game = (TextView) findViewById(R.id.event_gametext);
		TextView t_date = (TextView) findViewById(R.id.event_datetext);
		TextView t_time = (TextView) findViewById(R.id.event_timetext);
		TextView t_country1 = (TextView) findViewById(R.id.event_country1);
		TextView t_country2 = (TextView) findViewById(R.id.event_country2);

		
		//////////////이벤트참여 버튼 눌렀을때 실행되는 부분이며, 준비중인이벤트인지 종료된이벤트인지 확인한다///////////////
		String urlstr = "http://guniversiade.url.ph/Event_vs.php";
		StringBuilder sb = new StringBuilder();

		try {
			check = 0;

			URL url = new URL(urlstr + "?" + "number="
					+ it1.getStringExtra("it_number"));
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
					t_game.setText("http_not");
				}
				conn.disconnect();
			}

		} catch (Exception e) {

		}

		String jsonString = sb.toString();

		try {
			s_game = "";
			s_date = "";
			s_time = "";
			s_country1 = "";
			s_country2 = "";
			s_result = "";
			s_number = "";

			JSONArray ja = new JSONArray(jsonString);
			JSONObject jo = ja.getJSONObject(0);

			s_game = jo.getString("game");
			s_date = jo.getString("date");
			s_time = jo.getString("time");
			s_country1 = jo.getString("country1");
			s_country2 = jo.getString("country2");
			s_result = jo.getString("result");
			s_number = jo.getString("number");
			
			if(!s_result.equals("")){
				check = 1;
				Toast.makeText(getApplicationContext(), "종료된 이벤트입니다.",
						Toast.LENGTH_SHORT).show();
				
				super.onBackPressed();
			}

			t_game.setText(s_game);
			t_date.setText(s_date);
			t_time.setText(s_time);
			t_country1.setText(s_country1);
			t_country2.setText(s_country2);
			
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "준비중입니다!",
					Toast.LENGTH_SHORT).show();
			super.onBackPressed();
		}
		////////////////////////////////////user의 id를 받아와 이벤트에 참여하였으면 참여했다는 토스를 띄운다////////////////////////

				String checkurl = "http://guniversiade.url.ph/event_user_check.php";
				StringBuilder checksb = new StringBuilder();

				try {

					URL url = new URL(checkurl + "?" + "id=" + id + "&number="
							+ s_number);
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
								checksb.append(line + "\n");
							}
							br.close();

						} else {

						}
						conn.disconnect();
					}
				} catch (Exception e) {
				}

				String checkString = checksb.toString();

				try {

					JSONArray checkja = new JSONArray(checkString);

					JSONObject checkjo = checkja.getJSONObject(0);
					
					if(check == 0){
						Toast.makeText(getApplicationContext(), "이미 참여하였습니다.",
								Toast.LENGTH_SHORT).show();
						super.onBackPressed();
					}

				} catch (JSONException e) {

				}

		// ///////////////////////////참여하지않은이벤트이면 참여할수있도록한다./////////////////////////////////////////

		final RadioGroup radio_group = (RadioGroup) findViewById(R.id.country_radio);
		final RadioButton radio_bt = new RadioButton(this);

		Button event_check_btn1 = (Button) findViewById(R.id.event_check_btn1);
		event_check_btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String r_result = null;
				int radio_id = radio_group.getCheckedRadioButtonId();
				radio_bt.setId(radio_id);
				if (radio_id == -1) {
					Toast.makeText(getApplicationContext(), "나라를 선택해주세요.",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (radio_id == R.id.event_radioBtn1)
					r_result = s_country1;
				else if (radio_id == R.id.event_radioBtn2)
					r_result = s_country2;

				String urlstr1 = "http://guniversiade.url.ph/event_result.php";
				StringBuilder sb1 = new StringBuilder();
				try {
					URL url = new URL(urlstr1 + "?" + "id=" + id
							+ "&number=" + s_number
							+ "&betting=" + URLEncoder.encode(r_result, "UTF-8") 
							+ "&game=" + URLEncoder.encode(s_game, "UTF-8")
							+ "&d=" + URLEncoder.encode(s_date, "UTF-8") 
							+ "&t=" + URLEncoder.encode(s_time, "UTF-8")
							+ "&s_check=" + "false");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();

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
				Toast.makeText(getApplicationContext(),
						"이벤트에 참여하였습니다! 참여내역에서 확인해주세요~", Toast.LENGTH_SHORT)
						.show();
				finish();

			}
		});
		////////////////////////////////////엑티비티닫는버튼(뒤로가기버튼)//////////////////////////////////////////

		Button event_check_btn2 = (Button) findViewById(R.id.event_check_btn2);
		event_check_btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		

	}

}
