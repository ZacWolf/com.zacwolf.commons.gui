/* com.zacwolf.commons.gui.JSplashScreen.java
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

/**
 * A JSplashScreen for use when starting applications. The splash screen contains
 * a title, and image and a status bar. The status bar can display messages
 * as well as the percentage of completion using a progress bar.
 */

public class JSplashScreen extends JWindow{
final	static	private	long			serialVersionUID	=	9002937329129913659L;
final			private JProgressBar	progress;

    /**
     * Create a new JSplashScreen
     * @param coolPicture The image to display in the splash screen
     * @param initialMessage The initial message to display in the progress area
     * @param title The title of the splash screen window, may be null
     */
    public JSplashScreen(final Icon coolPicture, final String initialMessage, final String title){
        // Create a JPanel so we can use a BevelBorder
final	JPanel	panelForBorder	=	new JPanel(new BorderLayout());
		        panelForBorder.setBackground(Color.white);
		        panelForBorder.setLayout(new BorderLayout());
		        panelForBorder.add(new JLabel(coolPicture),BorderLayout.CENTER);
        if (title != null) {
        		panelForBorder.add(new JLabel(title,JLabel.CENTER),BorderLayout.NORTH);
        }
        progress				=	new JProgressBar(0,100);
        progress.setStringPainted(true);
        progress.setString(initialMessage);

        panelForBorder.add(progress,BorderLayout.SOUTH);
        panelForBorder.setBorder(new BevelBorder(BevelBorder.RAISED));

        getContentPane().add(panelForBorder);
    }
    /**
     * Show or hide the splash screen.
     * @param show True to display the splash screen
     */
    @Override
	public void setVisible(final boolean show){
        if (show){
            pack();
            // Plonk it on center of screen
final	Dimension	WindowSize		=	getSize(),
					ScreenSize		=	Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((ScreenSize.width-WindowSize.width)/2,(ScreenSize.height-WindowSize.height)/2,WindowSize.width,WindowSize.height);
        }
        super.setVisible(show);
    }
    /**
     * Updates the status bar. This method is thread safe and can be called from
     * any thread.
     * @param message The message to display
     * @param percent The percentage towards completion
     */
    public void showStatus(final String message, final int percent){
        if (isVisible()){
            SwingUtilities.invokeLater(new UpdateStatus(message,percent));
        }
    }
    /**
     * Close the splash screen and free any resources associated with it.
     * This method is thread safe and can be called from any thread.
     */
    public void close(){
        if (isVisible()){
            SwingUtilities.invokeLater(new CloseSplashScreen());
        }
    }

    private class UpdateStatus implements Runnable{
final	private	String	message;
final	private	int		value;

        public UpdateStatus(final String status, final int pc){
            message = status;
            value = pc;
        }

        @Override
		public void run(){
            progress.setValue(value);
            progress.setString(message);
        }
    }

    private class CloseSplashScreen implements Runnable{
        @Override
		public void run(){
            setVisible(false);
            dispose();
        }
    }
}