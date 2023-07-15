package com.github.tacowasa059.discordchatmod.server;


import com.github.tacowasa059.discordchatmod.Packet.PlayerListPacket;
import com.github.tacowasa059.discordchatmod.network.ServerToClientNetwork;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.*;

public class ServerPacketHandler {
    public static Set<String> inputtingPlayers = new HashSet<>();
    private static Map<String, Long> lastPacketTime = new HashMap<>();

    public static void updatePlayerName(String playerName,boolean isadd) {
        if (!playerName.isEmpty()) {
            if(isadd){
                inputtingPlayers.add(playerName);
                lastPacketTime.put(playerName, System.currentTimeMillis());
            }else{
                inputtingPlayers.remove(playerName);
                lastPacketTime.remove(playerName);
            }

        }
        ServerToClientNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new PlayerListPacket(new ArrayList<>(inputtingPlayers)));
    }
    public static void checkInactivePlayers() {
        long currentTime = System.currentTimeMillis();
        List<String> inactivePlayers = new ArrayList<>();
        Iterator<Map.Entry<String, Long>> iterator = lastPacketTime.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Long> entry = iterator.next();
            String playerName = entry.getKey();
            long lastTime = entry.getValue();
            long inactiveTime = currentTime - lastTime;
            if (inactiveTime > 3000) {//3秒=3000ms
                // 5秒間パケットを受信していないプレイヤーを削除
                inactivePlayers.add(playerName);
                iterator.remove();
                inputtingPlayers.remove(playerName);
            }
        }
    }
}
