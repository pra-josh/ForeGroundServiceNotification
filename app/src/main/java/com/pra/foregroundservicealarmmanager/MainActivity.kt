package com.pra.foregroundservicealarmmanager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pra.foregroundservicealarmmanager.databinding.ActivityMainBinding
import com.pra.foregroundservicealarmmanager.service.BackGroundService

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        val restartServiceIntent = Intent(applicationContext, BackGroundService::class.java)
        mBinding?.btnStart?.setOnClickListener {
            // Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show()

            restartServiceIntent.`package` = packageName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(restartServiceIntent)
            } else {
                startService(restartServiceIntent)
            }
        }

        mBinding?.btnStop?.setOnClickListener {

            stopService(restartServiceIntent)
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show()
        }
    }
}