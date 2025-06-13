package org.studio4sv.ponr.client;

public class ClientStaminaData {
    private static int playerStamina;
    private static int maxStamina;

    public static void set(int stamina) {
        ClientStaminaData.playerStamina = stamina;
    }

    public static int get() {
        return playerStamina;
    }

    public static void setMax(int stamina) {
        ClientStaminaData.maxStamina = stamina;
    }

    public static int getMax() {
        return maxStamina;
    }
}
