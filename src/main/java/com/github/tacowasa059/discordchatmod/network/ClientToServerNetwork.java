package com.github.tacowasa059.discordchatmod.network;


import com.github.tacowasa059.discordchatmod.DiscordChatMod;
import com.github.tacowasa059.discordchatmod.Packet.PlayerNamePacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ClientToServerNetwork {
    public static final String NETWORK_VERSION="0.1.0";

    //ResouceLocation(namespace,path)
    public static final SimpleChannel CHANNEL= NetworkRegistry.newSimpleChannel(new ResourceLocation(DiscordChatMod.MOD_ID,"network"),()->NETWORK_VERSION, version->version.equals(NETWORK_VERSION), version->version.equals(NETWORK_VERSION));
    public static void init(){
        CHANNEL.registerMessage(0, PlayerNamePacket.class,PlayerNamePacket::encode,PlayerNamePacket::decode,PlayerNamePacket::handle);
    }
}
