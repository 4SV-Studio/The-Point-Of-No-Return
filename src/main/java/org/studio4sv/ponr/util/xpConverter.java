package org.studio4sv.ponr.util;

public class xpConverter {
    public static int calculate(Integer level) {
        int xpForLevel;
        if (level <= 15) { // 0-15
            xpForLevel = 17 * level;
        } else if (level <= 30) { // 16-30
            xpForLevel = (int) (1.5 * level * level - 29.5 * level + 350);
        } else { // 30+
            xpForLevel = (int) (3.5 * level * level - 151.5 * level + 2220);
        }
        return xpForLevel ;
    }

    public static int calculate(int level, float experienceProgress) {
        int xpForLevel = 0;
        if (level <= 15) {
            xpForLevel = 17 * level;
        } else if (level <= 30) {
            xpForLevel = (int) (1.5 * level * level - 29.5 * level + 360);
        } else {
            xpForLevel = (int) (3.5 * level * level - 151.5 * level + 2220);
        }
        int nextLevelXP = level <= 15 ? 17 : (level <= 30 ? (int)(3 * level - 28) : (int)(7 * level - 155));
        return xpForLevel + (int)(experienceProgress * nextLevelXP);
    }
}
