package com.example.globochatpluralsight

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val dataStore = DataStore()
        //Enable preferenceDataStore for the entire hierarchy and disable sharedPreferences

        preferenceManager.preferenceDataStore = dataStore
        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))

        accSettingsPref?.setOnPreferenceClickListener {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)
            true
        }

        val sharedPreferences = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val autoReplyTime =
            sharedPreferences?.getString(getString(R.string.key_auto_reply_time), "")
        Log.i("SettingsFragment", "Auto Reply Time: $autoReplyTime")

        val autoDownload = sharedPreferences?.getBoolean("key_auto_download", false)
        Log.i("SettingsFragment", "Auto Download: $autoDownload")

        val statusPref = findPreference<EditTextPreference>("key_status")

        //This method is called before the change of the value of the preference(setOnPreferenceChangeListener)
        statusPref?.setOnPreferenceChangeListener { _, newValue ->

            val newStatus = newValue as String
            if (newStatus.contains("bad")) {
                Toast.makeText(activity, "Inappropriate Status. Please maintain community guidelines.",
                    Toast.LENGTH_SHORT).show()

                false
            } else {
                true
            }

        }

        val notificationPref = findPreference<SwitchPreferenceCompat>(getString(R.string.key_new_msg_notif))
        notificationPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> { switchPref: SwitchPreferenceCompat ->

            if (switchPref.isChecked)
                "Status: ON"
            else
                "Status: OFF"
        }
        notificationPref?.preferenceDataStore = dataStore
    }

    inner class DataStore : PreferenceDataStore() {

        override fun getBoolean(key: String?, defValue: Boolean): Boolean {
            if(key == "key_new_msg_notif") {
                Log.i("DataStore", "getBoolean executed for $key")
            }

            return defValue
        }

        override fun putBoolean(key: String?, value: Boolean) {
            if(key == "key_new_msg_notif") {
                Log.i("DataStore", "PutBoolean executed for $key with new value: $value")
            }
        }

    }

}