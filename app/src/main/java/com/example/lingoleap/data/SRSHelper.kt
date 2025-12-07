package com.example.lingoleap.data

/**
 * Enhanced Spaced Repetition System (SRS) helper.
 * Implements an improved SM-2 algorithm for optimal flashcard scheduling.
 */
object SRSHelper {
    private const val MIN_INTERVAL = 1
    private const val MAX_EASE = 2.6
    private const val MIN_EASE = 1.3
    private const val INITIAL_EASE = 2.5

    /**
     * Calculate next review interval based on correctness and current stats.
     * @param correct: whether the answer was correct
     * @param previousInterval: last interval in days
     * @param previousEase: easiness factor
     * @param repetitions: number of successful repetitions so far
     * @return Triple of (nextInterval, newEase, nextReviewTime)
     */
    fun calculateNextReview(
        correct: Boolean,
        previousInterval: Int = 1,
        previousEase: Double = INITIAL_EASE,
        repetitions: Int = 0
    ): Triple<Int, Double, Long> {
        val newEase = if (correct) {
            (previousEase + 0.1).coerceIn(MIN_EASE, MAX_EASE)
        } else {
            (previousEase - 0.2).coerceIn(MIN_EASE, MAX_EASE)
        }

        val nextInterval = if (correct) {
            when {
                repetitions == 0 -> 1
                repetitions == 1 -> 3
                else -> (previousInterval * newEase).toInt().coerceAtLeast(MIN_INTERVAL)
            }
        } else {
            1 // reset on wrong answer
        }

        val nextReviewTime = System.currentTimeMillis() + (nextInterval * 24L * 60L * 60L * 1000L)
        return Triple(nextInterval, newEase, nextReviewTime)
    }

    /**
     * Get difficulty rating (1-5) based on ease factor for user feedback.
     */
    fun getDifficultyRating(ease: Double): Int {
        return when {
            ease >= 2.4 -> 5 // very easy
            ease >= 2.2 -> 4 // easy
            ease >= 2.0 -> 3 // medium
            ease >= 1.6 -> 2 // hard
            else -> 1 // very hard
        }
    }

    /**
     * Get recommended review count per day based on target study time.
     */
    fun getRecommendedDailyReviews(studyMinutesPerDay: Int = 30): Int {
        return (studyMinutesPerDay / 2).coerceIn(5, 50) // avg 2 min per card
    }
}
