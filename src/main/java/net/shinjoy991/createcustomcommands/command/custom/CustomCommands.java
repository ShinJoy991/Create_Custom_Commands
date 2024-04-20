package net.shinjoy991.createcustomcommands.command.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;

import static net.shinjoy991.createcustomcommands.CreateCustomCommands.LOGGER;
import static net.shinjoy991.createcustomcommands.config.ReadConfig.*;
public class CustomCommands {

    public CustomCommands(CommandDispatcher<CommandSource> dispatcher,
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

    private static void registerCommands(CommandDispatcher<CommandSource> dispatcher, String key) {
        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal(key).executes(command
                -> {CustomCommandActivate(command.getSource(), key);
            return 0;}));
    }

    private static void CustomCommandActivate(CommandSource source, String key) throws CommandSyntaxException {

        Entity player = source.getEntity();
        if (!(player instanceof ServerPlayerEntity)) {
            return;
        }
        ArrayList<String> returnArray = ReadElement(key);
        if (player.hasPermissions(Integer.parseInt(returnArray.get(0)))) {
            if (!player.level.isClientSide && player.level.getServer() != null) {
                MinecraftServer source1 = player.getServer();
                if (returnArray.get(1).equalsIgnoreCase("no")) {
                    for (int i = 2; i < returnArray.size(); i++) {
                        if (source1 != null) {
                            source1.getCommands().performCommand(player.createCommandSourceStack().
                                    withSuppressedOutput(), returnArray.get(i));
                        }
                    }
                } else {
                    for (int i = 2; i < returnArray.size(); i++) {
                        if (source1 != null) {
                            source1.getCommands().performCommand(player.createCommandSourceStack(),
                                    returnArray.get(i));
                        }
                    }
                }
            }
        }
    }

}
