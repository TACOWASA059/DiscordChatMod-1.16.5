package com.github.tacowasa059.discordchatmod.event;

import com.github.tacowasa059.discordchatmod.DiscordChatMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.client.gui.AbstractGui.fill;

@Mod.EventBusSubscriber(modid = DiscordChatMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class renderHandler {
    public static List<String> inputtingPlayers = new ArrayList<>();
    private static final String[] INPUT_ANIMATION = {"..✍", "✍..", ".✍."};
    private static int  animationIndex = 0;
    private static int  dt=0;

    @SubscribeEvent
    public static void UpdateInputAnimation(TickEvent.ClientTickEvent event) {
        if(dt>6){
            animationIndex = (animationIndex + 1) % INPUT_ANIMATION.length;
            dt=0;
        }
        else{
            dt++;
        }
    }

    @SubscribeEvent
    public static void onPostRenderGuiOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getInstance().player == null) return;
        MatrixStack stack = event.getMatrixStack();
        List<String> tmp_list=inputtingPlayers;
        tmp_list.remove(Minecraft.getInstance().player.getEntity().getName().getString());
        if(tmp_list.size()==0)return;
        stack.push();
        int chatWidgetY = event.getWindow().getScaledHeight() - 25;
        extracted(stack, tmp_list, chatWidgetY);
        stack.pop();
//        List<String> tmp_list2=new ArrayList<>(tmp_list);
//        if(tmp_list2.size()==0){
//            return;
//        }
//        stack.push();
//        extracted(stack, tmp_list2, chatWidgetY-10);
//        stack.pop();
    }

    private static void extracted(MatrixStack stack, List<String> tmp_list, int chatWidgetY) {
        Minecraft mc=Minecraft.getInstance();
        String pos=TextFormatting.AQUA+INPUT_ANIMATION[animationIndex];
        pos+=TextFormatting.BOLD;
        pos+=TextFormatting.GREEN+ StringUtils.join(tmp_list, ", ");
        pos+=TextFormatting.WHITE+I18n.format("discordchatmod.display");
        // テキストの位置を計算
        fill(stack,2,chatWidgetY-2,5+mc.fontRenderer.getStringWidth(pos),chatWidgetY+mc.fontRenderer.FONT_HEIGHT,0x80000000);
        renderText(Minecraft.getInstance(),pos, stack,5, chatWidgetY);
    }

    private static void renderText(Minecraft minecraft,String s,MatrixStack stack,float a,float b){
        if (!minecraft.gameSettings.showDebugInfo) {
            minecraft.fontRenderer.drawStringWithShadow(stack,s,a-1,b-1, Color.WHITE.getRGB());
        }
    }

}
