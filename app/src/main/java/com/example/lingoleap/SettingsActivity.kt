package com.example.lingoleap

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val apiKeyInput = findViewById<TextInputEditText>(R.id.apiKeyInput)
        val saveBtn = findViewById<MaterialButton>(R.id.saveKeyBtn)

        // Setup EncryptedSharedPreferences with fallback for older devices
        val sharedPrefs = try {
            val masterKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            EncryptedSharedPreferences.create(
                applicationContext,
                "lingoleap_secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            // Fallback to regular SharedPreferences on older devices or if crypto fails
            getSharedPreferences("lingoleap_secure_prefs", MODE_PRIVATE)
        }

        // Pre-fill if present
        apiKeyInput?.setText(sharedPrefs.getString("ai_api_key", ""))

        // Assistant toggle: store in regular preferences for quick checks
        val toggle = findViewById<SwitchMaterial>(R.id.assistantToggle)
        val prefsSimple = getSharedPreferences("lingoleap_prefs", MODE_PRIVATE)
        toggle?.isChecked = prefsSimple.getBoolean("assistant_enabled", true)

        saveBtn.setOnClickListener {
            val key = apiKeyInput?.text?.toString() ?: ""
            sharedPrefs.edit().putString("ai_api_key", key).apply()
            // Save assistant enabled toggle to regular prefs
            prefsSimple.edit().putBoolean("assistant_enabled", toggle?.isChecked ?: true).apply()
            Toast.makeText(this, "Settings saved.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
