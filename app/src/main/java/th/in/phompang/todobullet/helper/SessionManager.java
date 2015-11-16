package th.in.phompang.todobullet.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by พิชัย on 5/10/2558.
 */
public class SessionManager {

    SharedPreferences pref;

    Editor editor;

    Context _contex;

    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "Login";

    public static final String KEY_IS_LOGGIN = "isLogin";

    public SessionManager(Context context) {
        this._contex = context;
        pref = _contex.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGGIN, isLogin);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(KEY_IS_LOGGIN, false);
    }

}
