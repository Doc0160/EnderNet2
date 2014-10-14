package pl.asie.endernet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import pl.asie.endernet.api.EnderNetRegistry;
import pl.asie.endernet.endpoint.EndpointAcceptsItems;
import pl.asie.endernet.endpoint.EndpointVersion;
import pl.asie.endernet.server.EnderServer;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = EnderNet.MODID, version = EnderNet.VERSION)
public class EnderNet
{
    public static final String MODID = "endernet";
    public static final String VERSION = "1.90.0";
    public static final int API_VERSION = 1;
    
    public static int HTTP_PORT;
    
    @Instance(value = EnderNet.MODID)
    public static EnderNet instance;
    
	public static Logger log;
	public static Configuration config;
	public static Gson gson = new Gson();
	
	public static EnderServer server;
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	log = LogManager.getLogger(EnderNet.MODID);
    	config = new Configuration(event.getSuggestedConfigurationFile());
    	config.load();
    	
    	// Config entries
    	HTTP_PORT = EnderNet.config.getInt("port", "server", 8123, 0, 65535, "The port for the EnderNet HTTP server.");
    	
    	config.save();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	server = new EnderServer();
    	
    	// EnderNet information endpoints (/api)
    	server.registerEndpoint(new EndpointAcceptsItems());
    	server.registerEndpoint(new EndpointVersion());
    }
    
    @EventHandler
    public void serverStart(FMLServerStartingEvent event) {
    	try {
    		server.start();
    		EnderNetRegistry.instance = new EnderNetRegistry(event.getServer().getFile("endernet-registry.json"));
    	} catch(Exception e) {
    		log.error("Could not start EnderNet server!");
    		e.printStackTrace();
    	}
    }
    
    @EventHandler
    public void serverStop(FMLServerStoppingEvent event) {
    	server.stop();
    }
}
