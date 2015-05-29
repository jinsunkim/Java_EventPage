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
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EventParticipate extends Activity {

	String id;

	String user_number = null;
	String event_number = null;
	String event_result = null;

	int s_watermelon = 0;
	int s_seed = 0;
	int temp_seed = 20;
	
	
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
		setContentView(R.layout.event_participate);
		
		Management M = (Management) getApplication();
		id=M.GetId();
		
		
		// //////////////////////////////////////////////////////////////////////////

		String checkurl = "http://guniversiade.url.ph/event_participate.php";
		StringBuilder checksb = new StringBuilder();

		try {
			URL check_url = new URL(checkurl + "?" + "id=" + id);
			HttpURLConnection conn = (HttpURLConnection) check_url
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
						checksb.append(line + "\n");
					}
					br.close();

				} else {

				}
				conn.disconnect();
			}
		} catch (Exception e) {

		}

		String partiString;

		TextView parti_text = (TextView) findViewById(R.id.parti_textview);

		partiString = checksb.toString();

		try {
			String parti_string = "";

			JSONArray partija = new JSONArray(partiString);
			JSONObject partijo = null;

			for (int i = 0; i < partija.length(); i++) {
				partijo = partija.getJSONObject(i);

				parti_string += partijo.getString("date") + " "
						+ partijo.getString("time") + " "
						+ partijo.getString("game") + " | "
						+ partijo.getString("betting") + "\n";

			}

			parti_text.setText(parti_string);

		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "참여한이벤트가 없습니다!", Toast.LENGTH_SHORT).show();
		    super.onBackPressed();
		}

		// ///////////////////////////////////////출력
		// 끝////////////////////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////////////////////////////////////////////

		String t_url = "http://guniversiade.url.ph/event_participate_empty.php";
		StringBuilder t_sb = new StringBuilder();

		try {
			URL tt_url = new URL(t_url + "?" + "id=" + id + "&s_check="
					+ "false");
			HttpURLConnection t_conn = (HttpURLConnection) tt_url
					.openConnection();

			if (t_conn != null) {
				t_conn.setConnectTimeout(10000);
				t_conn.setUseCaches(false);

				if (t_conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader t_br = new BufferedReader(
							new InputStreamReader(t_conn.getInputStream()));

					while (true) {
						String t_line = t_br.readLine();
						if (t_line == null)
							break;
						t_sb.append(t_line + "\n");
					}
					t_br.close();

				} else {

				}
				t_conn.disconnect();
			}
		} catch (Exception e) {

		}

		// ////////////////////////////////////이벤트받아오기//////////////////////////////////////////////////

		String all_urlstr = "http://guniversiade.url.ph/event_main.php";
		StringBuilder all_sb = new StringBuilder();

		try {

			URL all_url = new URL(all_urlstr);
			HttpURLConnection all_conn = (HttpURLConnection) all_url
					.openConnection();

			if (all_conn != null) {
				all_conn.setConnectTimeout(10000);
				all_conn.setUseCaches(false);

				if (all_conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader all_br = new BufferedReader(
							new InputStreamReader(all_conn.getInputStream()));

					while (true) {
						String all_line = all_br.readLine();
						if (all_line == null)
							break;
						all_sb.append(all_line + "\n");
					}
					all_br.close();

				} else {

				}
				all_conn.disconnect();
			}
		} catch (Exception e) {

		}

		// /////////////////////////////////////////////////

		String t_String;

		t_String = t_sb.toString();

		try {
			user_number = "";

			JSONArray t_ja = new JSONArray(t_String);
			JSONObject t_jo = null;

			for (int i = 0; i < t_ja.length(); i++) {
				t_jo = t_ja.getJSONObject(i);

				user_number = t_jo.getString("number");

				// ///////////////////////////////////////////////////////

				String all_jsonString = all_sb.toString();

				try {
					event_result = "";
					event_number = "";

					JSONArray all_ja = new JSONArray(all_jsonString);
					JSONObject all_jo = null;

					for (int j = 0; j < 6; j++) {
						all_jo = all_ja.getJSONObject(j);
						if (!all_jo.getString("result").equals("")) // 결과값이 있으면
																	// 들어감
						{
							if (all_jo.getString("number").equals(user_number)) // 넘버링이
																				// 같으면
																				// 들어감
							{
								event_result = all_jo.getString("result"); // 결과
																			// 값과
								event_number = all_jo.getString("number"); // 넘버링값을
																			// 받음
								if(t_jo.getString("betting").equals(event_result))
								{
									// ////////////////////////////////////update//////////////////////////////////////////////////////
									String seed_urlstr1 = "http://guniversiade.url.ph/event_seed_update.php";
									StringBuilder seed_sb1 = new StringBuilder();
									try {
										URL seed_url = new URL(seed_urlstr1
												+ "?"
												+ "id="
												+ id
												+ "&number="
												+ event_number
												+ "&betting="
												+ URLEncoder.encode(event_result,
														"UTF-8") + "&s_check="
												+ "true");
										HttpURLConnection seedconn = (HttpURLConnection) seed_url
												.openConnection();
	
										if (seedconn != null) {
											seedconn.setConnectTimeout(10000);
											seedconn.setUseCaches(false);
	
											if (seedconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
												BufferedReader seed_br = new BufferedReader(
														new InputStreamReader(
																seedconn.getInputStream()));
	
												while (true) {
													String seedline = seed_br
															.readLine();
													if (seedline == null)
														break;
													seed_sb1.append(seedline + "\n");
												}
												seed_br.close();
											}
											seedconn.disconnect();
										}
									} catch (Exception e) {
	
									}
									// //////////////////////////////////////////////////////////////////////////////////
									String count_url = "http://guniversiade.url.ph/event_w_s_count.php";
									StringBuilder count_sb = new StringBuilder();
	
									try {
	
										URL c_url = new URL(count_url + "?" + "id="
												+ id);
										HttpURLConnection conn = (HttpURLConnection) c_url
												.openConnection();
	
										if (conn != null) {
											conn.setConnectTimeout(10000);
											conn.setUseCaches(false);
	
											if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
												BufferedReader count_br = new BufferedReader(
														new InputStreamReader(conn
																.getInputStream()));
	
												while (true) {
													String line = count_br
															.readLine();
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
	
										JSONArray count_ja = new JSONArray(
												count_jsonString);
										JSONObject count_jo = count_ja
												.getJSONObject(0);
	
										s_watermelon = Integer.parseInt(count_jo
												.getString("watermelon"));
										s_seed = Integer.parseInt(count_jo
												.getString("seed"));
	
										s_seed = s_seed + temp_seed;
	
										if (s_seed >= 30) {
											s_seed = s_seed - 30;
											s_watermelon = s_watermelon + 1;
										}
	
									} catch (JSONException e) {
	
									}
									// ///////////////////////////////////////////////////////
	
									String w_s_urlstr1 = "http://guniversiade.url.ph/event_w_s_update.php";
									StringBuilder w_s_sb1 = new StringBuilder();
									try {
										URL url = new URL(w_s_urlstr1 + "?" + "id="
												+ id + "&watermelon="
												+ s_watermelon + "&seed=" + s_seed);
										HttpURLConnection conn = (HttpURLConnection) url
												.openConnection();
	
										if (conn != null) {
											conn.setConnectTimeout(10000);
											conn.setUseCaches(false);
	
											if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
												BufferedReader w_s_br = new BufferedReader(
														new InputStreamReader(conn
																.getInputStream()));
	
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
									// //////////////////////////////////////////////////////////////////////////////////
								}
							}
						}
					}

				} catch (JSONException e) {

				}

			}

		} catch (JSONException e) {

		}

	}
}
