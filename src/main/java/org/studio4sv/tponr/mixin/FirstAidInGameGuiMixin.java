package org.studio4sv.tponr.mixin;

import ichttt.mods.firstaid.client.gui.FirstaidIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FirstaidIngameGui.class)
public class FirstAidInGameGuiMixin {
    @ModifyConstant(
            method = "renderHealth",
            constant = @Constant(intValue = 144),
            remap = false
    )
    private static int modifyUOffset(int u) {
        return 160;
    }
}
