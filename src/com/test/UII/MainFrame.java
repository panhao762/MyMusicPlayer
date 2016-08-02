package com.test.UII;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTree;

import com.test.search.SongLibraryMap;
import com.test.song.SongInfos;
import com.test.UII.PlayListPanel;
import com.test.UII.PlayPanel;
import com.test.UII.SearchPanel;
import com.test.UII.ShowPanel;
import com.test.player.HigherPlayer;
import com.test.ui.tool.ButtonToolBar;
import com.test.ui.tool.IconButton;

public class MainFrame extends JFrame {
	
	private final int InitialWidth = 975;
	private final int InitialHeight = 670;
	private final Point InitialPoint;

	private final int ChangedWidth = 365;
	
	
	//public final Point InnitialPoint;
	
	private PlayPanel playPanel=null;
	private PlayListPanel playListPanel=null;
	private SearchPanel searchPanel=null;
	private ShowPanel showPanel=null;
	private ButtonToolBar toolBar=null;
	
	private SongLibraryMap<String, List<SongInfos>> songLibrary;
	private final static String savaPath = "E:/Hub/download";
	
	private IconButton jb1=null;
	
	public MainFrame()
	{
		
		
		Dimension dime= Toolkit.getDefaultToolkit().getScreenSize();
		InitialPoint= new Point((dime.width - InitialWidth) / 2,(dime.height - InitialHeight) / 2);
		
		this.setTitle("MyMusicPlayer");
		this.setSize(InitialWidth, InitialHeight);
		this.setLocation(InitialPoint);
		//在这里添加其他控制面板或者按钮
		//jb1=new IconButton("上一首", "icon/back.png");
		//this.add(jb1);
		
		//保存下载歌曲文件夹
		File savefolder = new File(savaPath);
		//创建已经下载歌曲的存储文件夹
		if(!savefolder.exists())
		{
			savefolder.mkdirs();
		}
		
		//读取歌曲库
		readSongLibrary();
		
		//建立嵌入的面板
		buildPanel();
		
		
		this.setVisible(true);
		requestFocus();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	private void buildPanel()
	{
		playPanel = new PlayPanel();
		
		//工具栏的创建：the left of the Frame
		toolBar= new ButtonToolBar();
		
		JButton[] toolBarButtons = new JButton[]{
				new IconButton("本地列表", "icon/note.png"),
				new IconButton("网络收藏", "icon/clouds.png"),
				new IconButton("我的下载","icon/download.png"),
				new IconButton("更多","icon/app.png")
		};
		
		toolBar.addButtons(toolBarButtons);
		
		playListPanel = new PlayListPanel(toolBar.getButtons(), this);
		
		searchPanel = new SearchPanel();
		
		showPanel = new ShowPanel();
		
		//传递给各个面板的属性
		JTree[] listTree = playListPanel.deliverTree();
		
		HigherPlayer player =playPanel.getHigherPlayer();
		
		//沟通播放面板和歌曲列表面板
		playPanel.setTrees(listTree);
		player.setJTree(listTree[0]);
		playListPanel.deliverHigherPlayer(player);
		
		//沟通乐库面板和歌曲列表面板
		showPanel.setListTree(listTree);
		// 沟通搜索面板与展示面板
		searchPanel.setShowPanel(showPanel);
		
		// 沟通搜索面板与本地
		searchPanel.setSongLibraryMap(songLibrary);
		// 沟通播放面板与歌词面板
		playPanel.setLrcPanelTextArea(showPanel.getTextArea());

		// 沟通乐库面板与播放面板
		showPanel.setPlayer(player);

		playPanel.setParentFrame(this);
		
		playPanel.setPreferredSize(new Dimension(350, 115));
		playListPanel.setPreferredSize(new Dimension(305, 520));
		toolBar.setPreferredSize(new Dimension(47, 520));
		searchPanel.setPreferredSize(new Dimension(610, 115));
		showPanel.setPreferredSize(new Dimension(610, 520));
		
		buildLayout();
		
	}
	
	private void setAction() {

		// 设置最大化事件 即展开窗体
		this.addWindowStateListener(event -> {
			if (event.getNewState() == JFrame.MAXIMIZED_BOTH) {
				searchPanel.setVisible(true);
				showPanel.setVisible(true);
				setSize(InitialWidth, InitialHeight);
				setLocation(InitialPoint);
				setVisible(true);

			}
		});

		// 折叠窗体
		searchPanel.getCollapseButton().addActionListener(event -> {
			searchPanel.setVisible(false);
			showPanel.setVisible(false);
			setSize(ChangedWidth, InitialHeight);
			setVisible(true);
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				saveSongLibrary();

				System.exit(0);
			}
		});
	}
	
	private void buildLayout() {
		Box topBox = Box.createHorizontalBox();

		topBox.add(playPanel);
		topBox.add(searchPanel);

		Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(toolBar);
		bottomBox.add(playListPanel);
		bottomBox.add(showPanel);

		Container mainPanel = getContentPane();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		mainPanel.add(topBox);
		mainPanel.add(bottomBox);
		mainPanel.add(Box.createVerticalStrut(15));
	}

	@SuppressWarnings({ "unchecked" })
	private void readSongLibrary() {

		File library = new File("E:/Hub/SongLibrary.dat");
		if (!library.exists())
			songLibrary = new SongLibraryMap<String, List<SongInfos>>();
		else {

			try {
				ObjectInputStream inputStream = new ObjectInputStream(
						new FileInputStream(library));
				songLibrary = (SongLibraryMap<String, List<SongInfos>>) inputStream
						.readObject();

				inputStream.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveSongLibrary() {

		if (songLibrary != null) {

			try {
				ObjectOutputStream outputStream = new ObjectOutputStream(
						new FileOutputStream(new File("E:/Hub/SongLibrary.dat")));
				outputStream.writeObject(songLibrary);
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
