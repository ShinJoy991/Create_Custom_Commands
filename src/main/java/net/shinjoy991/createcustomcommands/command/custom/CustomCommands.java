package net.shinjoy991.createcustomcommands.command.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        ArrayList<String> returnArray = ReadElement(key);
        int requiredPermissionLevel = Integer.parseInt(returnArray.get(0));

        dispatcher.register(LiteralArgumentBuilder.<CommandSource>literal(key).requires(commandSource -> commandSource.hasPermission(requiredPermissionLevel)).executes(command
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
                if (returnArray.get(3).equalsIgnoreCase("createcustomcommandnull")) {
                    return;
                }

                boolean suppressOutput = returnArray.get(1).equalsIgnoreCase("no")
                        || (!returnArray.get(1).equalsIgnoreCase("no") && !returnArray.get(2)
                        .equals("notifystringnull"));
                if (!returnArray.get(1).equalsIgnoreCase("no") && !returnArray.get(2).equals(
                        "notifystringnull")) {
                    IFormattableTextComponent message = parseCustomNotifyString(returnArray.get(2));
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

    private static final Map<Character, TextFormatting> CODE_TO_FORMAT_MAP = new HashMap<>();
    static {
        // Manually initialize the mapping
        CODE_TO_FORMAT_MAP.put('0', TextFormatting.BLACK);
        CODE_TO_FORMAT_MAP.put('1', TextFormatting.DARK_BLUE);
        CODE_TO_FORMAT_MAP.put('2', TextFormatting.DARK_GREEN);
        CODE_TO_FORMAT_MAP.put('3', TextFormatting.DARK_AQUA);
        CODE_TO_FORMAT_MAP.put('4', TextFormatting.DARK_RED);
        CODE_TO_FORMAT_MAP.put('5', TextFormatting.DARK_PURPLE);
        CODE_TO_FORMAT_MAP.put('6', TextFormatting.GOLD);
        CODE_TO_FORMAT_MAP.put('7', TextFormatting.GRAY);
        CODE_TO_FORMAT_MAP.put('8', TextFormatting.DARK_GRAY);
        CODE_TO_FORMAT_MAP.put('9', TextFormatting.BLUE);
        CODE_TO_FORMAT_MAP.put('a', TextFormatting.GREEN);
        CODE_TO_FORMAT_MAP.put('b', TextFormatting.AQUA);
        CODE_TO_FORMAT_MAP.put('c', TextFormatting.RED);
        CODE_TO_FORMAT_MAP.put('d', TextFormatting.LIGHT_PURPLE);
        CODE_TO_FORMAT_MAP.put('e', TextFormatting.YELLOW);
        CODE_TO_FORMAT_MAP.put('f', TextFormatting.WHITE);
        CODE_TO_FORMAT_MAP.put('k', TextFormatting.OBFUSCATED);
        CODE_TO_FORMAT_MAP.put('l', TextFormatting.BOLD);
        CODE_TO_FORMAT_MAP.put('m', TextFormatting.STRIKETHROUGH);
        CODE_TO_FORMAT_MAP.put('n', TextFormatting.UNDERLINE);
        CODE_TO_FORMAT_MAP.put('o', TextFormatting.ITALIC);
        CODE_TO_FORMAT_MAP.put('r', TextFormatting.RESET);
    }

    private static IFormattableTextComponent parseCustomNotifyString(String notifyString) {
        IFormattableTextComponent message = new StringTextComponent("");
        Style currentStyle = Style.EMPTY;
        StringBuilder currentText = new StringBuilder();

        for (int i = 0; i < notifyString.length(); i++) {
            char currentChar = notifyString.charAt(i);

            if (currentChar == '&' && i + 1 < notifyString.length()) {
                char code = notifyString.charAt(i + 1);
                TextFormatting format = CODE_TO_FORMAT_MAP.get(code);

                if (format != null) {
                    if (currentText.length() > 0) {
                        message.append(new StringTextComponent(currentText.toString()).setStyle(currentStyle));
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

        if (currentText.length() > 0) {
            message.append(new StringTextComponent(currentText.toString()).setStyle(currentStyle));
        }

        return message;
    }
}