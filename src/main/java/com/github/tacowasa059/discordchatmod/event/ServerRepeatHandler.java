package com.github.tacowasa059.discordchatmod.event;

import com.github.tacowasa059.discordchatmod.DiscordChatMod;
import com.github.tacowasa059.discordchatmod.Packet.PlayerListPacket;
import com.github.tacowasa059.discordchatmod.network.ServerToClientNetwork;
import com.github.tacowasa059.discordchatmod.server.ServerPacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = DiscordChatMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerRepeatHandler {
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onRepeatingTask(TickEvent.ServerTickEvent event) {
        if(event.side.isServer()){
            tickCounter++;
            if (tickCounter >= 6) { // 0.3秒ごと
                tickCounter = 0;
                ServerPacketHandler.checkInactivePlayers();
                ServerToClientNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new PlayerListPacket(new ArrayList<>(ServerPacketHandler.inputtingPlayers)));
            }
        }
    }
}
