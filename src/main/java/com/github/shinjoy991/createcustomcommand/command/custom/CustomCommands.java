package com.github.shinjoy991.createcustomcommand.command.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;

import static com.github.shinjoy991.createcustomcommand.CreateCustomCommand.LOGGER;
import static com.github.shinjoy991.createcustomcommand.config.ReadConfig.ReadElement;

public class CustomCommands {

    public CustomCommands(CommandDispatcher<CommandSourceStack> dispatcher,
            ArrayList<String> keysList, int index) {

        for (int i = 0; i < index; i++) {
            String key = keysList.get(i);
            try {
                registerCommands(dispatcher, key);
            } catch (Exception e) {
                //e.printStackTrace();
                LOGGER.error("Error registering command for key: " + key + " " + e);
            }
        }
    }

    private static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, String key) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal(key).executes(command
                -> {CustomCommandActivate(command.getSource(), key);
            return 0;}));
    }

    private static void CustomCommandActivate(CommandSourceStack source, String key) throws CommandSyntaxException {

        Entity player = source.getEntity();
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        ArrayList<String> returnArray = ReadElement(key);
        if (player.hasPermissions(Integer.parseInt(returnArray.get(0)))) {
            if (!player.level().isClientSide && player.level().getServer() != null) {
                MinecraftServer source1 = player.getServer();
                if (returnArray.get(1).equalsIgnoreCase("no")) {
                    for (int i = 2; i < returnArray.size(); i++) {
                        if (source1 != null) {
                            source1.getCommands().performPrefixedCommand(player.createCommandSourceStack()
                                    .withSuppressedOutput().withPermission(4), returnArray.get(i));
                        }
                    }
                } else {
                    for (int i = 2; i < returnArray.size(); i++) {
                        if (source1 != null) {
                            source1.getCommands().performPrefixedCommand(player.createCommandSourceStack()
                                            .withPermission(4),
                                    returnArray.get(i));
                        }
                    }
                }
            }
        }
    }

}
