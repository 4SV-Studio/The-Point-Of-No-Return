package org.studio4sv.tponr.mechanics.stamina;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStaminaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerStamina> PLAYER_STAMINA = CapabilityManager.get(new CapabilityToken<PlayerStamina>() { });

    private PlayerStamina Stamina = null;
    private final LazyOptional<PlayerStamina> optional = LazyOptional.of(this::createPlayerStamina);

    private PlayerStamina createPlayerStamina() {
        if(this.Stamina == null) {
            this.Stamina = new PlayerStamina();
        }

        return this.Stamina;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_STAMINA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerStamina().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerStamina().loadNBTData(nbt);
    }
}