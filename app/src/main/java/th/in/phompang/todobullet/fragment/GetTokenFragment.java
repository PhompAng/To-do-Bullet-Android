package th.in.phompang.todobullet.fragment;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import th.in.phompang.todobullet.R;

public class GetTokenFragment extends Fragment {

    private static String access_token = "";
    private Activity mActivity;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GetTokenFragment.
     */
    public static GetTokenFragment newInstance() {
        GetTokenFragment fragment = new GetTokenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public GetTokenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_get_token, container, false);

        final WebView pushbullet = (WebView) v.findViewById(R.id.webView);
        WebSettings webSettings = pushbullet.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        pushbullet.addJavascriptInterface(new MyJavaScriptInterface(getContext()), "HtmlViewer");
        pushbullet.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("?code=")) {
                    pushbullet.loadUrl("javascript:window.HtmlViewer.showHTML" +
                            "(document.getElementsByTagName('body')[0].innerHTML);");
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flContent, new RegisterFragment().newInstance()).commit();
                }
            }
        });
        pushbullet.loadUrl("http://www.pushbullet.com/authorize?client_id=3w5GXegY7rSX812RYgt7E1AaKUXfw22m&redirect_uri=http%3A%2F%2F203.170.193.91&response_type=code&scope=everything");
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
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
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, new GetTokenFragment().newInstance()).commit();
            }

            SharedPreferences pref = mActivity.getSharedPreferences("token", 0);
            SharedPreferences.Editor editor = pref.edit();

            editor.putString("token", access_token);
            editor.apply();
        }
    }

}
