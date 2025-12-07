package com.example.lingoleap.data

/**
 * Lightweight replacement for cloud sync.
 *
 * This object intentionally does not reference Firebase so the app
 * will run purely with local storage (SQLite). The previous behavior
 * attempted to sync XP and badges with Firestore; those calls are
 * now effectively no-ops because all important state is persisted
 * locally in `DBHelper`.
 */
object FirebaseSync {
    fun syncUserXp(email: String, xp: Int) {
        // No-op: persisted locally via DBHelper.addXp
    }

    fun addBadge(email: String, badgeName: String) {
        // No-op: badges are saved locally via DBHelper.addBadge
    }
}
