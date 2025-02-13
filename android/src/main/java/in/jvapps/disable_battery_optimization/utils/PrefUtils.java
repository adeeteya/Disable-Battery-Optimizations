package in.jvapps.disable_battery_optimization.utils;

import android.content.Context;
import android.util.Log;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.BooleanPreferencesKey;
import androidx.datastore.preferences.core.DoublePreferencesKey;
import androidx.datastore.preferences.core.FloatPreferencesKey;
import androidx.datastore.preferences.core.IntPreferencesKey;
import androidx.datastore.preferences.core.LongPreferencesKey;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.core.StringPreferencesKey;
import androidx.datastore.preferences.preferencesDataStore;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.catch;
import kotlinx.coroutines.flow.map;

import java.io.IOException;
import java.util.Objects;

public class PrefUtils {
    private static final String DATASTORE_NAME = "app_prefs";
    private static final Context.preferencesDataStore dataStore = new Context.preferencesDataStore(DATASTORE_NAME);

    public static void saveToPrefs(Context context, String key, Object value) {
        DataStore<Preferences> dataStore = context.getDataStore();
        Preferences.Key<Object> prefKey = getKey(key, value);
        
        context.getDataStore().edit(prefs -> {
            prefs.set(prefKey, value);
        });
    }

    public static Flow<Object> getFromPrefs(Context context, String key, Object defaultValue) {
        DataStore<Preferences> dataStore = context.getDataStore();
        Preferences.Key<Object> prefKey = getKey(key, defaultValue);

        return dataStore.data
            .catch(exception -> {
                if (exception instanceof IOException) {
                    Log.e("PrefUtils", Objects.requireNonNull(exception.getMessage()));
                    emit(emptyPreferences());
                } else {
                    throw exception;
                }
            })
            .map(prefs -> prefs.getOrDefault(prefKey, defaultValue));
    }

    public static void removeFromPrefs(Context context, String key) {
        DataStore<Preferences> dataStore = context.getDataStore();
        
        dataStore.edit(prefs -> {
            prefs.remove(PreferencesKeys.stringKey(key));
        });
    }

    private static Preferences.Key<Object> getKey(String key, Object value) {
        if (value instanceof Integer) {
            return (Preferences.Key<Object>) IntPreferencesKey(key);
        } else if (value instanceof String) {
            return (Preferences.Key<Object>) StringPreferencesKey(key);
        } else if (value instanceof Boolean) {
            return (Preferences.Key<Object>) BooleanPreferencesKey(key);
        } else if (value instanceof Long) {
            return (Preferences.Key<Object>) LongPreferencesKey(key);
        } else if (value instanceof Float) {
            return (Preferences.Key<Object>) FloatPreferencesKey(key);
        } else if (value instanceof Double) {
            return (Preferences.Key<Object>) DoublePreferencesKey(key);
        }
        throw new IllegalArgumentException("Unsupported data type");
    }
}
