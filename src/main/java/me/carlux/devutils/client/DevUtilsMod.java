package me.carlux.devutils.client;

import me.carlux.devutils.client.command.DehighlightCommand;
import me.carlux.devutils.client.command.HighlightCommand;
import me.carlux.devutils.client.listener.RenderHighlightedAreasListener;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = DevUtilsMod.MOD_ID, useMetadata=true)
public class DevUtilsMod {
    
    public static final String MOD_ID = "devutils";

    @Mod.EventHandler
    public static void onInitialize(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new HighlightCommand());
        ClientCommandHandler.instance.registerCommand(new DehighlightCommand());
        MinecraftForge.EVENT_BUS.register(new RenderHighlightedAreasListener());
    }

}
