package com.github.shinjoy991.createcustomcommand;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.github.shinjoy991.createcustomcommand.config.CreateJson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.shinjoy991.createcustomcommand.config.ReadConfig.readJsonValue;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("createcustomcommand")
public class CreateCustomCommands {
    public static final String MOD_ID = "createcustomcommand";
    public static final Logger LOGGER = LogManager.getLogger();
    public CreateCustomCommands() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("CreateCustomCommands PREINIT setting up...");
        CreateJson.CreateJsonConfigFile();
        readJsonValue(CreateJson.configFile);
    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        //LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }
    private void enqueueIMC(final InterModEnqueueEvent event) {
    }
    private void processIMC(final InterModProcessEvent event) {
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Create Custom Command PREINIT serer setting up...");
    }
}
