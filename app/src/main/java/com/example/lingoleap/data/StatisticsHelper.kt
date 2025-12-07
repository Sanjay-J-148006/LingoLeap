package com.example.lingoleap.data

class StatisticsHelper {

    /**
     * Helper object to calculate user levels based on XP.
     */
    object StatisticsHelper {

        /**
         * Calculates the user's level based on their total XP.
         * Example logic: Level 1 is default. Every 100 XP gains a level.
         */
        fun calculateLevel(xp: Int): Int {
            if (xp < 0) return 1
            // Example: 0-99 XP = Lvl 1, 100-199 XP = Lvl 2
            return (xp / 100) + 1
        }

        object StatisticsHelper {

            /**
             * Calculates the user's level based on their total XP.
             * Example logic: Level = sqrt(XP / 100) + 1
             * Adjust this formula to match your game design.
             */
            fun calculateLevel(xp: Int): Int {
                if (xp <= 0) return 1
                // Simple progressive curve: 0-99xp = Lvl 1, 100-399xp = Lvl 2, etc.
                return (Math.sqrt(xp.toDouble() / 100.0)).toInt() + 1
            }

            /**
             * Calculates the total XP required to reach the *next* level.
             * This should be the inverse of the level formula.
             */
            fun xpForNextLevel(currentLevel: Int): Int {
                // Inverse of logic above: XP = 100 * (Level - 1)^2
                // To get to the *next* level, we calculate the threshold for (currentLevel)
                // effectively finding the ceiling of the current level.
                val targetLevel = currentLevel
                return 100 * targetLevel * targetLevel
            }
        }


        /**
         * Returns the total accumulated XP required to reach the next level.
         */
        fun xpForNextLevel(currentLevel: Int): Int {
            // Example: If current level is 1, next is 2. Level 2 requires 100 XP total.
            // If current level is 2, next is 3. Level 3 requires 200 XP total.
            return currentLevel * 100
        }
    }

}