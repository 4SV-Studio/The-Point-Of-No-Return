package org.studio4sv.tponr.mechanics.attributes;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes {
    private final Map<String, Integer> attributes = new HashMap<>();

    public PlayerAttributes() {
        attributes.put("Level", 1);
        attributes.put("Health", 10);
        attributes.put("Stamina", 10);
        attributes.put("Strength", 10);
        attributes.put("Agility", 10);
        attributes.put("Intelligence", 10);
        attributes.put("Luck", 10);
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public int get(String key) {
        return attributes.getOrDefault(key, 10);
    }

    public void set(String key, int value) {
        attributes.put(key, value);
    }

    public void copyFrom(PlayerAttributes source) {
        for (String key : attributes.keySet()) {
            this.set(key, source.get(key));
        }
    }

    public void saveNBTData(CompoundTag nbt) {
        for (String key : attributes.keySet()) {
            nbt.putInt(key, attributes.get(key));
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (String key : attributes.keySet()) {
            if (nbt.contains(key)) {
                attributes.put(key, nbt.getInt(key));
            } else {
                if (!key.equals("Level")) {
                    attributes.put(key, 10);
                } else {
                    attributes.put(key, 1);
                }
            }
        }
    }
}