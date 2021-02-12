package com.zacwolf.commons.gui.statusbar;

import java.awt.*;

import javax.swing.*;

public class JStatusBarSeperator extends JPanel{

	private	Color	leftColor;
	private	Color	rightColor;
	
	private	int		offset			=	80; // Color offset
	
	public JStatusBarSeperator(Color bar) {
	int 	red				=	java.awt.SystemColor.control.getRed();
	int 	green			=	java.awt.SystemColor.control.getGreen();
	int 	blue			=	java.awt.SystemColor.control.getBlue();
			this.leftColor	=	new Color((red-offset<0?0:red-offset), (green-offset<0?0:green-offset), (blue-offset<0?0:blue-offset));
			this.rightColor	=	new Color((red+offset>255?255:red+offset), (green+offset>255?255:green+offset), (blue+offset>255?255:blue+offset));
			setOpaque(false);
			setPreferredSize(new Dimension(2,25));
	}

	protected void paintComponent(Graphics g){
		g.setColor(leftColor);
		g.drawLine(0,2,0,getHeight()-2);
		g.setColor(rightColor);
		g.drawLine(1,2,1,getHeight()-2);
	}
}
