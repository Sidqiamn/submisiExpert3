package com.example.submisiawal2.activity

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.submisiawal2.R
import com.example.submisiawal2.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val currentMode = AppCompatDelegate.getDefaultNightMode()

        binding.switchTheme.isChecked = currentMode == AppCompatDelegate.MODE_NIGHT_YES


        updateBackIcon(currentMode)

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                updateBackIcon(AppCompatDelegate.MODE_NIGHT_YES)
            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                updateBackIcon(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        binding.backIcon.setOnClickListener {
            finish()
        }
    }


    private fun updateBackIcon(mode: Int) {
        val drawableRes = if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            R.drawable.baseline_keyboard_backspace_24_white
        } else {
            R.drawable.baseline_keyboard_backspace_24
        }
        binding.backIcon.setImageDrawable(ContextCompat.getDrawable(this, drawableRes))
    }
}
