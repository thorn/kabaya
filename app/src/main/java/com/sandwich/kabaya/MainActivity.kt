package com.sandwich.kabaya

import android.Manifest
import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.sandwich.kabaya.data.local.PreferenceDataStoreConstants
import com.sandwich.kabaya.data.local.PreferenceDataStoreHelper
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPermissionToReceiveSMS()
//        requestScreeningRole()

        this.readAppPreferences()
        findViewById<Button>(R.id.savePreferences)
            .setOnClickListener {this.saveAppPreferences(this)}
    }

    private fun readAppPreferences() {
        lifecycleScope.launch {
            val preferenceDataStoreHelper = PreferenceDataStoreHelper(applicationContext)

            val tokenInput: EditText = findViewById(R.id.telegramBotTokenInput)
            val slot1Input: EditText = findViewById(R.id.slot_1_telegram_id_input)
            val slot2Input: EditText = findViewById(R.id.slot_2_telegram_id_input)
            val slot1NameInput: EditText = findViewById(R.id.slot_1_name_input)
            val slot2NameInput: EditText = findViewById(R.id.slot_2_name_input)

            val botToken: String = preferenceDataStoreHelper.getFirstPreference(PreferenceDataStoreConstants.TELEGRAM_BOT_TOKEN, "")
            val slot1Id: String = preferenceDataStoreHelper.getFirstPreference(PreferenceDataStoreConstants.TELEGRAM_SLOT1_ID, "")
            val slot2Id: String = preferenceDataStoreHelper.getFirstPreference(PreferenceDataStoreConstants.TELEGRAM_SLOT2_ID, "")
            val slot1Name: String = preferenceDataStoreHelper.getFirstPreference(PreferenceDataStoreConstants.TELEGRAM_SLOT1_NAME, "")
            val slot2Name: String = preferenceDataStoreHelper.getFirstPreference(PreferenceDataStoreConstants.TELEGRAM_SLOT2_NAME, "")
            tokenInput.setText(botToken)
            slot1Input.setText(slot1Id)
            slot2Input.setText(slot2Id)
            slot1NameInput.setText(slot1Name)
            slot2NameInput.setText(slot2Name)
        }
    }

    private fun saveAppPreferences(context: Context) {
        lifecycleScope.launch {
            val preferenceDataStoreHelper = PreferenceDataStoreHelper(applicationContext)

            val tokenInput: EditText = findViewById(R.id.telegramBotTokenInput)
            val slot1Input: EditText = findViewById(R.id.slot_1_telegram_id_input)
            val slot2Input: EditText = findViewById(R.id.slot_2_telegram_id_input)
            val slot1NameInput: TextView = findViewById(R.id.slot_1_name_input)
            val slot2NameInput: TextView = findViewById(R.id.slot_2_name_input)
            preferenceDataStoreHelper.putPreference(
                PreferenceDataStoreConstants.TELEGRAM_BOT_TOKEN,
                tokenInput.text.toString()
            )
            preferenceDataStoreHelper.putPreference(
                PreferenceDataStoreConstants.TELEGRAM_SLOT1_ID,
                slot1Input.text.toString()
            )
            preferenceDataStoreHelper.putPreference(
                PreferenceDataStoreConstants.TELEGRAM_SLOT2_ID,
                slot2Input.text.toString()
            )
            preferenceDataStoreHelper.putPreference(
                PreferenceDataStoreConstants.TELEGRAM_SLOT1_NAME,
                slot1NameInput.text.toString()
            )
            preferenceDataStoreHelper.putPreference(
                PreferenceDataStoreConstants.TELEGRAM_SLOT2_NAME,
                slot2NameInput.text.toString()
            )
            Toast.makeText(context, R.string.settings_saved_toast_text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getPermissionToReceiveSMS() {
        val permissionTextView = this.findViewById<TextView>(R.id.smsPermissionStatusLabel)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            permissionTextView.text = getString(R.string.sms_permission_granted_notice)
            return
        }

        if (shouldShowRequestPermissionRationale(Manifest.permission.RECEIVE_SMS)) {
            Toast.makeText(this, R.string.please_allow_sms_permissions_toast_text, Toast.LENGTH_SHORT).show()
        }
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                permissionTextView.text = getString(R.string.sms_permission_granted_notice)
            } else {
                permissionTextView.text = getString(R.string.please_grant_sms_permissions_notice)
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
    }

    private fun requestScreeningRole(){
        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
        val isHeld = roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
        val permissionTextView = this.findViewById<TextView>(R.id.callPermissionStatusLabel)
        if(isHeld){
            permissionTextView.text = getString(R.string.call_permission_granted_notice)
            return
        }

        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)

        // Create an ActivityResultLauncher
        val requestRoleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // The user has granted the role
                permissionTextView.text = getString(R.string.call_permission_granted_notice)
            } else {
                // The user has not granted the role
                permissionTextView.text = getString(R.string.please_grant_call_permissions_notice)
            }
        }

        // Launch the Activity
        requestRoleLauncher.launch(intent)
    }
}