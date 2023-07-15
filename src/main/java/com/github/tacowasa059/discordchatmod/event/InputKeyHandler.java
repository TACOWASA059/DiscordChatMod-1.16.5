package com.github.tacowasa059.discordchatmod.event;

import com.github.tacowasa059.discordchatmod.DiscordChatMod;
import com.github.tacowasa059.discordchatmod.Packet.PlayerNamePacket;
import com.github.tacowasa059.discordchatmod.network.ClientToServerNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = DiscordChatMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InputKeyHandler {
    private static int pre_TextLength=0;
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        Minecraft mc=Minecraft.getInstance();
        if(mc==null)return;
        if (mc.currentScreen instanceof ChatScreen) {
            int num=examineInputField(mc.currentScreen);
            if(num>0){
                // パケットを作成
                if(Minecraft.getInstance().player.getName()!=null) {
                    String playerName = Minecraft.getInstance().player.getName().getString();
                    PlayerNamePacket packet=new PlayerNamePacket(playerName,true);
                    // パケットを送信
                    System.out.println("パケットを送信");
                    ClientToServerNetwork.CHANNEL.sendToServer(packet);
                }
            }
        }
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof ChatScreen) {
            pre_TextLength=0;
        }else if(event.getGui()==null){
            String playerName = Minecraft.getInstance().player.getName().getString();
            PlayerNamePacket packet=new PlayerNamePacket(playerName,false);
            ClientToServerNetwork.CHANNEL.sendToServer(packet);
        }
    }
    private static int examineInputField(Screen screen){
        ChatScreen chatScreen=(ChatScreen) screen;
        TextFieldWidget textField=chatScreen.inputField;
        String text = textField.getText();
        int num=text.length()-pre_TextLength;
        pre_TextLength=text.length();
        return num;
    }
}