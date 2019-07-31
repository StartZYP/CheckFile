package com.github.qq44920040.CheckFile;


import com.github.qq44920040.CheckFile.client.PlayerEvent;
import com.github.qq44920040.CheckFile.common.CommonProxy;
import com.github.qq44920040.CheckFile.common.NetWorkMsg;
import com.google.gson.Gson;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.HashMap;

@Mod(modid = Main.MODID, version = Main.VERSION, acceptedMinecraftVersions = "1.7.10")
public class Main {
    public static final String MODID = "checkfile";
    public static final String VERSION = "1.0";
    public static final Logger logger = LogManager.getLogger(MODID);
    public static HashMap<String,String> filemd5 = new HashMap<String, String>();

    @SidedProxy(clientSide = "com.github.qq44920040.CheckFile.client.ClientProxy",
            serverSide = "com.github.qq44920040.CheckFile.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        new PlayerEvent();
        new NetWorkMsg();
        GetPathFile();
        Gson gson = new Gson();
        logger.info(gson.toJson(filemd5));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public static void GetPathFile(){
        filemd5.clear();
        File file = new File("./mods");
        try{
            logger.info(file.getCanonicalPath());
        }catch (IOException e){
            e.printStackTrace();
        }
        for (String path:file.list()){
            File filetemp = new File("./mods/"+path);
            try{
                String md5ByFile = getMd5ByFile(filetemp);
                filemd5.put(md5ByFile,path);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        File FlanFile = new File("./Flan/Emberscraft-Pack-Stalingrad-1.7.10.jar");
        if (FlanFile.exists()){
            try{
                String md5ByFile = getMd5ByFile(FlanFile);
                filemd5.put(md5ByFile,"Emberscraft-Pack-Stalingrad-1.7.10.jar");
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

    }

    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
}
