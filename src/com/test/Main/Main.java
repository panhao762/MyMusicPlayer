package com.test.Main;

import java.awt.EventQueue;


import com.test.UII.MainFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new MainFrame();
		EventQueue.invokeLater(() -> {
			new MainFrame();
		});
	}

}
