package com.example.clock2.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*

class TimerService : Service()
{
    // Cung cấp 1 giao diện để tương tác service ko ràng buộc thì để là null
    override fun onBind(p0: Intent?): IBinder? = null

    private val timer = Timer()

    // bắt buộc phải override lại hàm hàm chạy trong background vô thời hạn
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        Log.d("string", intent.getDoubleExtra(TIME_EXTRA, 0.0).toString())
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy()
    {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask()
    {
        override fun run()
        {
            val intent = Intent(TIMER_UPDATED)
            time++
            Log.d("Time",time.toString())
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    companion object
    {
        const val TIMER_UPDATED = "timerUpdated"
        const val TIME_EXTRA = "timeExtra"
    }

}