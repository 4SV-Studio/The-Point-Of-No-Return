package org.studio4sv.tponr.client;

public class ClientStaminaData {
    private static float playerStamina;
    private static int maxStamina;

    public static void set(float stamina) {
        ClientStaminaData.playerStamina = stamina;
    }

    public static float get() {
        return playerStamina;
    }

    public static void setMax(int stamina) {
        ClientStaminaData.maxStamina = stamina;
    }

    public static int getMax() {
        return maxStamina;
    }
}
