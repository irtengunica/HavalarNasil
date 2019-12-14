package com.example.havalarnasil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HavaDurumu extends Activity {
	TextView temperature, description;
	EditText cityName;
	Button weatherButton;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hava_durumu);
        
        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        cityName = (EditText) findViewById(R.id.cityname);
        weatherButton = (Button) findViewById(R.id.weatherbutton);
        
        weatherButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					new getWeatherCondition().execute();		
			}
		});
          
    }
    
    private class getWeatherCondition extends AsyncTask<Void, Void, Void> {	
    	
    	int tempNo;
    	
    	@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			String weatherUrl = "http://api.openweathermap.org/data/2.5/find?q="
					+cityName.getText()+"&units=metric";
			
			JSONObject jsonObject=null;

			try {
				String json = JSONParser.getJSONFromUrl(weatherUrl);
				try {
					//en baştaki kocaman json objesi
					jsonObject = new JSONObject(json);
				} catch (JSONException e) {
					Log.e("JSON Parser",
							"json objesi yaratamadı ya la!" + e.toString());
				}
				//Parçalara ayırma zamanıııııı!		
				//1. En baştaki Json Objesinden list adlı array'i çek
				JSONArray listArray = jsonObject.getJSONArray("list");
				//2. List adlı array'in ilk objesini çek.
				JSONObject firstObj = listArray.getJSONObject(0);
				//3. İlk objenin main adlı objesini çek.
				JSONObject mainObj = firstObj.getJSONObject("main");
				//3. main objesi içinden temp sayısını çek
				tempNo =  mainObj.getInt("temp");
				//Olleyyyy! sıcaklık değerine ulaştık.
				
			}
			catch (JSONException e) {

				Log.e("json", "json'ı ayrıştıramadı ya la!");
			}	
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			temperature.setText(tempNo+"\u2103 Anam");
		}
	}

}
