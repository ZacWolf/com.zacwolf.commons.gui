/* com.zacwolf.commons.gui._GUI.java
 *
 * Copyright (C) 2021-2021 Zac Morris <a href="mailto:zac@zacwolf.com">zac@zacwolf.com</a>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.zacwolf.commons.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.zacwolf.commons.gui.statusbar.JStatusBar;

/**
 *
 */
public class _GUI extends JFrame implements WindowFocusListener, WindowListener, WindowStateListener{
final	private static	long				serialVersionUID			=	233513413252690269L;

		private	static	_GUI				self						=	null;

final	private			GUIProperties		settings;
final	private 		List<Font>			installedFonts;

		private 		JPanel 				jContentPane				=	null;
		private 		JScrollPane 		jScrollPane 				=	null;
		private 		JTabbedPane 		jTabbedPane					=	null;
		private 		JPanel 				tab1JPanel					=	null;
		private 		JPanel 				tab1ContentJPanel			=	null;
		private 		JPanel 				tab2JPanel					=	null;

		private 		JStatusBar 			jStatusBar					=	null;
		private			JMenuBar			jJMenuBar					=	null;
		private			JMenu				jMenu						=	null;
		private			JMenuItem			jMenuItem					=	null;
		private 		JMenu 				jMenu1 						=	null;
		private 		JMenuItem 			jMenuItem1 					=	null;


		private 		JPanel 				jPanel 						=	null;
		private 		JPanel 				fontSettingsJPanel 			=	null;
		private			JPanel				fontSettingsJPanel_inner	=	null;
		private 		JComboBox<String> 	fontjComboBox 				=	null;
		private 		JTextField 			fontPreview 				=	null;
		private 		JButton 			selectFontButton 			=	null;

	/**
	 * @throws HeadlessException
	 */
	private _GUI(final String title) throws HeadlessException{
		installedFonts				=	Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
		setTitle(title);
		setSize(new java.awt.Dimension(640,480));
		settings					=	new GUIProperties(this);
		setJMenuBar(getJJMenuBar());
		setContentPane(getJContentPane());
		addWindowListener(this);
		addWindowFocusListener(this);
		addWindowStateListener(this);
	}



	public static _GUI getGUI() {
		if (self!=null) {
			return self;
		}
		try {

			self	=	new _GUI("Demo");
		} catch (final Exception e){
			System.err.println(e);
			System.exit(0);
		}
		return self;
	}

	public void setStatusBarMessage(final String msg){
		jStatusBar.setMessage(msg);
	}

	public void setStatusBarMessage(final String msg, final int fadeTimeInMillis){
		jStatusBar.setMessage(msg, fadeTimeInMillis);
	}

//LISTENERS
	@Override
	public void windowStateChanged(final WindowEvent e){

	}


	@Override
	public void windowGainedFocus(final WindowEvent e){
	    //System.out.print("High:windowGainedFocus ");
	    //System.out.println(((JFrame)e.getSource()).getTitle().substring(0,1));
	}

	  @Override
	public void windowLostFocus(final WindowEvent e){
	    //System.out.print("High:windowLostFocus ");
	    //System.out.println(((JFrame)e.getSource()).getTitle().substring(0,1));
	}

	@Override
	public void windowClosing(final WindowEvent evt) {
		doExit();
	}

	@Override
	public void windowActivated(final WindowEvent evt) {

	}

	@Override
	public void windowClosed(final WindowEvent evt) {

	}

	@Override
	public void windowDeactivated(final WindowEvent evt) {

	}

	@Override
	public void windowDeiconified(final WindowEvent evt) {

	}

	@Override
	public void windowIconified(final WindowEvent evt) {

	}

	@Override
	public void windowOpened(final WindowEvent evt) {

	}

	private JPanel getJContentPane() {
		if (jContentPane ==	null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
			//jContentPane.setPreferredSize(new java.awt.Dimension(BASEW+ALBUM_ART_SIZES[this.albumSize].width,BASEH+ALBUM_ART_SIZES[this.albumSize].height-jJMenuBar.getHeight()));
			jContentPane.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJStatusBar(), java.awt.BorderLayout.SOUTH);
			jContentPane.setOpaque(true);
		}
		return jContentPane;
	}

	private JScrollPane getJScrollPane() {
		if (jScrollPane ==	null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			jScrollPane.setViewportView(getJTabbedPane());
		}
		return jScrollPane;
	}

	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane ==	null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Now Playing", null, getTab1JPanel(), "Find out what\'s playing on your iTunes");
			jTabbedPane.addTab("Settings", null, getTab2JPanel(), "Find out about the creator of miTunes");
		}
		return jTabbedPane;
	}

	private JPanel getTab1JPanel() {
		if (tab1JPanel ==	null) {
			tab1JPanel = new JPanel();
			tab1JPanel.setLayout(new BorderLayout());
			tab1JPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			tab1JPanel.setBackground(java.awt.SystemColor.window);
			tab1JPanel.add(getTab1ContentJPanel());
		}
		return tab1JPanel;
	}

	private JPanel getTab1ContentJPanel() {
		if (tab1ContentJPanel ==	null) {
			tab1ContentJPanel = new JPanel();
			tab1ContentJPanel.setLayout(new BorderLayout());
			tab1ContentJPanel.setBackground(java.awt.SystemColor.window);
		}
		return tab1ContentJPanel;
	}


	/**
	 * This method initializes jJMenuBar
	 *
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar ==	null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu());
			jJMenuBar.add(getJMenu1());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu() {
		if (jMenu ==	null) {
			jMenu = new JMenu();
			jMenu.setText("File");
			jMenu.add(getJMenuItem());
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem ==	null) {
			jMenuItem =		new JMenuItem("Exit");
			jMenuItem.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(final ActionEvent evt) {
	            	doExit();
	            }
	        });
		}
		return jMenuItem;
	}

	/**
	 * This method initializes jMenu1
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu1() {
		if (jMenu1 ==	null) {
			jMenu1 = new JMenu();
			jMenu1.setText("Help");
			jMenu1.add(getJMenu1Item());
		}
		return jMenu1;
	}
	/**
	 * This method initializes jMenuItem1
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenu1Item() {
		if (jMenuItem1 ==	null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("About");
		}
		return jMenuItem1;
	}


	/**
	 * This method initializes jJToolBarBar
	 *
	 * @return javax.swing.JToolBar
	 */
	private JStatusBar getJStatusBar() {
		if (jStatusBar ==	null) {
			jStatusBar	= new JStatusBar(this);
			jStatusBar.setBackground(java.awt.SystemColor.control);
		}
		return jStatusBar;
	}

	private JPanel getTab2JPanel() {
		if (tab2JPanel ==	null) {
			tab2JPanel = new JPanel();
			tab2JPanel.setLayout(new BorderLayout());
			tab2JPanel.setBackground(java.awt.SystemColor.window);
			tab2JPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			tab2JPanel.add(getJPanel(), java.awt.BorderLayout.NORTH);
		}
		return tab2JPanel;
	}
	/**
	 * This method initializes jPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel ==	null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel(), BoxLayout.Y_AXIS));
			jPanel.setBackground(java.awt.SystemColor.window);
			jPanel.add(getFontSettingsJPanel(), null);
		}
		return jPanel;
	}
	/**
	 * This method initializes fontSettingsJPanel
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getFontSettingsJPanel() {
		if (fontSettingsJPanel ==	null) {
			fontSettingsJPanel = new JPanel();
			fontSettingsJPanel.setLayout(new BorderLayout());
			fontSettingsJPanel.setBackground(java.awt.SystemColor.window);
			fontSettingsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Now Playing Font", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			fontSettingsJPanel.add(getFontSettingsJPanel_inner(), java.awt.BorderLayout.WEST);
			fontSettingsJPanel.add(getFontPreview(), java.awt.BorderLayout.CENTER);
		}
		return fontSettingsJPanel;
	}

		/**
	 * This method initializes fontSettingsJPanel_inner
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getFontSettingsJPanel_inner() {
		if (fontSettingsJPanel_inner ==	null) {
			fontSettingsJPanel_inner = new JPanel();
			fontSettingsJPanel_inner.setLayout(new BorderLayout());
			fontSettingsJPanel_inner.setBackground(java.awt.SystemColor.window);
			fontSettingsJPanel_inner.add(getFontjComboBox(), BorderLayout.WEST);
			fontSettingsJPanel_inner.add(getSelectFontButton(), BorderLayout.CENTER);
		}
		return fontSettingsJPanel_inner;
	}
	/**
	 * This method initializes fontjComboBox
	 *
	 * @return javax.swing.JComboBox
	 */
	private JComboBox<String> getFontjComboBox() {
		if (fontjComboBox ==	null) {
final	String[]	installedFF		=	GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		int			selectedIndex	=	0;
			for(int f=0;f<installedFF.length;f++){
				if (installedFF[f].equals(settings.getProperty("ui.Font"))){
					selectedIndex	=	f;
					break;
				}
			}
					fontjComboBox	=	new JComboBox<String>(installedFF);
					fontjComboBox.setSelectedIndex(selectedIndex);
					fontjComboBox.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
					fontjComboBox.setPreferredSize(new Dimension(175,26));
					fontjComboBox.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(final ActionEvent ae) {
final	String		selectedFont	=	fontjComboBox.getSelectedItem().toString();
							 if (installedFonts.contains(new Font(selectedFont+" Bold",0, 1))){
								 updatePreviewFont(new Font(selectedFont+ " Bold", 0, 16));
							 } else if (installedFonts.contains(new Font(selectedFont,Font.BOLD, 1))){
								 updatePreviewFont(new Font(selectedFont, Font.BOLD, 16));
							 } else {
								 updatePreviewFont(new Font(selectedFont, 0, 16));
							 }
						}
					}
			);
		}
		return fontjComboBox;
	}
	/**
	 * This method initializes fontPreview
	 *
	 * @return javax.swing.JTextField
	 */
	private JTextField getFontPreview() {
		if (fontPreview ==	null) {
			fontPreview = new JTextField();
			fontPreview.setText("AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz.!@#$%^&*()-+");
			fontPreview.setBackground(java.awt.SystemColor.control);
			fontPreview.setFont(new Font(settings.getProperty("ui.Font"),Font.BOLD,16));
			fontPreview.setPreferredSize(new java.awt.Dimension(1,26));
		}
		return fontPreview;
	}
	/**
	 * This method initializes selectFontButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getSelectFontButton() {
		if (selectFontButton ==	null) {
			selectFontButton = new JButton();
			selectFontButton.setText("Select Font");
			selectFontButton.setPreferredSize(new java.awt.Dimension(100,26));
			selectFontButton.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(final ActionEvent ae) {
				final Font	selectedFont	=	fontPreview.getFont();
						settings.setProperty("ui.Font",selectedFont.getFamily());
					}
				}
			);
		}
		return selectFontButton;
	}




	private void updatePreviewFont(final Font font){
		fontPreview.setFont(font);
	}

	private void doExit() {
		settings.store();
		System.exit(0);
	}


	/**
	 * @param args
	 */
	public static void main(final String[] args){
		try {
/*
final	String		lcOSName		=	System.getProperty("os.name").toLowerCase();
final	String		MAC_OS_X	=	System.getProperty("system.os").toLowerCase().startsWith("mac os x");
final	String		rtv			=	System.getProperty("java.runtime.version");
					 // Special code for initial Java 1.4.2 release on Mac OS X
					 //	if (MAC_OS_X && rtv.startsWith("1.4.2")	&& rtv.endsWith("117.1"))
*/
			UIManager.put("PopupMenuUI","com.zacwolf.commons.gui.PopupMenuUI");
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());//UIManager.getSystemLookAndFeelClassName()
			Toolkit.getDefaultToolkit().setDynamicLayout(true);
			_GUI.getGUI().setVisible(true);
		} catch (final Exception e) {
			System.exit(ABORT);
		}

	}

}
