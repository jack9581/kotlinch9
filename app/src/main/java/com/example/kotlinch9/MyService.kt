package com.example.kotlinch9

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {
    companion object{
        var flag:Boolean=false
    }
    private var h=0
    private var m=0
    private var s=0

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        flag= intent.getBooleanExtra("flag",false)
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            run1();
        }
        return START_STICKY
    }
    private suspend fun run1(){
        while(flag) {
            try {
                delay(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            s++
            if (s >= 60) {
                s = 0
                m++
                if (m >= 60) {
                    m = 0
                    h++
                }
            }
            val intent = Intent("mymessage")
            val b = Bundle()
            b.putInt("H", h)
            b.putInt("M", m)
            b.putInt("S", s)
            intent.putExtras(b)
            sendBroadcast(intent)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}