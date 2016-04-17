package com.example.vojjalateja.causelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

public class Main extends AppCompatActivity {

    private Context context;
    private WebView mWebView;
    private ImageView imageview;
    String ini="http://hc.tap.nic.in/Hcdbs/search.do",ga="general administration";
    public static String mainhtmlcontents="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview=(ImageView)findViewById(R.id.imageview);
        Ion.with(imageview).load("http://www.arborlanekennel.com/croogo/images/loading.gif");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context=this;
        ConnectionDetector connectionDetector=new ConnectionDetector(context);
        if(!connectionDetector.isConnectingToInternet())
        {
            Intent i=new Intent(context,load.class);
            i.putExtra("internetconnection",0);
            startActivity(i);
        }
        else {
            class MyJavaScriptInterface {
                @SuppressWarnings("unused")
                @JavascriptInterface
                public void showHTML(String html)
                {
                    mainhtmlcontents=html;
                    Intent i=new Intent(context,GetInfo.class);
                    i.putExtra("comstring1","GP FOR GENERAL ADMINISTRATION (TG)");
                    i.putExtra("comstring2","GP FOR GENERAL ADMINISTRATION");
                    i.putExtra("internetconnection",1);
                    startActivity(i);
                }
            }
            mWebView=(WebView)findViewById(R.id.webView);
            mWebView.setVisibility(View.INVISIBLE);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mWebView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            mWebView.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    mWebView.setWebViewClient(new WebViewClient() {
                                        @Override
                                        public void onPageFinished(WebView view, String url) {
                                            super.onPageFinished(view, url);
                                            mWebView.loadUrl("javascript:window.HTMLOUT.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                                        }
                                    });
                                    mWebView.loadUrl("javascript:{document.getElementsByTagName('input')[0].value='" + ga + "';" + "document.getElementsByTagName('input')[1].click();" + "}");
                                }
                            });
                            mWebView.loadUrl("javascript:{document.getElementsByTagName('input')[4].click();}");
                        }
                    });
                    mWebView.loadUrl("javascript:{document.getElementsByName('causelisttype')[0].click();}");
                }
            });
            mWebView.loadUrl(ini);
        }
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
    protected void onPause(){
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
