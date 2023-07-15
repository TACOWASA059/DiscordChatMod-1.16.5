package com.github.tacowasa059.discordchatmod.Packet;


import com.github.tacowasa059.discordchatmod.server.ServerPacketHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerNamePacket {
    private String playerName;
    private boolean isadd;
    public PlayerNamePacket(String playerName,boolean isadd) {
        this.playerName = playerName;
        this.isadd=isadd;
    }

    public static void encode(PlayerNamePacket packet, PacketBuffer buffer) {

        buffer.writeString(packet.playerName);
        buffer.writeBoolean(packet.isadd);
    }

    public static PlayerNamePacket decode(PacketBuffer buffer) {
        String playerName = buffer.readString();
        boolean is_add=buffer.readBoolean();
        return new PlayerNamePacket(playerName,is_add);
    }

    public static void handle(PlayerNamePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPacketHandler.updatePlayerName(packet.playerName,packet.isadd);
        });
        ctx.setPacketHandled(true);
    }
}
