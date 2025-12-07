package com.example.lingoleap.data

// Lightweight user record used by DBHelper. Room removed in this build.
data class UserRecord(val email: String, val xp: Int = 0, val level: Int = 1)
