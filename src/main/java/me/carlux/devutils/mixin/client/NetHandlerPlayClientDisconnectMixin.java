package me.carlux.devutils.mixin.client;

import me.carlux.devutils.client.handler.ClientDisconnectHandler;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientDisconnectMixin {

    @Inject(method = "handleDisconnect", at = @At("HEAD"))
    public void onHandleDisconnect(S40PacketDisconnect packetIn, CallbackInfo ci) {
        ClientDisconnectHandler.onDisconnect();
    }

    @Inject(method = "onDisconnect", at = @At("HEAD"))
    public void onDisconnect(IChatComponent reason, CallbackInfo ci) {
        ClientDisconnectHandler.onDisconnect();
    }

}
