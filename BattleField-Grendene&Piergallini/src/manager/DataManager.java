package manager;

import java.util.ArrayList;

import ia.battle.core.FieldCell;

public class DataManager {
	
	private static DataManager instance;
	private int rangoHunter;
	private ArrayList<FieldCell> specialItems;
	
	
	private DataManager(){
		specialItems = new ArrayList<>();
		rangoHunter = 10;
	}
	
	public static DataManager getInstance(){
		if(instance==null)
			instance = new DataManager();
		
		return instance;
	}

	public ArrayList<FieldCell> getSpecialItems() {
		return specialItems;
	}

	public void addSpecialItems(FieldCell specialItem) {
		if(!findspecialItem(specialItem))
			this.specialItems.add(specialItem);
	}
	
	public void deleteSpecialItems(FieldCell specialItem) {
		
		this.specialItems.remove(specialItem);
	
	}
	
	private Boolean findspecialItem(FieldCell specialItem){
		
		Boolean check = false;
		
		for (FieldCell fieldCell : this.specialItems) 
			if(specialItem.equals(fieldCell))
				check = true;
		
		return check;
		
	}

	public int getRangoHunter() {
		return rangoHunter;
	}

	public void setRangoHunter(int rangoHunter) {
		this.rangoHunter = rangoHunter;
	}

}
