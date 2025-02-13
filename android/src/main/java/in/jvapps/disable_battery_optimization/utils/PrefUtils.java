package in.jvapps.disable_battery_optimization.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class PrefUtils {

    private static final String PREF_NAME = "MyPrefs";

    public static void saveToPrefs(Context context, String key, Object value) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences prefs = contextWeakReference.get().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            
            if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Double) {
                editor.putLong(key, Double.doubleToRawLongBits((double) value));
            }
            editor.apply();
        }
    }

    public static Object getFromPrefs(Context context, String key, Object defaultValue) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences sharedPrefs = contextWeakReference.get().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            try {
                if (defaultValue instanceof String) {
                    return sharedPrefs.getString(key, (String) defaultValue);
                } else if (defaultValue instanceof Integer) {
                    return sharedPrefs.getInt(key, (Integer) defaultValue);
                } else if (defaultValue instanceof Boolean) {
                    return sharedPrefs.getBoolean(key, (Boolean) defaultValue);
                } else if (defaultValue instanceof Long) {
                    return sharedPrefs.getLong(key, (Long) defaultValue);
                } else if (defaultValue instanceof Float) {
                    return sharedPrefs.getFloat(key, (Float) defaultValue);
                } else if (defaultValue instanceof Double) {
                    return Double.longBitsToDouble(sharedPrefs.getLong(key, Double.doubleToLongBits((double) defaultValue)));
                }
            } catch (Exception e) {
                Log.e("Exception", Objects.requireNonNull(e.getMessage()));
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static void removeFromPrefs(Context context, String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences prefs = contextWeakReference.get().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    @SuppressWarnings("unused")
    public static boolean hasKey(Context context, String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        if (contextWeakReference.get() != null) {
            SharedPreferences prefs = contextWeakReference.get().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            return prefs.contains(key);
        }
        return false;
    }
}
