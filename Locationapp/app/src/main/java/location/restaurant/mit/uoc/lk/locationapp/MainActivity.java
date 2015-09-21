package location.restaurant.mit.uoc.lk.locationapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    private String macAddress,latitude,longitude,orderid;
    private  Timer timer;
    private TimerTask timerTask;
    private LocationManager locationManager;
    private Button startButton;
    private Button stopButton;
    private EditText orderNo;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopButton = (Button)findViewById(R.id.butStop);
        startButton = (Button)findViewById(R.id.button);
        orderNo=(EditText)findViewById(R.id.txtOrderNo);

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress= wInfo.getMacAddress();
        locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stoptimertask(v);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderid=orderNo.getText().toString();
                startTimer();
                startButton.setText("Runing");
        }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
        protected void onResume() {
                super.onResume();
                //onResume we start our timer so it can start when the app comes from the background
               // startTimer();
                //startButton.setText("Runing");
            }


    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();
        //schedule the timer, after the first 5000ms the TimerTask will run every 50000000ms
        timer.schedule(timerTask, 5000, 10000); //
            }

     public void stoptimertask(View v) {
         //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
                    }
         startButton.setText("Start");
         finish();
     }


    @Override
    protected void onDestroy() {
    // closing Entire Application
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }


    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {//get the current timeStamp
                        // Define a listener that responds to location updates
                        LocationListener locationListener = new LocationListener() {
                            public void onLocationChanged(Location location) {

                                // Called when a new location is found by the network location provider.
                                latitude = String.valueOf(location.getLatitude());
                                longitude = String.valueOf(location.getLongitude());

                                // makeUseOfNewLocation(location);
                            }

                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            public void onProviderEnabled(String provider) {
                            }

                            public void onProviderDisabled(String provider) {
                            }
                        };
 //send http reqvest
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                        if (latitude != null & longitude != null) {
                            System.out.println("============" + latitude + "=======" + longitude);
                            //send http reqvest
                            new HttpRequestTask().execute();

                        }
                    }
                });
            }
        };
    }




    private class HttpRequestTask extends AsyncTask<Void, Void,String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                GpsLocation gpsLocation=new GpsLocation();
                gpsLocation.setLatitude(latitude);
                gpsLocation.setLongitude(longitude);
                gpsLocation.setOrderNo(orderid);
                gpsLocation.setMac(macAddress);
                final String url = "http://192.168.43.82:9764/RestaurantService-1.0.0/location/add";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
              //  restTemplate.postForLocation(url, gpsLocation);
                String a=restTemplate.postForObject(url,gpsLocation,String.class);

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {

        }

    }



}
