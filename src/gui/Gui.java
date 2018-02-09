package autofighter.src.gui;

import autofighter.src.core.AutoFighter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JList;


@SuppressWarnings("serial")
public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField txtNpcName;
	private JTextField txtAreaRadius;
	private JTextField txtFoodName;
	private JTextField txtEatPercent;
	private JTextField txtLootItem;
	private AutoFighter af;

	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui(af);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui(final AutoFighter af) {
		this.af = af;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 438, 266);
		contentPane.add(tabbedPane);
		
		JPanel mainPanel = new JPanel();
		tabbedPane.addTab("Main", null, mainPanel, null);
		tabbedPane.setEnabledAt(0, true);
		mainPanel.setLayout(null);
		
		JLabel lblAutoFighter = new JLabel("Auto Fighter");
		lblAutoFighter.setBounds(129, 5, 158, 31);
		lblAutoFighter.setFont(new Font("Lucida Grande", Font.BOLD, 25));
		mainPanel.add(lblAutoFighter);
		
		JPanel textPanel = new JPanel();
		textPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		textPanel.setBounds(6, 40, 405, 140);
		mainPanel.add(textPanel);
		textPanel.setLayout(null);
		
		JLabel lblThanksForUsing = new JLabel("<html>Thanks for using Auto Fighter by Novak!  Please visit the<br>\nthread on OSBot for the latest news and a description <br>\nof how to set up the bot correctly.  If you find a bug, please <br>\nmake a DETAILED post on where you were and what your <br>\nsettings are.  As always, Happy Botting!<br>\n<br>\nSincerely,<br>\nNovak</html>");
		lblThanksForUsing.setVerticalAlignment(SwingConstants.TOP);
		lblThanksForUsing.setBounds(6, 7, 393, 128);
		textPanel.add(lblThanksForUsing);
		
		JPanel fightPanel = new JPanel();
		tabbedPane.addTab("Fight", null, fightPanel, null);
		tabbedPane.setEnabledAt(1, true);
		fightPanel.setLayout(null);
		
		JLabel lblFightSettings = new JLabel("Fight Settings");
		lblFightSettings.setBounds(152, 5, 113, 20);
		lblFightSettings.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		fightPanel.add(lblFightSettings);
		
		JLabel lblNpcName = new JLabel("Exact NPC Name:");
		lblNpcName.setBounds(6, 42, 113, 16);
		fightPanel.add(lblNpcName);
		
		txtNpcName = new JTextField();
		txtNpcName.setText("NPC NAME");
		txtNpcName.setBounds(131, 37, 134, 28);
		fightPanel.add(txtNpcName);
		txtNpcName.setColumns(10);
		
		JLabel lblCuttingAreaRadius = new JLabel("Fighting Area Radius: ");
		lblCuttingAreaRadius.setBounds(6, 95, 146, 16);
		fightPanel.add(lblCuttingAreaRadius);
		
		txtAreaRadius = new JTextField();
		txtAreaRadius.setBounds(164, 89, 134, 28);
		fightPanel.add(txtAreaRadius);
		txtAreaRadius.setColumns(10);
		
		JPanel eatPanel = new JPanel();
		tabbedPane.addTab("Eat", null, eatPanel, null);
		tabbedPane.setEnabledAt(2, true);
		eatPanel.setLayout(null);
		
		JLabel lblEatSettings = new JLabel("Eat Settings");
		lblEatSettings.setBounds(160, 5, 96, 20);
		lblEatSettings.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		eatPanel.add(lblEatSettings);
		
		JLabel lblUseFood = new JLabel("Use Food: ");
		lblUseFood.setBounds(6, 42, 66, 16);
		eatPanel.add(lblUseFood);
		
		final JCheckBox eatCheckBox = new JCheckBox("");
		eatCheckBox.setBounds(68, 38, 28, 23);
		eatPanel.add(eatCheckBox);
		
		JLabel lblExactFoodName = new JLabel("Exact Food Name:");
		lblExactFoodName.setBounds(6, 83, 118, 16);
		eatPanel.add(lblExactFoodName);
		
		txtFoodName = new JTextField();
		txtFoodName.setText("FOOD NAME");
		txtFoodName.setBounds(136, 77, 134, 28);
		eatPanel.add(txtFoodName);
		txtFoodName.setColumns(10);
		
		JLabel lblEatAt = new JLabel("Percent to Eat:");
		lblEatAt.setBounds(6, 132, 90, 16);
		eatPanel.add(lblEatAt);
		
		txtEatPercent = new JTextField();
		txtEatPercent.setBounds(108, 126, 134, 28);
		eatPanel.add(txtEatPercent);
		txtEatPercent.setColumns(10);
		
		JPanel lootPanel = new JPanel();
		tabbedPane.addTab("Loot", null, lootPanel, null);
		tabbedPane.setEnabledAt(3, true);
		lootPanel.setLayout(null);
		
		JLabel lblLootSettings = new JLabel("Loot Settings");
		lblLootSettings.setBounds(155, 5, 106, 20);
		lblLootSettings.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lootPanel.add(lblLootSettings);
		
		JList<String> list = new JList<String>();
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBounds(6, 38, 165, 176);
		final DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();
		list.setModel(listModel);
		lootPanel.add(list);
		
		JLabel lblItem = new JLabel("Item:");
		lblItem.setBounds(218, 91, 43, 16);
		lootPanel.add(lblItem);
		
		txtLootItem = new JTextField();
		txtLootItem.setBounds(277, 85, 134, 28);
		lootPanel.add(txtLootItem);
		txtLootItem.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtLootItem.getText() != null) {
					listModel.addElement(txtLootItem.getText().toString());
				}
			}
		});
		btnAdd.setBounds(243, 140, 117, 29);
		lootPanel.add(btnAdd);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				af.npcName = txtNpcName.getText().toString();
				af.fightAreaRadius = Integer.parseInt(txtAreaRadius.getText().toString());
				
				if(listModel.size() > 0)
					af.loot = true;
				for(int i = 0; i < listModel.size(); i++) {
					af.lootList.add(listModel.getElementAt(i));
				}
				if(eatCheckBox.isSelected()) {
					af.useFood = true;
					af.foodItem = txtFoodName.getText().toString();
					af.healthPercent = Integer.parseInt(txtEatPercent.getText().toString());
				}
				af.guiWait = false;
				dispose();
			}
		});
		startButton.setBounds(129, 185, 158, 29);
		mainPanel.add(startButton);
	}
}
