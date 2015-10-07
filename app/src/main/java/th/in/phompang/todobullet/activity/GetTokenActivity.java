package th.in.phompang.todobullet.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import th.in.phompang.todobullet.R;

public class GetTokenActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static String access_token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettoken);
        final WebView pushbullet = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = pushbullet.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        pushbullet.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");
        pushbullet.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("?code=")) {
                    pushbullet.loadUrl("javascript:window.HtmlViewer.showHTML" +
                            "(document.getElementsByTagName('body')[0].innerHTML);");
                    Intent i = new Intent(GetTokenActivity.this, RegisterActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        pushbullet.loadUrl("http://www.pushbullet.com/authorize?client_id=3w5GXegY7rSX812RYgt7E1AaKUXfw22m&redirect_uri=http%3A%2F%2F203.170.193.91&response_type=code&scope=everything");
    }

    public String getAccess_token() {
        return access_token;
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            try {
                JSONObject obj = new JSONObject(html);
                access_token = obj.getString("access_token");
                access_token = access_token.split("\\.")[1];
            } catch (JSONException e) {
                access_token = "null";
            }

            if (access_token.equals("null")) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            SharedPreferences pref = getSharedPreferences("token", 0);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("token", access_token);
            editor.commit();
        }
    }
}
