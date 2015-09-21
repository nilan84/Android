package ucsc.mit13550368.myapplication;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Pattern;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt,discription,price,name;
    private Button urlButton;
    private Button selectButton;
    private String barCord;
    private String scanContent;
    private String macAddress;
    private String possibleEmail;

    private Long orderNo=0L;
    private int foodNo=0;
    private int customerId=0;
    private double unitPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        urlButton=(Button)findViewById(R.id.buttonUrl);
        selectButton=(Button)findViewById(R.id.buttonselect);
        discription = (TextView) findViewById(R.id.textDiscription);
        price = (TextView) findViewById(R.id.textPrice);
        name = (TextView) findViewById(R.id.textView);
        selectButton.setEnabled(false);

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress= wInfo.getMacAddress();

       // formatTxt.setText("Mob No: " + macAddress);
        possibleEmail="";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;

            }
        }
       // formatTxt.setText("Email: " + possibleEmail);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                // Called when a new location is found by the network location provider.
               // formatTxt.setText(String.valueOf(location.getLatitude()));
               // contentTxt.setText(String.valueOf(location.getLongitude()));

                // makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatTxt.setText("");
                Intent intent = new Intent(getApplicationContext(),WebActivity.class);
                startActivity(intent);
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new HttpRequestForAddOrder().execute();
                selectButton.setEnabled(false);
                formatTxt.setText("Successfully added your Order !");
            }
        });

        scanBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
           // formatTxt.setText("FORMAT: " + scanFormat);
            //contentTxt.setText("CONTENT: " + scanContent);
            String[] readInfo=scanContent.split(":");
            barCord=readInfo[0];
            new HttpRequestTask().execute();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, FoodItem> {
        @Override
        protected FoodItem doInBackground(Void... params) {
            try {
                final String url = "http://192.168.43.82:9764/RestaurantService-1.0.0/view/foodinfo/"+barCord;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                FoodItem foodItem = restTemplate.getForObject(url, FoodItem.class);

                return foodItem;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(FoodItem foodItem) {
            setValue(foodItem);
        }

    }

    private class HttpRequestForAddOrder extends AsyncTask<Void, Void,Long> {
        @Override
        protected Long doInBackground(Void... params) {
            try {
                Customer customer=new Customer();
                customer.setMacAddress(macAddress);
                customer.setCustomerEmail(possibleEmail);
                String []CustomerName=possibleEmail.split("@");
                customer.setCustomerName(CustomerName[0]);
                customer.setCustomerMob(macAddress);
                OrderFood orderFood=new OrderFood();
                orderFood.setCustomer(customer);
                orderFood.setOrderNo(orderNo);
                orderFood.setFoodId(foodNo);
                orderFood.setUnitPrice(unitPrice);
                orderFood.setNoOfitem(1);
                orderFood.setDescription("Mobile User "+CustomerName[0]);
                final String url = "http://192.168.43.82:9764/RestaurantService-1.0.0/food/addorder";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                orderNo=restTemplate.postForObject(url, orderFood, Long.class);

            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return orderNo;
        }

        @Override
        protected void onPostExecute(Long result) {

        }

    }


    public void setValue(FoodItem foodItem){
        formatTxt.setText("");
        discription.setText("Description : " + foodItem.getDiscription());
        price.setText("Food Price : "+String.valueOf(foodItem.getPrice()));
        name.setText("Food Name : "+foodItem.getFoodItemName());
        foodNo=foodItem.getFoodNo();
        unitPrice=foodItem.getPrice();
        selectButton.setEnabled(true);

    }




}

