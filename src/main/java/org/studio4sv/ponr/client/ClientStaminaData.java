package org.studio4sv.ponr.client;

public class ClientStaminaData {
    private static int playerStamina;
    private static int playerMaxStamina = 50;

    public static void setPlayerStamina(int stamina) {
        ClientStaminaData.playerStamina = Math.min(stamina, playerMaxStamina);
    }

    public static int getPlayerStamina() {
        return playerStamina;
    }

    public static void setPlayerMaxStamina(int playerMaxStamina) {
        ClientStaminaData.playerMaxStamina = playerMaxStamina;
    }

    public static int getPlayerMaxStamina() {
        return playerMaxStamina;
    }
}
