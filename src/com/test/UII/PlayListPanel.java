package com.test.UII;

import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import com.test.player.HigherPlayer;
import com.test.ui.tool.AppPanel;
import com.test.ui.tool.SongListPanel;

public class PlayListPanel extends JPanel {
	private SongListPanel songListPanel;
	private SongListPanel cloudsPanel;
	private SongListPanel downloadPanel;
	private JScrollPane appPanel;

	private JButton[] buttons;
	private JFrame parent;

	private CardLayout card;
	
	public PlayListPanel(JButton[] buttons, JFrame parent) {

		setOpaque(false);

		card = new CardLayout();
		setLayout(card);

		this.buttons = buttons;
		this.parent = parent;

		initPanel();
	}
	
	private void initPanel() {
		songListPanel = new SongListPanel("Ĭ���б�");
		songListPanel.addPopupMenuToTree();

		cloudsPanel = new SongListPanel("Ĭ���б�", "��ϲ��");
		cloudsPanel.addPopupMenuToTree();

		downloadPanel = new SongListPanel("������", "������");

		appPanel = new JScrollPane(new AppPanel(parent));

		add(songListPanel, "0");
		add(cloudsPanel, "1");
		add(downloadPanel, "2");
		add(appPanel, "3");

		setAction();
	}
	
	private void setAction() {

		for (int i = 0; i < buttons.length; i++) {
			int k = i;
			buttons[i].addActionListener(event -> {
				card.show(this, "" + k);
			});
		}

	}
	
	public void deliverHigherPlayer(HigherPlayer HigherPlayer) {
		songListPanel.setPlayer(HigherPlayer);
		cloudsPanel.setPlayer(HigherPlayer);
		downloadPanel.setPlayer(HigherPlayer);
	}
	
	public JTree[] deliverTree() {
		return new JTree[] { songListPanel.getTree(), cloudsPanel.getTree(),
				downloadPanel.getTree() };
	}
	
}