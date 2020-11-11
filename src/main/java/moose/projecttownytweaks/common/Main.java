package moose.projecttownytweaks.common;


import moose.projecttownytweaks.common.commands.DiplomacyCommand;
import moose.projecttownytweaks.common.util.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:states")
public class Main {

	@Instance
	public static Main instance;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
				
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		
	}
	
	@EventHandler
	public static void registerCommands(FMLServerStartingEvent event) {
		event.registerServerCommand(new DiplomacyCommand());
	}
	
}

