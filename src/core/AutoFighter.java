package autofighter.src.core;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;


import autofighter.src.gui.Gui;
import autofighter.src.nodes.EatNode;
import autofighter.src.nodes.FightNode;
import autofighter.src.nodes.LootNode;
import dependencies.net.novakscripts.framework.regular.Controller;
import dependencies.net.novakscripts.framework.regular.Node;
import dependencies.net.novakscripts.utils.Calculations;
import dependencies.net.novakscripts.utils.Painter;
import dependencies.net.novakscripts.utils.Timer;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;



@ScriptManifest(author = "Novak", info = "Fights For You!", logo = "", name = "Auto Fighter", version = 1.0D)
public class AutoFighter extends Script {

	
	private Painter painter;
	private Painter.PaintProperty time, node, exp;
	public boolean loot = false;
	public List<String> lootList = new ArrayList<String>();
	public String foodItem;
	public String npcName;
	public int healthPercent, fightAreaRadius, startAtkExp, startAtkLvl, startStrExp, startStrLvl, startDefExp, startDefLvl, startHpExp, startHpLvl;
	public boolean useFood = false;
	public boolean guiWait = true;
	
	public Area fightArea;
	
	private long startTime;
	private Controller controller;
	
	
	@Override
	public void onStart() throws InterruptedException {
		this.painter = new Painter(this.getName(), this.getVersion() + "", new Color(102, 204, 255), Color.WHITE);
		this.time = new Painter.PaintProperty();
		this.node = new Painter.PaintProperty();
		this.exp = new Painter.PaintProperty();
		Gui gui = new Gui(this);
		gui.start();
		while(guiWait) {
			sleep(50);
		}
		
		controller = new Controller(new FightNode(this, lootList));
		if(useFood)
			controller.addNodes(new EatNode(this, foodItem));
		if(lootList.size() > 0) 
			controller.addNodes(new LootNode(this, lootList));
		

		fightArea = new Area(myPlayer().getX() + fightAreaRadius, myPlayer().getY() + fightAreaRadius, myPlayer().getX() - fightAreaRadius, myPlayer().getY() - fightAreaRadius);
		
		startAtkExp = skills.getExperience(Skill.ATTACK);
		startAtkLvl = skills.getStatic(Skill.ATTACK);
		startStrExp = skills.getExperience(Skill.STRENGTH);
		startStrLvl = skills.getStatic(Skill.STRENGTH);
		startDefExp = skills.getExperience(Skill.DEFENCE);
		startDefLvl = skills.getStatic(Skill.DEFENCE);
		startHpExp = skills.getExperience(Skill.HITPOINTS);
		startHpLvl = skills.getStatic(Skill.HITPOINTS);
		
		startTime = System.currentTimeMillis();
		log("Loop Started");
	}
	
	
	@Override
	public int onLoop() throws InterruptedException {
		Node n = controller.getCurrentNode();
		if(n != null)
			n.execute();
		return 200;
	}
	
	@Override
	public void onExit() throws InterruptedException {
		
	}
	
	@Override
	public void onPaint(Graphics2D g) {
		
		int atkExpGained = skills.getExperience(Skill.ATTACK) - startAtkExp;
		int atkLvlsGained = skills.getStatic(Skill.ATTACK) - startAtkLvl;
		int strExpGained = skills.getExperience(Skill.STRENGTH) - startStrExp;
		int strLvlsGained = skills.getStatic(Skill.STRENGTH) - startStrLvl;
		int defExpGained = skills.getExperience(Skill.DEFENCE) - startDefExp;
		int defLvlsGained = skills.getStatic(Skill.DEFENCE) - startDefLvl;
		int hpExpGained = skills.getExperience(Skill.HITPOINTS) - startHpExp;
		int hpLvlsGained = skills.getStatic(Skill.HITPOINTS) - startHpLvl;
		
		
		painter.properties(
				time.value("Time Running: " + Timer.runTime(startTime)),
				node.value("Node: " + currentNode()),
				exp.value("Total Exp: " )
				).draw(g, this);
		
		Graphics2D atkpbar = (Graphics2D) g.create();
		Graphics2D strpbar = (Graphics2D) g.create();
		Graphics2D defpbar = (Graphics2D) g.create();
		Graphics2D hppbar = (Graphics2D) g.create();
		drawProgressBar(atkpbar, 5, 160, 180, 15, new Color(0, 0, 0), new Color(34, 86, 0), 75, percentToLevel(Skill.ATTACK));
		drawProgressBar(strpbar, 5, 175, 180, 15, new Color(0, 0, 0), new Color(163, 0, 0), 75, percentToLevel(Skill.STRENGTH));
		drawProgressBar(defpbar, 5, 190, 180, 15, new Color(0, 0, 0), new Color(0, 10, 155), 75, percentToLevel(Skill.DEFENCE));
		drawProgressBar(hppbar, 5, 205, 180, 15, new Color(0, 0, 0), new Color(209, 0, 191), 75, percentToLevel(Skill.HITPOINTS));
		

		
		
		g.setColor(Color.WHITE);
		
		
		g.drawString("Attack: " + atkExpGained + " (" + Calculations.getHourly(atkExpGained, Calculations.getRuntime(startTime)) + ") - " + painter.getTimeToLevel(this, Skill.ATTACK,skills.getStatic(Skill.ATTACK) + 1, Calculations.getHourly(atkExpGained, Calculations.getRuntime(startTime))), 8, 172);
		g.drawString("Strength: " + strExpGained + " (" + Calculations.getHourly(strExpGained, Calculations.getRuntime(startTime)) + ") - " + painter.getTimeToLevel(this, Skill.STRENGTH,skills.getStatic(Skill.STRENGTH) + 1, Calculations.getHourly(strExpGained, Calculations.getRuntime(startTime))), 8, 187);
		g.drawString("Defence: " + defExpGained + " (" + Calculations.getHourly(defExpGained, Calculations.getRuntime(startTime)) + ") - " + painter.getTimeToLevel(this, Skill.DEFENCE,skills.getStatic(Skill.DEFENCE) + 1, Calculations.getHourly(defExpGained, Calculations.getRuntime(startTime))), 8, 202);
		g.drawString("Hitpoints: " + hpExpGained + " (" + Calculations.getHourly(hpExpGained, Calculations.getRuntime(startTime)) + ") - " + painter.getTimeToLevel(this, Skill.HITPOINTS,skills.getStatic(Skill.HITPOINTS) + 1, Calculations.getHourly(hpExpGained, Calculations.getRuntime(startTime))), 8, 217);
				
		
	}
	
	private String currentNode() {
		if(controller.getCurrentNode() != null)
			return controller.getCurrentNode().getName();
		return "Waiting...";
	}
	
	private int percentToLevel(Skill skill) {
		int currExp = skills.getExperience(skill);
		int currLvlStartExp = skills.getExperienceForLevel(skills.getStatic(skill));
		int nextLvlExp = skills.getExperienceForLevel(skills.getStatic(skill) + 1);
		int dividend = currExp - currLvlStartExp;
		int divisor = nextLvlExp - currLvlStartExp;
		int prcnt = 100 * dividend / divisor;
		return prcnt;
	}
	
	private void drawProgressBar(Graphics2D g, final int x, final int y,
			final int width, final int height, final Color main,
			final Color progress, final int alpha, final int percentage) {
				g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
				final GradientPaint base = new GradientPaint(x, y, new Color(200, 200,200, alpha), x, y + height, main);
				final GradientPaint overlay = new GradientPaint(x, y, new Color(200,200, 200, alpha), x, y + height, progress);
				if (height > width) {
					g.setPaint(base);
					g.fillRect(x, y, width, height);
					g.setPaint(overlay);
					g.fillRect(x,y + (height - (int) (height * (percentage / 100.0D))),width, (int) (height * (percentage / 100.0D)));
				} else {
					g.setPaint(base);
					g.fillRect(x, y, width, height);
					g.setPaint(overlay);
					g.fillRect(x, y, (int) (width * (percentage / 100.0D)), height);
				}
				g.setColor(Color.BLACK);
				g.drawRect(x, y, width, height);
	}


}
