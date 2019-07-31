package com.github.qq44920040.CheckFile.common;

import com.github.qq44920040.CheckFile.Main;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;

public class NetWorkMsg {
    public static FMLEventChannel channel;
    public static NetWorkMsg netWorkMsg;


    public NetWorkMsg() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        NetWorkMsg.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(Main.MODID);
        NetWorkMsg.channel.register(this);
    }
    public static void sendMessage(byte[] array) {
        ByteBuf buf = Unpooled.wrappedBuffer(array);
        FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(buf), Main.MODID);
        channel.sendToServer(packet);
    }

}
