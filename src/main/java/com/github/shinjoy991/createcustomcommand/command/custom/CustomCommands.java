package com.github.shinjoy991.createcustomcommand.command.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;

import static com.github.shinjoy991.createcustomcommand.CreateCustomCommands.LOGGER;
import static com.github.shinjoy991.createcustomcommand.config.ReadConfig.*;
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
        ArrayList<String> returnArray = ReadElement(key);
        int requiredPermissionLevel = Integer.parseInt(returnArray.get(0));

        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal(key).requires(commandSource -> commandSource.hasPermission(requiredPermissionLevel)).executes(command
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
            if (!player.level.isClientSide && player.level.getServer() != null) {
                MinecraftServer source1 = player.getServer();
                if (returnArray.get(3).equalsIgnoreCase("createcustomcommandnull")) {
                    return;
                }

                boolean suppressOutput = returnArray.get(1).equalsIgnoreCase("no")
                        || (!returnArray.get(1).equalsIgnoreCase("no") && !returnArray.get(2)
                        .equals("notifystringnull"));
                if (!returnArray.get(1).equalsIgnoreCase("no") && !returnArray.get(2).equals(
                        "notifystringnull")) {
                    MutableComponent message = parseCustomNotifyString(returnArray.get(2));
                    player.sendMessage(message, player.getUUID());
                }

                for (int i = 3; i < returnArray.size(); i++) {
                    if (source1 != null) {
                        if (suppressOutput) {
                            source1.getCommands().performCommand(
                                    player.createCommandSourceStack().withSuppressedOutput().withPermission(4),
                                    returnArray.get(i)
                            );
                        } else {
                            source1.getCommands().performCommand(
                                    player.createCommandSourceStack().withPermission(4),
                                    returnArray.get(i)
                            );
                        }
                    }
                }
            }
        }

    }

    private static MutableComponent parseCustomNotifyString(String notifyString) {
        MutableComponent message = new TextComponent("");
        Style currentStyle = Style.EMPTY;
        StringBuilder currentText = new StringBuilder();

        for (int i = 0; i < notifyString.length(); i++) {
            char currentChar = notifyString.charAt(i);

            if (currentChar == '&' && i + 1 < notifyString.length()) {
                char code = notifyString.charAt(i + 1);
                ChatFormatting format = ChatFormatting.getByCode(code);

                if (format != null) {
                    if (!currentText.isEmpty()) {
                        message.append(new TextComponent(currentText.toString()).setStyle(currentStyle));
                        currentText.setLength(0);
                    }
                    currentStyle = currentStyle.applyFormat(format);
                    i++;
                } else {
                    currentText.append(currentChar);
                }
            } else {
                currentText.append(currentChar);
            }
        }

        if (!currentText.isEmpty()) {
            message.append(new TextComponent(currentText.toString()).setStyle(currentStyle));
        }

        return message;
    }
}
