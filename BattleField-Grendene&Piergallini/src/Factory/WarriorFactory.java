/*
	Solo hago un factory para poder usar patrones
	como a ezequiel tanto le gustaban..


*/
package Factory;
import ia.battle.core.Warrior;
import ia.exceptions.RuleException;
import warriors.WanchopeAbila;

public class WarriorFactory {
	
	public static Warrior crearWanchopeAbila(int health, int defense, int strength, int speed, int range) throws RuleException{
		String name = "Wanchope Ábila";
		Warrior w = new WanchopeAbila(name, health, defense, strength, speed, range);
		return w;
	}

}
