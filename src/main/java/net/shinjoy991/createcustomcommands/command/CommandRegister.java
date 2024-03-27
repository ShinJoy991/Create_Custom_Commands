package net.shinjoy991.createcustomcommands.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.shinjoy991.createcustomcommands.command.custom.CustomCommands;
import net.shinjoy991.createcustomcommands.CreateCustomCommands;

import static net.shinjoy991.createcustomcommands.config.ReadConfig.keysList;

@Mod.EventBusSubscriber(modid = CreateCustomCommands.MOD_ID)
public class CommandRegister {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {

        new CustomCommands(event.getDispatcher(), keysList, keysList.size());
        ConfigCommand.register(event.getDispatcher());
    }
}
