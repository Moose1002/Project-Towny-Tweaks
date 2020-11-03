package moose.projecttownytweaks.common.commands;

import java.util.ArrayList;

import moose.projecttownytweaks.common.data.Data;
import net.fexcraft.lib.mc.utils.Print;
import net.fexcraft.mod.states.data.Municipality;
import net.fexcraft.mod.states.data.capabilities.PlayerCapability;
import net.fexcraft.mod.states.data.capabilities.StatesCapabilities;
import net.fexcraft.mod.states.util.StateUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException; 
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.server.MinecraftServer;

public class DiplomacyCommand extends CommandBase {

	
	@Override
	public String getName() {
		return "diplomacy";
	}
	
	public int getRequiredPermissionLevel()
    {
        return 0;
    }
	
	@Override
	public String getUsage(ICommandSender sender) {
		return "/diplomacy <declarewar|makepeace>";
	}


	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		PlayerCapability playerCap = ((EntityPlayer) sender).getCapability(StatesCapabilities.PLAYER, null);
		ArrayList<Municipality> muns = new ArrayList<>();
		//Data data = new Data();
		Data externalData = playerCap.getMunicipality().getExternalData("activeWars");
		
		for (EntityPlayer player : server.getPlayerList().getPlayers()) {	
			PlayerCapability otherPlayerCap = player.getCapability(StatesCapabilities.PLAYER, null);
			//Re-enable after testing is done!
			//if (otherPlayerCap.getMunicipality().getId() == playerCap.getMunicipality().getId()) continue;
			if (otherPlayerCap.getMunicipality().getId() <= 0) continue;
			if (otherPlayerCap.isMayorOf(playerCap.getMunicipality())) {
				muns.add(otherPlayerCap.getMunicipality());
			}
		}
		
 		if (args.length > 0) {
			
			if ("online".equalsIgnoreCase(args[0])) {
					
				if (muns.isEmpty()) {
					Print.chat(sender, "No Municipalities mayors are online");
				}
				
				else {
					
					Print.chat(sender, "Online Municipalities:");
					
					for(Municipality mun : muns) {
						Print.chat(sender, "&2-> &6" + mun.getName() + " (" + mun.getId() + ")");
					}
				}
			}
			
			if ("declarewar".equalsIgnoreCase(args[0])) {

				if (args.length >= 2) {

					for(Municipality mun : muns) {
						
						if (mun.getId() == Integer.parseInt(args[1])) {
							ArrayList<Integer> activeWars = externalData.getActiveWars();
							
							if (!activeWars.contains(mun.getId())) {
							Print.chat(sender, "Declaring War against " + mun.getName());
							externalData.getActiveWars().add(mun.getId());
							}
							else {
								Print.chat(sender, "You are already at war with " + mun.getName());
							}
						}						
						else {
							Print.chat(sender, "Municipality not found, or Muncipality mayor offline");
						}			
					}
				}
				else {
					Print.chat(sender, "&cUsage: /diplomacy declarewar (municipality id)");
				}
			}

			if ("peace".equalsIgnoreCase(args[0])) {
				
				if (args.length >= 2) {
	
					for(Municipality mun : muns) {
						if (mun.getId() == Integer.parseInt(args[1])) {
							ArrayList<Integer> activeWars = externalData.getActiveWars();
							
							if (activeWars.contains(mun.getId())) {
							Print.chat(sender, "Making peace with " + mun.getName());
							externalData.getActiveWars().remove(mun.getId());
							}
							else {
								Print.chat(sender, "You are currently not at war with " + mun.getName());
							}
						}
						
						else {
							Print.chat(sender, "Municipality not found, or Muncipality mayor offline");
						}			
					}
				}
				else {
					Print.chat(sender, "&cUsage: /diplomacy peace (municipality id)");
				}
			}
			
			if ("wars".equalsIgnoreCase(args[0])) {

				ArrayList<Integer> activeWars = externalData.getActiveWars();
				
				//externalData.getActiveWars().add(1);
				
				if (!(activeWars.size() == 0)) {
					Print.chat(sender, "Active Wars:");
					for (int i = 0; i < activeWars.size(); i++) {
						Integer x = activeWars.get(i);
						Print.chat(sender, "&2-> &6" + StateUtil.getMunicipalityName(x) + " (" + x + ")");
					}
				}
				else {
					Print.chat(sender, "Your Municipality is not currently at war");
				}
			
					
			}

			
			if ("help".equalsIgnoreCase(args[0])) {
				
				Print.chat(sender, "/diplomacy online\n/diplomacy declarewar\n/diplomacy wars\n/diplomacy peace");

				
					
			}
		}
	}
}
