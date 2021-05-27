package ru.smole.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public interface IApiPlayer {

    void sendMessage(String msg);

    void sendChatMessage(String msg);

    MinecraftServer getServer();

    EntityPlayer getMcPlayer();
}
