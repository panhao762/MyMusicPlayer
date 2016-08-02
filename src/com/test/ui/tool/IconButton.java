package com.test.ui.tool;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class IconButton extends JButton {
	
	public IconButton(String tip) {
		setToolTipText(tip);
		setBorderPainted(false);
		setOpaque(false);
		setContentAreaFilled(false);

	}
	
	public IconButton(String name,String url)
	{
		this(name);
		this.setIcon(new ImageIcon(url));	
	}
	
	public String toString() {
		return getToolTipText();
	}

}
