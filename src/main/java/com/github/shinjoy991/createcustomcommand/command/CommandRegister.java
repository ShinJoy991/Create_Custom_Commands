package com.github.shinjoy991.createcustomcommand.command;

import com.github.shinjoy991.createcustomcommand.CreateCustomCommand;
import com.github.shinjoy991.createcustomcommand.command.custom.CustomCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import static com.github.shinjoy991.createcustomcommand.config.ReadConfig.keysList;

@Mod.EventBusSubscriber(modid = CreateCustomCommand.MOD_ID)
public class CommandRegister {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {

        new CustomCommands(event.getDispatcher(), keysList, keysList.size());
        ConfigCommand.register(event.getDispatcher());
    }

}
