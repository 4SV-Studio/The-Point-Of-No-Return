package org.studio4sv.tponr.mechanics.stamina;

import net.minecraft.nbt.CompoundTag;

public class PlayerStamina {
    private int stamina;
    private int maxStamina;
    private boolean staminaEnabled;
    private static final int limit = 1000;

    public static int getLimit() {
        return limit;
    }

    public PlayerStamina() {
        this.stamina = 300;
        this.maxStamina = 300;
        this.staminaEnabled = true;
    }

    public void copyFrom(PlayerStamina source) {
        this.stamina = source.stamina;
    }

    public void toggleStamina() {
        this.staminaEnabled = !this.staminaEnabled;
    }

    public boolean isEnabled() {
        return this.staminaEnabled;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = Math.min(maxStamina, limit);

        if (this.stamina > this.maxStamina) {
            this.stamina = this.maxStamina;
        }
    }

    public void addStamina(int amount) {
        if (staminaEnabled) this.stamina = Math.min(stamina + amount, maxStamina);
    }

    public void subStamina(int amount) {
        if (staminaEnabled) this.stamina = Math.max(stamina - amount, 0);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", stamina);
        nbt.putInt("maxStamina", maxStamina);
        nbt.putBoolean("staminaEnabled", staminaEnabled);
    }

    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getInt("stamina");
        maxStamina = nbt.getInt("maxStamina");
        staminaEnabled = nbt.getBoolean("staminaEnabled");
    }
}