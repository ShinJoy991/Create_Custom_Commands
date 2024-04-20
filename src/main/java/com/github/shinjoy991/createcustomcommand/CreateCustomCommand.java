package com.github.shinjoy991.createcustomcommand;

import com.github.shinjoy991.createcustomcommand.config.CreateJson;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.github.shinjoy991.createcustomcommand.config.ReadConfig.readJsonValue;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreateCustomCommand.MOD_ID)
public class CreateCustomCommand {

    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "createcustomcommand";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateCustomCommand() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("CreateCustomCommand PREINIT setting up...");
        CreateJson.CreateJsonConfigFile();
        readJsonValue(CreateJson.configFile);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Create Custom Command PREINIT serer setting up...");
    }
}