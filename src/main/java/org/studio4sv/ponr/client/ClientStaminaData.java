package org.studio4sv.ponr.client;

public class ClientStaminaData {
    private static int playerStamina;
    private static int playerMaxStamina = 50;

    private static final int staminaLimit = 250;

    public static void setPlayerStamina(int stamina) {
        ClientStaminaData.playerStamina = Math.min(stamina, playerMaxStamina);
    }

    public static int getPlayerStamina() {
        return playerStamina;
    }

    public static void setPlayerMaxStamina(int playerMaxStamina) {
        ClientStaminaData.playerMaxStamina = Math.min(playerMaxStamina, staminaLimit);
    }

    public static int getPlayerMaxStamina() {
        return playerMaxStamina;
    }
}
