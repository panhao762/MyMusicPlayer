package com.test.ui.tool;

import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class ButtonToolBar extends JToolBar{
	
	private JButton[] buttons;
	
	public ButtonToolBar()
	{
		this.setBorderPainted(false);
		
		this.setMargin(new Insets(0,5,5,5));
		this.setFloatable(false);
		this.setOpaque(false);
		this.setBorderPainted(false);
		
	}
	
	public ButtonToolBar(int orentation,int buttonNum)
	{
		this();
		
		this.setOrientation(orentation);
		if(orentation==JToolBar.VERTICAL)
		{
			this.setLayout(new GridLayout(buttonNum,1));
		}else
		{
			this.setLayout(new GridLayout(1,buttonNum));
		}
	}
	
	public void addButtons(JButton[] buttons)
	{
		this.buttons=buttons;
		
		for(int i=0;i<buttons.length;i++)
		{
			this.add(buttons[i]);
		}
	}
	
	public JButton[] getButtons()
	{
		return this.buttons;
	}

}
