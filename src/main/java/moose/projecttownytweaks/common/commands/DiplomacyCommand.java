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
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

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
		Data externalData = new Data();
		
		for (EntityPlayer player : server.getPlayerList().getPlayers()) {
			System.out.println(player);
			PlayerCapability otherPlayerCap = player.getCapability(StatesCapabilities.PLAYER, null);
			if (otherPlayerCap.getMunicipality().getId() <= 0) continue;
			if (otherPlayerCap.isMayorOf(otherPlayerCap.getMunicipality())) {
				muns.add(otherPlayerCap.getMunicipality());
				System.out.println(muns);
			}
		}
		
 		if (args.length > 0) {
			
			if ("online".equalsIgnoreCase(args[0])) {
					
				if (muns.isEmpty()) {
					Print.chat(sender, "No Municipalities mayors are online");
				}
				
				else {
					
					Print.chat(sender, "Online Municipalities:");
					System.out.println(muns);
					for(Municipality mun : muns) {
						Print.chat(sender, "&2-> &6" + mun.getName() + " (" + mun.getId() + ")");
					}
				}
			}
			
			if ("declarewar".equalsIgnoreCase(args[0])) {
				
				if (playerCap.isMayorOf(playerCap.getMunicipality())) {
					
				if (args.length >= 2) {
				
				if (playerCap.getMunicipality().getId() != Integer.parseInt(args[1])) {	
					for(Municipality mun : muns) {
							
						if (mun.getId() == Integer.parseInt(args[1])) {
							
							ArrayList<Integer> activeWars = externalData.getActiveWars(playerCap.getMunicipality());
							int spawnRadius = 1000000000; // This is a terrible way to do this
							
							if (!activeWars.contains(mun.getId())) {
								
							Print.chat(sender, "Declaring War against " + mun.getName());
							Municipality localMun = playerCap.getMunicipality();
							externalData.getActiveWars(playerCap.getMunicipality()).add(mun.getId());
							
							SPacketTitle.Type typeTitle = SPacketTitle.Type.TITLE;
							ITextComponent warDeclaredText = new TextComponentString("War Declared");
							SPacketTitle warDeclaredTitlePacket = new SPacketTitle(typeTitle, warDeclaredText);
							
							SPacketTitle.Type typeSubtitle = SPacketTitle.Type.SUBTITLE;
							ITextComponent warDeclaredSubText = new TextComponentString(localMun.getName() + " has declared war on " + mun.getName());
							SPacketTitle warDeclaredSubtitlePacket = new SPacketTitle(typeSubtitle, warDeclaredSubText);
							
							server.getPlayerList().sendPacketToAllPlayers(warDeclaredSubtitlePacket);
							server.getPlayerList().sendPacketToAllPlayers(warDeclaredTitlePacket);
							server.getPlayerList().sendPacketToAllPlayers(new SPacketCustomSound("minecraft:ambient.cave", SoundCategory.MASTER, 0, 0, 0, spawnRadius, (float) 1));


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
					Print.chat(sender, "You can't declare war on yourself");
					}
				}
				else {
					Print.chat(sender, "&cUsage: /diplomacy declarewar (municipality id)");
				}
			}
			else {
				Print.chat(sender, "Only Municipality mayors can declare war");
			}
		}

			if ("peace".equalsIgnoreCase(args[0])) {
				
				if (playerCap.isMayorOf(playerCap.getMunicipality())) {
					
				if (args.length >= 2) {
					
				if (playerCap.getMunicipality().getId() != Integer.parseInt(args[1])) {
						
					for(Municipality mun : muns) {
						
						if (mun.getId() == Integer.parseInt(args[1])) {
							ArrayList<Integer> activeWars = externalData.getActiveWars(playerCap.getMunicipality());
							int spawnRadius = 1000000000;
							
							if (activeWars.contains(mun.getId())) {
								
							Municipality localMun = playerCap.getMunicipality();
								
							Print.chat(sender, "Making peace with " + mun.getName());	
							
							SPacketTitle.Type typeTitle = SPacketTitle.Type.TITLE;
							ITextComponent warDeclaredText = new TextComponentString("Peace Established");
							SPacketTitle warDeclaredTitlePacket = new SPacketTitle(typeTitle, warDeclaredText);
							
							SPacketTitle.Type typeSubtitle = SPacketTitle.Type.SUBTITLE;
							ITextComponent warDeclaredSubText = new TextComponentString(localMun.getName() + " has made peace with " + mun.getName());
							SPacketTitle warDeclaredSubtitlePacket = new SPacketTitle(typeSubtitle, warDeclaredSubText);
							
							server.getPlayerList().sendPacketToAllPlayers(warDeclaredSubtitlePacket);
							server.getPlayerList().sendPacketToAllPlayers(warDeclaredTitlePacket);
							server.getPlayerList().sendPacketToAllPlayers(new SPacketCustomSound("minecraft:record.cat", SoundCategory.MASTER, 0, 0, 0, spawnRadius, 1));

							
							for (int i = 0; i < activeWars.size(); i++) {
								if (activeWars.get(i) == mun.getId()) {
									externalData.getActiveWars(playerCap.getMunicipality()).remove(i);
									break;
								}
							}
								
							
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
							Print.chat(sender, "You're not at war with yourself");
						}
					
				}
				else {
					Print.chat(sender, "&cUsage: /diplomacy peace (municipality id)");
				}
			}
				else {
					Print.chat(sender, "Only Municipality mayors can make peace");
				}
		}
			if ("wars".equalsIgnoreCase(args[0])) {

				ArrayList<Integer> activeWars = externalData.getActiveWars(playerCap.getMunicipality());
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
