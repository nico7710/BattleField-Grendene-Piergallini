package Factory;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.Warrior;
import ia.exceptions.RuleException;
import warriors.Kamikaze;
import warriors.WanchopeAbila;

public class WarriorFactory {
	

	
	public static Warrior crearWanchopeAbila() throws RuleException{
		
		String name = "Wanchope Ábila";
		
		int range, health, defense, strength, speed;
		
		int maxPointsPerWarrior = ConfigurationManager.getInstance().getMaxPointsPerWarrior();
		int maxRangeForWarrior = ConfigurationManager.getInstance().getMaxRangeForWarrior();
		int total = 0;

		if(maxPointsPerWarrior/5<maxRangeForWarrior)
			range = maxPointsPerWarrior/5;
		else
			range = maxRangeForWarrior;
				
		health = maxPointsPerWarrior/5;
		defense= maxPointsPerWarrior/5; 
		speed= maxPointsPerWarrior/5;
		
		total+=range;
		total+=health;
		total+=defense;
		total+=speed;

		strength= maxPointsPerWarrior-total;
		
		Warrior w = new WanchopeAbila(name, health, defense, strength, speed, range);
		return w;
	}
	
	public static Warrior crearKamikaze() throws RuleException{
		
		String name = "Kamikaze";
		
		int range, health, defense, strength, speed;
		
		int maxPointsPerWarrior = ConfigurationManager.getInstance().getMaxPointsPerWarrior();
		int total = 0;

		range = 2;
		health = 30;
		defense= 1; 
		strength= 1;
		total+=range;
		total+=health;
		total+=defense;
		total+=strength;
		
		speed= maxPointsPerWarrior-total;

		
		Warrior w = new Kamikaze(name, health, defense, strength, speed, range);
		return w;
	}

}
