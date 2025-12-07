package com.example.lingoleap.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "LingoLeap.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_USERS = "users"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password"
        private const val COL_XP = "xp"

        @Volatile
        private var instance: DBHelper? = null

        // Singleton Pattern: Ensures only one database instance exists
        fun getInstance(context: Context): DBHelper {
            return instance ?: synchronized(this) {
                instance ?: DBHelper(context.applicationContext).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = "CREATE TABLE $TABLE_USERS (" +
                "$COL_EMAIL TEXT PRIMARY KEY," +
                "$COL_PASSWORD TEXT," +
                "$COL_XP INTEGER DEFAULT 0)"
        db.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun registerUser(email: String, pass: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_EMAIL, email)
            put(COL_PASSWORD, pass)
            put(COL_XP, 0)
        }
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

    fun checkUser(email: String, pass: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USERS WHERE $COL_EMAIL = ? AND $COL_PASSWORD = ?",
            arrayOf(email, pass)
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun addXp(email: String, xpToAdd: Int) {
        val db = this.writableDatabase
        try {
            // Ensure table name and columns match exactly what is in onCreate
            // "users", "xp", "email"
            val sql = "UPDATE users SET xp = xp + ? WHERE email = ?"
            val args = arrayOf(xpToAdd, email)
            db.execSQL(sql, args)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // No need to close db here if using Singleton, but typically safe to leave open
    }


    // ... existing code ...

    /**
     * Retrieves the raw integer XP value for calculations.
     */
    fun getUserXp(email: String): Int {
        val db = this.readableDatabase
        var xp = 0
        val cursor = db.rawQuery("SELECT $COL_XP FROM $TABLE_USERS WHERE $COL_EMAIL = ?", arrayOf(email))

        if (cursor.moveToFirst()) {
            xp = cursor.getInt(0)
        }
        cursor.close()
        return xp
    }


    fun getUserStats(email: String): String {
        val db = this.readableDatabase
        var stats = "XP: 0"
        val cursor = db.rawQuery("SELECT $COL_XP FROM $TABLE_USERS WHERE $COL_EMAIL = ?", arrayOf(email))

        if (cursor.moveToFirst()) {
            val xp = cursor.getInt(0)
            // Professional Level Calculation: Level increases every 100 XP
            val level = (xp / 100) + 1
            stats = "Level: $level\nTotal XP: $xp"
        }
        cursor.close()
        return stats
    }
}


