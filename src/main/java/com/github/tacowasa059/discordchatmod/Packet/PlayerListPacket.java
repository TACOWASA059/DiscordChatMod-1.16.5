package com.github.tacowasa059.discordchatmod.Packet;


import com.github.tacowasa059.discordchatmod.event.renderHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PlayerListPacket {
    private List<String> playerList;
    public PlayerListPacket(List<String> playerList) {
        this.playerList = playerList;
    }

    public static void encode(PlayerListPacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.playerList.size());
        for (String playerName : packet.playerList){
            buffer.writeString(playerName);
        }
    }

    public static PlayerListPacket decode(PacketBuffer buffer) {
        int count = buffer.readInt();
        List<String> playerList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            playerList.add(buffer.readString());
        }
        return new PlayerListPacket(playerList);
    }
    public static void handle(PlayerListPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
//            System.out.println("クライアント:プレイヤーリストを受け取った");
            List<String> playerList = packet.playerList;
            renderHandler.inputtingPlayers=playerList;
        });
        context.setPacketHandled(true);
    }
}
