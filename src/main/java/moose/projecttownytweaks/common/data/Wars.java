package moose.projecttownytweaks.common.data;

import java.util.ArrayList;

public class Wars {

	private static Wars instance = new Wars();
	public ArrayList<Integer> activeWars;
	
	public static Wars getInstance() {
		return instance;
	}
	
	private Wars() {
		activeWars = new ArrayList<>();
	}
}
