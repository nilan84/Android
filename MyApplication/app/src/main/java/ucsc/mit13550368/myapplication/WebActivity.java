package ucsc.mit13550368.myapplication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.regex.Pattern;


public class WebActivity extends ActionBarActivity {

    private String possibleEmail;
    private String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Button backButton=(Button)findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // find the WebView by name in the main.xml of step 2
        WebView browser = (WebView) findViewById(R.id.webview);
        browser=(WebView)findViewById(R.id.webview);
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        macAddress= wInfo.getMacAddress();
        possibleEmail="";
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;

            }
        }

        String[] customerName=possibleEmail.split("@");
        // Enable javascript
        browser.getSettings().setJavaScriptEnabled(true);

        // Set WebView client
        browser.setWebChromeClient(new WebChromeClient());

        browser.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // Load the webpage
        browser.loadUrl("http://192.168.43.82:9764/RestaurantService-1.0.0/apphome?cusname="+customerName[0]+"&mac="+macAddress);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
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
}
