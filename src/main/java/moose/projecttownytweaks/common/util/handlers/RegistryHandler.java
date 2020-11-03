package moose.projecttownytweaks.common.util.handlers;

//import moose.statesdiplomacy.common.items.ItemInit;
//import moose.statesdiplomacy.common.util.IHasModel;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		
		// event.getRegistry().register(ItemInit.WAR_DECLARATION);

	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
				
		//	if(ItemInit.WAR_DECLARATION instanceof IHasModel) {
				
			//	((IHasModel)ItemInit.WAR_DECLARATION).registerModels();
	//	}
	}
}
