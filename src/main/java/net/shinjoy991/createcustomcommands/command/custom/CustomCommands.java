package net.shinjoy991.createcustomcommands.command.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;

import static net.shinjoy991.createcustomcommands.config.ReadConfig.*;
import static net.shinjoy991.createcustomcommands.CreateCustomCommands.LOGGER;
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
        dispatcher.register(Commands.literal(key).executes((command) -> {
            return CustomCommandActivate(command.getSource(), key);
        }));
    }

    private static int CustomCommandActivate(CommandSource source, String key) throws CommandSyntaxException {

        ServerPlayerEntity player = source.getPlayerOrException();
        ArrayList<String> returnArray = ReadElement(key);
        if (player.hasPermissions(Integer.parseInt(returnArray.get(0)))) {
            if (!player.level.isClientSide && player.level.getServer() != null) {
                MinecraftServer source1 = player.server;
                if (returnArray.get(1).equalsIgnoreCase("no")) {
                    for (int i = 2; i < returnArray.size(); i++) {
                        source1.getCommands().performCommand(player.createCommandSourceStack().
                                withSuppressedOutput(), returnArray.get(i));
                    }
                } else {
                    for (int i = 2; i < returnArray.size(); i++) {
                        source1.getCommands().performCommand(player.createCommandSourceStack(),
                                returnArray.get(i));
                    }
                }
            }
        }
        return 0;
    }

}
