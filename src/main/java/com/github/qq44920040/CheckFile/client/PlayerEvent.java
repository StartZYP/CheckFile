package com.github.qq44920040.CheckFile.client;

import com.github.qq44920040.CheckFile.Main;
import com.github.qq44920040.CheckFile.common.NetWorkMsg;
import com.google.gson.Gson;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.common.MinecraftForge;


import java.nio.charset.Charset;
public class PlayerEvent {
    public PlayerEvent(){
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void PlayerJoinGame(FMLNetworkEvent.ClientConnectedToServerEvent event){
        Gson gson = new Gson();
        final String tempsmg = gson.toJson(Main.filemd5);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NetWorkMsg.sendMessage(tempsmg.getBytes(Charset.forName("UTF-8")));
                super.run();
            }
        }.start();
    }
}
