package com.example.lingoleap

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object DebugUtil {
    fun writeStatus(ctx: Context, tag: String) {
        try {
            val ts = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(Date())
            val f = File(ctx.filesDir, "status_${tag}_${ts}.txt")
            f.writeText("$tag at $ts")
        } catch (_: Exception) {
        }
    }
}
