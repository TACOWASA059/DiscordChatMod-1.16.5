package com.github.tacowasa059.discordchatmod.network;


import com.github.tacowasa059.discordchatmod.DiscordChatMod;
import com.github.tacowasa059.discordchatmod.Packet.PlayerListPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
public class ServerToClientNetwork {
    public static final String NETWORK_VERSION="0.1.0";

    public static final SimpleChannel CHANNEL= NetworkRegistry.newSimpleChannel(new ResourceLocation(DiscordChatMod.MOD_ID,"network2"),()->NETWORK_VERSION, version->version.equals(NETWORK_VERSION), version->version.equals(NETWORK_VERSION));
    public static void init(){
        CHANNEL.registerMessage(1, PlayerListPacket.class,PlayerListPacket::encode,PlayerListPacket::decode,PlayerListPacket::handle);
    }
}