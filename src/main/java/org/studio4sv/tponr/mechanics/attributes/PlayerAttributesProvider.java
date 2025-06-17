package org.studio4sv.tponr.mechanics.attributes;

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

public class PlayerAttributesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerAttributes> PLAYER_ATTRIBUTES = CapabilityManager.get(new CapabilityToken<PlayerAttributes>() { });

    private PlayerAttributes Attributes = null;
    private final LazyOptional<PlayerAttributes> optional = LazyOptional.of(this::createPlayerAttributes);

    private PlayerAttributes createPlayerAttributes() {
        if(this.Attributes == null) {
            this.Attributes = new PlayerAttributes();
        }

        return this.Attributes;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_ATTRIBUTES) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAttributes().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAttributes().loadNBTData(nbt);
    }
}