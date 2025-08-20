package org.studio4sv.tponr.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.studio4sv.tponr.TPONR;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SEAL_BLOCKS = TagKey.create(
            Registries.BLOCK, TPONR.id("seal_blocks")
        );
    }
}
