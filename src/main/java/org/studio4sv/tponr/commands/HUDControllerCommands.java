package org.studio4sv.tponr.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.studio4sv.tponr.TPONR;
import org.studio4sv.tponr.networking.ModMessages;
import org.studio4sv.tponr.networking.packet.S2C.StaminaDataSyncS2CPacket;
import org.studio4sv.tponr.networking.packet.S2C.ToggleWidgetS2CPacket;
import org.studio4sv.tponr.mechanics.stamina.PlayerStamina;
import org.studio4sv.tponr.mechanics.stamina.PlayerStaminaProvider;

public class HUDControllerCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(TPONR.MOD_ID)
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("widget")
                                .then(Commands.literal("toggle")
                                        .then(Commands.literal("all")
                                                .executes(context -> {
                                                    ServerPlayer executor = context.getSource().getPlayerOrException();
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");

                                                    if (!executor.getUUID().equals(target.getUUID())) {
                                                        if (!context.getSource().hasPermission(2)) {
                                                            throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                        }
                                                    }

                                                    ModMessages.sendToPlayer(new ToggleWidgetS2CPacket("all"), target);

                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("hunger")
                                                .executes(context -> {
                                                    ServerPlayer executor = context.getSource().getPlayerOrException();
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");

                                                    if (!executor.getUUID().equals(target.getUUID())) {
                                                        if (!context.getSource().hasPermission(2)) {
                                                            throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                        }
                                                    }

                                                    ModMessages.sendToPlayer(new ToggleWidgetS2CPacket("hunger"), target);

                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("stamina")
                                                .executes(context -> {
                                                    ServerPlayer executor = context.getSource().getPlayerOrException();
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");

                                                    if (!executor.getUUID().equals(target.getUUID())) {
                                                        if (!context.getSource().hasPermission(2)) {
                                                            throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                        }
                                                    }

                                                    ModMessages.sendToPlayer(new ToggleWidgetS2CPacket("stamina"), target);

                                                    return 1;
                                                })
                                        )
                                        .then(Commands.literal("points")
                                                .executes(context -> {
                                                    ServerPlayer executor = context.getSource().getPlayerOrException();
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");

                                                    if (!executor.getUUID().equals(target.getUUID())) {
                                                        if (!context.getSource().hasPermission(2)) {
                                                            throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                        }
                                                    }

                                                    ModMessages.sendToPlayer(new ToggleWidgetS2CPacket("points"), target);

                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("mechanic")
                                .then(Commands.literal("stamina")
                                        .then(Commands.literal("setMax")
                                                .then(Commands.argument("value", IntegerArgumentType.integer(0, PlayerStamina.getLimit()))
                                                        .executes(context -> {
                                                            ServerPlayer executor = context.getSource().getPlayerOrException();
                                                            ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                                            int value = IntegerArgumentType.getInteger(context, "value");

                                                            if (!executor.getUUID().equals(target.getUUID())) {
                                                                if (!context.getSource().hasPermission(2)) {
                                                                    throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                                }
                                                            }

                                                            target.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(stamina -> {
                                                                stamina.setMaxStamina(value);
                                                                ModMessages.sendToPlayer(new StaminaDataSyncS2CPacket(stamina.getStamina(), value), target);
                                                            });

                                                            return 1;
                                                        })
                                                )
                                        )
                                        .then(Commands.literal("toggle")
                                                .executes(context -> {
                                                    ServerPlayer executor = context.getSource().getPlayerOrException();
                                                    ServerPlayer target = EntityArgument.getPlayer(context, "player");

                                                    if (!executor.getUUID().equals(target.getUUID())) {
                                                        if (!context.getSource().hasPermission(2)) {
                                                            throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                        }
                                                    }

                                                    target.getCapability(PlayerStaminaProvider.PLAYER_STAMINA).ifPresent(PlayerStamina::toggleStamina);

                                                    return 1;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("attribute")
                                .then(Commands.argument("attributes", StringArgumentType.string())
                                        .then(Commands.literal("set")
                                                .then(Commands.argument("value", StringArgumentType.string())
                                                        .executes(context -> {
                                                            ServerPlayer executor = context.getSource().getPlayerOrException();
                                                            ServerPlayer target = EntityArgument.getPlayer(context, "player");
                                                            String attributeName = StringArgumentType.getString(context, "attributes");
                                                            String value = StringArgumentType.getString(context, "value");

                                                            if (!executor.getUUID().equals(target.getUUID())) {
                                                                if (!context.getSource().hasPermission(2)) {
                                                                    throw new SimpleCommandExceptionType(Component.translatable("command.tponr.forbidden")).create();
                                                                }
                                                            }

                                                            // TODO: connect to attribute system

                                                            return 1;
                                                        })
                                                )
                                        )
                                )
                        )
                )
        );
    }
}