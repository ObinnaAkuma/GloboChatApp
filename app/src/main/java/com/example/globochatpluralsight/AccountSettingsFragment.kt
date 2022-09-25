package com.example.globochatpluralsight

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat


class AccountSettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // define all preference objects
        val publicInfoPref = MultiSelectListPreference(requireContext())
        publicInfoPref.entries = resources.getStringArray(R.array.entries_public_info)
        publicInfoPref.entryValues = resources.getStringArray(R.array.values_public_info)
        publicInfoPref.key = resources.getString(R.string.key_public_info)
        publicInfoPref.title = resources.getString(R.string.title_public_info)
        publicInfoPref.isIconSpaceReserved = false

        val logOutPref = Preference(requireContext())
        logOutPref.key = resources.getString(R.string.key_log_out)
        logOutPref.title = resources.getString(R.string.title_log_out)
        logOutPref.isIconSpaceReserved = false

        val deleteAccPref = Preference(requireContext())
        deleteAccPref.key = resources.getString(R.string.key_delete_account)
        deleteAccPref.summary = resources.getString(R.string.summary_delete_account)
        deleteAccPref.title = resources.getString(R.string.title_delete_account)
        deleteAccPref.icon = ResourcesCompat
            .getDrawable(resources, android.R.drawable.ic_input_delete,null)

        val privacyCategory = PreferenceCategory(requireContext())
        privacyCategory.title = "Privacy"
        privacyCategory.isIconSpaceReserved = false

        val securityCategory = PreferenceCategory(requireContext())
        securityCategory.title = "Security"
        securityCategory.isIconSpaceReserved = false

        val prefScreen = preferenceManager.createPreferenceScreen(requireContext())

        // Add different Categories
        prefScreen.addPreference(privacyCategory)
        prefScreen.addPreference(securityCategory)

        // Add preferences to the categories
        privacyCategory.addPreference(publicInfoPref)

        securityCategory.addPreference(logOutPref)
        securityCategory.addPreference(deleteAccPref)

        preferenceScreen = prefScreen

    }
}