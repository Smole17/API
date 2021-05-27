package ru.smole.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class ApiPlayer implements IApiPlayer{

    private final Minecraft mc;
    private final EntityPlayerSP player;

    public ApiPlayer() {
        mc = Minecraft.getMinecraft();
        player = mc.player;
    }

    @Override
    public void sendMessage(String msg) {
        player.sendMessage(new TextComponentString(msg));
    }

    @Override
    public void sendChatMessage(String msg) {
        player.sendChatMessage(msg);
    }

    @Override
    public MinecraftServer getServer() {
        return player.getServer();
    }


    @Override
    public EntityPlayer getMcPlayer() {
        return player;
    }
}
