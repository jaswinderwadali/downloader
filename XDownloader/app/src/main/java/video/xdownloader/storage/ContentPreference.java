package video.xdownloader.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jaswinderwadali on 11/09/17.
 */
public class ContentPreference {

    private SharedPreferences sharedPreferences;
    private static ContentPreference contentPreference;


    public static synchronized ContentPreference init(Application application) {
        if (contentPreference == null)
            contentPreference = new ContentPreference(application);
        return contentPreference;
    }


    public static synchronized ContentPreference getInstance() {
        if (contentPreference == null)
            throw new ExceptionInInitializerError("Please init with application context");
        return contentPreference;
    }

    public static synchronized ContentPreference getInstance(Context context) {
        if (contentPreference == null)
            contentPreference = new ContentPreference(context.getApplicationContext());
        return contentPreference;
    }

    private ContentPreference(Context context) {
        String mainPreference = "MainPreference";
        sharedPreferences = context.getApplicationContext().getSharedPreferences(mainPreference, Context.MODE_PRIVATE);
    }


    public boolean contains(PreferenceKey key) {
        return sharedPreferences.contains(key.toString());
    }

    public void putString(PreferenceKey key, String value) {
        sharedPreferences.edit().putString(key.toString(), value).apply();
    }

    public void putSet(PreferenceKey key, Set<String> value) {
        sharedPreferences.edit().putStringSet(key.toString(), value).apply();
    }


    public String getString(PreferenceKey key) {
        return sharedPreferences.getString(key.toString(), null);
    }

    public void putLong(PreferenceKey key, Long value) {
        sharedPreferences.edit().putLong(key.toString(), value).apply();
    }

    public long getLong(PreferenceKey key) {
        return sharedPreferences.getLong(key.toString(), 0);
    }

    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

    public void putBool(PreferenceKey key, boolean value) {
        sharedPreferences.edit().putBoolean(key.toString(), value).apply();

    }

    public boolean getBool(PreferenceKey key) {
        return sharedPreferences.getBoolean(key.toString(), false);
    }

    public Set<String> getStringSet(PreferenceKey key) {
        return sharedPreferences.getStringSet(key.toString(), new HashSet<String>());
    }


    public enum PreferenceKey {
        TOKEN("token"),
        CHAT_TOKEN("chat_token"),
        MAP_QUERY("map_query"),
        //QUE_REQUEST("QUE_REQUEST"),
        USER_NAME("USER_NAME"),
        FIRE_BASE__TOKEN("FIRE_BASE__TOKEN"),
        FB_ID("FB_ID"),
        GOOGLE_FIT_CLIENT("GOOGLE_FIT_CLIENT"),
        //INVITE("INVITE"),
        USER_OBJECT("USER_OBJECT"),
        RATE_US("RATE_US"),
        STOP_RATE_US("STOP_RATE_US"),
        CACHE_CHAT("CACHE_CHAT"),
        CACHE_HISTORY_CHAT("CACHE_HISTORY_CHAT"),
        RATE_TIME_OPEN("RATE_TIME_OPEN"),
        LOCATION_VARIABLE("LOCATION_VARIABLE"),
        PIN("PIN"),
        ON_BOARDING_DONE("ON_BOARDING_DONE");


        String key;

        PreferenceKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key;
        }

    }

}
