package moose.projecttownytweaks.common.data;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.fexcraft.mod.states.data.root.ExternalData;


public class Data implements ExternalData{
    
	Wars war = Wars.getInstance();
	
    public void addWar(Integer munId) {
    	war.activeWars.add(munId);
    	System.out.println(war.activeWars);
    }
    
    public void removeWar(Integer munId) {
    	war.activeWars.remove(munId);
    	System.out.println(war.activeWars);
    }
    
    public ArrayList<Integer> getActiveWars() {
    	System.out.println(war.activeWars);
		return war.activeWars;
    }
    
    public void load(JsonElement elm){
        JsonObject obj = elm.getAsJsonObject();
        if(obj.has("activeWars")){
            JsonArray array = obj.get("activeWars").getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
				war.activeWars.add(elm.getAsInt());
			}
        }

    }

    public JsonElement save(){
        JsonObject obj = new JsonObject();
        if(!war.activeWars.isEmpty()){
            JsonArray array = new JsonArray();
            for (int i = 0; i < war.activeWars.size(); i++){
                 array.add(war.activeWars.get(i));
            }
            obj.add("activeWars", array);
        }
        return obj;
    }
}