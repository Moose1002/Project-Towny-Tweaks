package moose.projecttownytweaks.common.data;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.fexcraft.mod.states.data.Municipality;
import net.fexcraft.mod.states.data.root.ExternalData;


public class Data implements ExternalData{
    
	public ArrayList<Integer> activeWars = new ArrayList<>();
			
    public ArrayList<Integer> getActiveWars(Municipality mun) {
    	Data externalData = mun.getExternalData("project_towny_tweaks");
		return externalData.activeWars;
    }
    
    public void load(JsonElement elm){
        JsonObject obj = elm.getAsJsonObject();
        if(obj.has("activeWars")){
            JsonArray array = obj.get("activeWars").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
            	System.out.println(array);
				activeWars.add(array.getAsInt());
			}
        }

    }

    public JsonElement save(){
        JsonObject obj = new JsonObject();
        if(!activeWars.isEmpty()){
            JsonArray array = new JsonArray();
            for (int i = 0; i < activeWars.size(); i++){
                 array.add(activeWars.get(i));
            }
            obj.add("activeWars", array);
        }
        return obj;
    }
}