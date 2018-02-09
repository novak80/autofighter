package autofighter.src.nodes;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import autofighter.src.core.AutoFighter;
import dependencies.net.novakscripts.framework.Priority;
import dependencies.net.novakscripts.framework.regular.Node;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.utility.ConditionalSleep;



public class LootNode extends Node {

	private AutoFighter script;
	private List<String> lootList;
	private Queue<String> lootQueue = new LinkedList<String>();
	
	
	public LootNode(AutoFighter script, List<String> lootList) {
		this.script = script;
		this.lootList = lootList;
	}
	
	@Override
	public boolean validate() {
		addQueue();
		return lootQueue.size() > 0 && script.myPlayer().getInteracting() == null;// || !script.myPlayer().getInteracting().isUnderAttack();
	}

	@Override
	public void execute() {
		lootItems();
	}

	@Override
	public Priority priority() {
		return Priority.VERY_HIGH;
	}

	@Override
	public String getName() {
		return "Looting...";
	}
	
	private void addQueue() {
		for(String s : lootList) {
			GroundItem loot = script.groundItems.closest(s);
			if(loot != null && script.map.distance(loot) < 10 && script.map.canReach(loot))
				lootQueue.add(s);
		}
	}
	
	private void lootItems() {
		if(lootQueue.size() > 0) {
			for(int i = 0; i < lootQueue.toArray().length; i++) {
				final GroundItem loot = script.groundItems.closest(lootQueue.toArray()[i].toString());
				if(!script.inventory.isFull() && loot != null && loot.exists() && script.map.canReach(loot)) {
					if(loot.interact("Take"))
						new ConditionalSleep(3000) {
							@Override
							public boolean condition() throws InterruptedException {
								return !loot.exists();
							}
						}.sleep();
				}
			}
			lootQueue.clear();
		}
		
	}

}
