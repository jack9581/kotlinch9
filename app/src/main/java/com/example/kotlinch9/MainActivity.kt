package com.example.kotlinch9

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private  var flag =false;
    private val mbroadcast=object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val b = intent?.extras
            textView.setText(String.format("%02d:%02d:%02d",b?.getInt("H"),b?.getInt("M"),b?.getInt("S")))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(mbroadcast, IntentFilter("mymessage"))
        flag=MyService.flag
        button.text=if(flag) "暫停" else "開始"
        button.setOnClickListener {
            flag=!flag
            button.text=if(flag) "暫停" else "開始"
            startService(Intent(this,MyService::class.java).putExtra("flag",flag))
            Toast.makeText(this, if(flag)"計時開始" else "計時暫停", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mbroadcast)
    }
}