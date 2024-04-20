package com.github.shinjoy991.createcustomcommand.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import com.github.shinjoy991.createcustomcommand.CreateCustomCommands;
import com.github.shinjoy991.createcustomcommand.command.custom.CustomCommands;

import static com.github.shinjoy991.createcustomcommand.config.ReadConfig.keysList;

@Mod.EventBusSubscriber(modid = CreateCustomCommands.MOD_ID)
public class CommandRegister {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {

        new CustomCommands(event.getDispatcher(), keysList, keysList.size());
        ConfigCommand.register(event.getDispatcher());
    }

}
