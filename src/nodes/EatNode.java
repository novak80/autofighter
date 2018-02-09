package autofighter.src.nodes;

import autofighter.src.core.AutoFighter;
import dependencies.net.novakscripts.framework.Priority;
import dependencies.net.novakscripts.framework.regular.Node;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;



public class EatNode extends Node {

	private AutoFighter script;
	private String food;
	
	public EatNode(AutoFighter script, String food) {
		this.script = script;
		this.food = food;
	}
	
	
	@Override
	public boolean validate() {
		return shouldEat() && script.useFood;
	}

	@Override
	public void execute() throws InterruptedException{
		if(shouldEat() && script.inventory.contains(food) && script.inventory.interact("Eat", food)) {
			MethodProvider.sleep(MethodProvider.random(2000, 3000));
		}
		
	}

	@Override
	public Priority priority() {
		return Priority.MAXIMUM;
	}

	@Override
	public String getName() {
		return "Eating...";
	}
	
	private boolean shouldEat() {
		double dynamic = script.skills.getDynamic(Skill.HITPOINTS);
		double base = script.skills.getStatic(Skill.HITPOINTS);
		float percent = (float) ((dynamic * 100)/base);
		return percent <= script.healthPercent;
	}

}
