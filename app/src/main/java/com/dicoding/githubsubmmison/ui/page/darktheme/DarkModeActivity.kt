package com.dicoding.githubsubmmison.ui.page.darktheme

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubsubmmison.R
import com.dicoding.githubsubmmison.databinding.ActivityDarkModeBinding
import com.dicoding.githubsubmmison.databinding.ActivityMainBinding
import com.dicoding.githubsubmmison.ui.view_model_factory.SettViewModelFactory
import com.dicoding.githubsubmmison.ui.viewmodel.SettingViewModel
import com.dicoding.githubsubmmison.utils.SettingPreferences
import com.dicoding.githubsubmmison.utils.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class DarkModeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val setViewModel = ViewModelProvider(this, SettViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        setViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }


        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            setViewModel.saveThemeSetting(isChecked)
        }

    }
}