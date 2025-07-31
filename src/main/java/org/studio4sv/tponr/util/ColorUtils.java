package org.studio4sv.tponr.util;

public class ColorUtils {
    public static int rgbToHex(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    public static int[] hexToRgb(int hex) {
        int r = (hex >> 16) & 0xFF;
        int g = (hex >> 8) & 0xFF;
        int b = hex & 0xFF;
        return new int[] { r, g, b };
    }

}
