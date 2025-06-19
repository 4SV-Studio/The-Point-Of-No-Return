package org.studio4sv.tponr.mechanics.attributes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.studio4sv.tponr.TPONR;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = TPONR.MOD_ID)
public class IntelligenceHandler {

    private static final Map<Integer, Integer> TIER_REQUIREMENTS = new HashMap<>();
    private static final Map<Integer, Integer> AGILITY_REQUIREMENTS = new HashMap<>();
    static int[] agilityRequirement = new int[1];

    static {
        TIER_REQUIREMENTS.put(0, 5);  // Wood/Gold
        TIER_REQUIREMENTS.put(1, 10);  // Stone
        TIER_REQUIREMENTS.put(2, 15); // Iron
        TIER_REQUIREMENTS.put(3, 25); // Diamond
        TIER_REQUIREMENTS.put(4, 35); // Netherite

        AGILITY_REQUIREMENTS.put(0, 5);
        AGILITY_REQUIREMENTS.put(1, 10);
        AGILITY_REQUIREMENTS.put(2, 15);
        AGILITY_REQUIREMENTS.put(3, 25);
        AGILITY_REQUIREMENTS.put(4, 35);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        checkToolUsage(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemUseBlock(PlayerInteractEvent.RightClickBlock event) {
        checkToolUsage(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onItemLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        checkToolUsage(event);
    }

    public static int calculate(ItemStack itemStack) {
        Item item = itemStack.getItem();

        if (itemStack.isEmpty()) {
            return 0;
        }

        int requiredIntelligence;
        int enchantmentBonus = 0;

        if (!itemStack.getEnchantmentTags().isEmpty()) {
            enchantmentBonus = EnchantmentHelper.getEnchantments(itemStack).size() * 2;
        }

        if (item instanceof TieredItem tieredItem) {
            final int tier = tieredItem.getTier().getLevel();
            final int baseRequirement = TIER_REQUIREMENTS.getOrDefault(tier, 25);
            agilityRequirement[0] = AGILITY_REQUIREMENTS.getOrDefault(tier, 15);
            int additionalRequirement = 0;

            if (item instanceof SwordItem) {
                double attackDamage = 0;
                double attackSpeed = 0;

                Collection<AttributeModifier> damageModifiers = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(Attributes.ATTACK_DAMAGE);
                for (AttributeModifier modifier : damageModifiers) {
                    attackDamage += modifier.getAmount();
                }

                Collection<AttributeModifier> speedModifiers = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(Attributes.ATTACK_SPEED);
                for (AttributeModifier modifier : speedModifiers) {
                    attackSpeed += modifier.getAmount();
                }

                additionalRequirement = (int) Math.ceil(attackDamage * 0.5 + attackSpeed * 0.75);

            } else if (item instanceof AxeItem) {
                double attackDamage = 0;

                Collection<AttributeModifier> damageModifiers = itemStack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(Attributes.ATTACK_DAMAGE);
                for (AttributeModifier modifier : damageModifiers) {
                    attackDamage += modifier.getAmount();
                }

                additionalRequirement = (int) Math.ceil(attackDamage * 0.75);

            } else if (item instanceof PickaxeItem) {
                additionalRequirement = 5;

            } else if (item instanceof ShovelItem) {
                additionalRequirement = 3;

            } else if (item instanceof HoeItem) {
                additionalRequirement = 2;

            } else {
                additionalRequirement = 1;
            }

            requiredIntelligence = baseRequirement + additionalRequirement + enchantmentBonus;
        } else if (item instanceof BowItem) {
            requiredIntelligence = 15 + enchantmentBonus;
        } else if (item instanceof CrossbowItem) {
            requiredIntelligence = 20 + enchantmentBonus;
        } else {
            return 0;
        }

        return requiredIntelligence;
    }

    private static void checkToolUsage(PlayerInteractEvent event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();

        if (itemStack.isEmpty()) {
            return;
        }

        final int requiredIntelligence = calculate(itemStack);

        if (requiredIntelligence <= 0) {
            return;
        }

        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
            int intelligence = attributes.get("Intelligence");

            if (intelligence < requiredIntelligence) {
                event.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        Player player = event.getEntity();
        ItemStack itemStack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        int requiredIntelligence = calculate(itemStack);


        if (requiredIntelligence > 0 && player != null) {
            final int[] playerIntelligence = {0};
            final int[] playerAgility = {0};
            player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                playerIntelligence[0] = attributes.get("Intelligence");
                playerAgility[0] = attributes.get("Agility");
            });

            tooltip.add(Component.translatable("tooltip.tponr.required_stats")
                    .withStyle(ChatFormatting.BLUE));

            Component intelligenceStat = Component.translatable("stat.tponr.intelligence")
                    .append(Component.literal(": "))
                    .withStyle(ChatFormatting.WHITE);
            ChatFormatting intelligenceColor = playerIntelligence[0] >= requiredIntelligence ?
                    ChatFormatting.GREEN : ChatFormatting.RED;
            Component intelligenceValueText = Component.literal(String.valueOf(requiredIntelligence))
                    .withStyle(intelligenceColor);

            Component agilityStat = Component.translatable("stat.tponr.agility")
                    .append(Component.literal(": "))
                    .withStyle(ChatFormatting.WHITE);
            ChatFormatting agilityColor = playerAgility[0] >= agilityRequirement[0] ?
                    ChatFormatting.GREEN : ChatFormatting.RED;
            Component agilityValueText = Component.literal(String.valueOf(agilityRequirement[0]))
                    .withStyle(intelligenceColor);

            tooltip.add(Component.empty().append(intelligenceStat).append(intelligenceValueText));
            tooltip.add(Component.empty().append(agilityStat).append(agilityValueText));
        }
    }
}
