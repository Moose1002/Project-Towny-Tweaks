package moose.projecttownytweaks.common.items;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.util.text.TextComponentTranslation;
import net.fexcraft.mod.states.data.capabilities.PlayerCapability;
import net.fexcraft.mod.states.data.capabilities.StatesCapabilities;


public class ItemWarDeclaration extends ItemBase{
	
	public ItemWarDeclaration(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	


		@Override
	    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
	        
			PlayerCapability playerCap = playerIn.getCapability(StatesCapabilities.PLAYER, null);

			ItemStack heldItem = playerIn.getHeldItem(handIn);
			
			if (!worldIn.isRemote) {
				
				if (playerCap.isMayorOf(playerCap.getMunicipality())) {
					
					playerIn.sendMessage(new TextComponentTranslation("Declaring War"));
				}
				
				else {
					
					playerIn.sendMessage(new TextComponentTranslation("You're not a mayor!"));
				}
				
				//titleCommand.execute(MinecraftServer, playerIn, commandArgs); 
				// /title @a subtitle "NCR has declared war on"
				// /title @a title "War Declared"

		}
	    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
	 }
}
