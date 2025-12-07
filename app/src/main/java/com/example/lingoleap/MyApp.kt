package com.example.lingoleap

import android.app.Application
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val ts = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
                val file = File(filesDir, "crash_$ts.txt")
                val sw = StringWriter()
                val pw = PrintWriter(sw)
                throwable.printStackTrace(pw)
                val content = "Timestamp: $ts\nThread: ${thread.name}\nException: ${throwable::class.java.name}\n\n${sw.toString()}"
                file.writeText(content)
                Log.e("MyAppCrash", "Wrote crash log to ${file.absolutePath}")
            } catch (e: Exception) {
                // ignore secondary failures
            }
            // Let the system handle the crash after writing file
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(2)
        }
    }
}
