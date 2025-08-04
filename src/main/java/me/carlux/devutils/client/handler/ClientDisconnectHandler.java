package me.carlux.devutils.client.handler;

import me.carlux.devutils.client.manager.HighlightedAreaManager;

public class ClientDisconnectHandler {

    public static void onDisconnect() {
        HighlightedAreaManager.INSTANCE.clear();
    }

}
