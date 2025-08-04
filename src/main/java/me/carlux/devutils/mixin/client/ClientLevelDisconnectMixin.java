package me.carlux.devutils.mixin.client;

import me.carlux.devutils.client.handler.ClientDisconnectHandler;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public class ClientLevelDisconnectMixin {

    @Inject(method = "sendQuittingDisconnectingPacket", at = @At("HEAD"))
    public void onHandleDisconnect(CallbackInfo ci) {
        ClientDisconnectHandler.onDisconnect();
    }

}
