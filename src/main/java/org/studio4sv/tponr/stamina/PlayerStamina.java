package org.studio4sv.tponr.stamina;

import net.minecraft.nbt.CompoundTag;

public class PlayerStamina {
    private int stamina;
    private int maxStamina;

    public PlayerStamina() {
        this.stamina = 150;
        this.maxStamina = 150;
    }

    public void copyFrom(PlayerStamina source) {
        this.stamina = source.stamina;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = Math.min(maxStamina, 500);

        if (this.stamina > this.maxStamina) {
            this.stamina = this.maxStamina;
        }
    }

    public void addStamina(int amount) {
        this.stamina = Math.min(stamina + amount, maxStamina);
    }

    public void subStamina(int amount) {
        this.stamina = Math.max(stamina - amount, 0);
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("stamina", stamina);
        nbt.putInt("maxStamina", maxStamina);
    }

    public void loadNBTData(CompoundTag nbt) {
        stamina = nbt.getInt("stamina");
        maxStamina = nbt.getInt("maxStamina");
    }
}