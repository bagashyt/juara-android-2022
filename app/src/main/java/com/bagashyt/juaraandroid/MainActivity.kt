package com.bagashyt.juaraandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bagashyt.juaraandroid.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.calculateButton.setOnClickListener{calculateTip()}
    }

    private fun calculateTip(){
        val stringInTextField = binding.costOfService.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        val tipPercentage = when (binding.tipOption.checkedRadioButtonId){
            R.id.option_20_percent -> 0.20
            R.id.option_18_percent -> 0.18
            else -> 0.15
        }

        if(cost == null || cost == 0.0){
             displayTip(0.0)
            return
        }

        var tip = cost * tipPercentage
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp){
           tip = ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip: Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}