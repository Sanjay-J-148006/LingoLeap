package com.example.lingoleap.data

/**
 * Achievement system for gamification and user engagement.
 * Tracks milestones and unlocks badges based on user progress.
 */
object AchievementSystem {
    data class Achievement(
        val id: String,
        val name: String,
        val description: String,
        val icon: String,
        val unlockCondition: (UserStats) -> Boolean
    )

    data class UserStats(
        val totalXp: Int = 0,
        val level: Int = 1,
        val lessonsCompleted: Int = 0,
        val quizzesCompleted: Int = 0,
        val correctAnswers: Int = 0,
        val totalAnswers: Int = 0,
        val daysActive: Int = 1,
        val streak: Int = 0
    )

    // Define all achievements
    val achievements = listOf(
        Achievement(
            id = "first_lesson",
            name = "First Steps",
            description = "Complete your first lesson",
            icon = "🚀",
            unlockCondition = { it.lessonsCompleted >= 1 }
        ),
        Achievement(
            id = "quiz_master_5",
            name = "Quiz Master (5)",
            description = "Complete 5 quizzes",
            icon = "📚",
            unlockCondition = { it.quizzesCompleted >= 5 }
        ),
        Achievement(
            id = "quiz_master_20",
            name = "Quiz Master (20)",
            description = "Complete 20 quizzes",
            icon = "📖",
            unlockCondition = { it.quizzesCompleted >= 20 }
        ),
        Achievement(
            id = "level_5",
            name = "Rising Star",
            description = "Reach level 5",
            icon = "⭐",
            unlockCondition = { it.level >= 5 }
        ),
        Achievement(
            id = "level_10",
            name = "Language Pro",
            description = "Reach level 10",
            icon = "🏆",
            unlockCondition = { it.level >= 10 }
        ),
        Achievement(
            id = "xp_100",
            name = "Century Club",
            description = "Earn 100 XP",
            icon = "💯",
            unlockCondition = { it.totalXp >= 100 }
        ),
        Achievement(
            id = "xp_500",
            name = "XP Legend",
            description = "Earn 500 XP",
            icon = "👑",
            unlockCondition = { it.totalXp >= 500 }
        ),
        Achievement(
            id = "accuracy_80",
            name = "Accuracy Expert",
            description = "Achieve 80% accuracy",
            icon = "🎯",
            unlockCondition = { it.totalAnswers > 0 && (it.correctAnswers * 100 / it.totalAnswers) >= 80 }
        ),
        Achievement(
            id = "streak_7",
            name = "Week Warrior",
            description = "Maintain 7-day streak",
            icon = "🔥",
            unlockCondition = { it.streak >= 7 }
        ),
        Achievement(
            id = "daily_learner",
            name = "Daily Learner",
            description = "Study 5 days in a row",
            icon = "📅",
            unlockCondition = { it.daysActive >= 5 }
        )
    )

    /**
     * Get list of unlocked achievements based on user stats.
     */
    fun getUnlockedAchievements(stats: UserStats): List<Achievement> {
        return achievements.filter { it.unlockCondition(stats) }
    }

    /**
     * Get achievement progress (0-100%) for display in UI.
     */
    fun getAchievementProgress(achievement: Achievement, stats: UserStats): Int {
        return when (achievement.id) {
            "quiz_master_5" -> (stats.quizzesCompleted * 100 / 5).coerceAtMost(100)
            "quiz_master_20" -> (stats.quizzesCompleted * 100 / 20).coerceAtMost(100)
            "level_5" -> (stats.level * 100 / 5).coerceAtMost(100)
            "level_10" -> (stats.level * 100 / 10).coerceAtMost(100)
            "xp_100" -> (stats.totalXp * 100 / 100).coerceAtMost(100)
            "xp_500" -> (stats.totalXp * 100 / 500).coerceAtMost(100)
            "accuracy_80" -> if (stats.totalAnswers > 0) (stats.correctAnswers * 100 / stats.totalAnswers).coerceAtMost(100) else 0
            "streak_7" -> (stats.streak * 100 / 7).coerceAtMost(100)
            "daily_learner" -> (stats.daysActive * 100 / 5).coerceAtMost(100)
            else -> if (achievement.unlockCondition(stats)) 100 else 0
        }
    }
}
