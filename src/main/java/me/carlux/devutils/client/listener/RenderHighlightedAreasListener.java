package me.carlux.devutils.client.listener;

import me.carlux.devutils.client.manager.HighlightedAreaManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHighlightedAreasListener {

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final double playerX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * event.partialTicks;
        final double playerY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * event.partialTicks;
        final double playerZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * event.partialTicks;
        HighlightedAreaManager.INSTANCE.render(playerX, playerY, playerZ);
    }

}
