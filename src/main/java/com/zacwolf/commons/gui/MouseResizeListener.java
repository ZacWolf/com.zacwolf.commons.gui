/* com.zacwolf.commons.gui.MouseResizeListener.java
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

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class MouseResizeListener implements MouseListener, MouseMotionListener {
    private final					JComponent	target;
    private					Point		drag_start;
    private					Dimension	start_size;
    private					JFrame		frame;
	public static final		int			MOUSEBUTTONUP	=	1;
	public static final 	int			MOUSEBUTTONDOWN	=	2;
	public	 	 		 	int			MOUSEBUTTONSTATE=	0;

    public MouseResizeListener(final JComponent target) {
        		this.target 	=	target;

    }

    public JFrame getFrame(){
    	return getFrame(target);
    }

    private JFrame getFrame(final Container target) {
    	if (frame==null){
	        if(target instanceof JFrame) {
	            return (JFrame)target;
	        }
	        return getFrame(target.getParent());
    	}
    	return frame;
    }

    @Override
	public void mouseClicked(final MouseEvent e) {}

    @Override
	public void mouseEntered(final MouseEvent e) {
    			frame	=	getFrame();
    			frame.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
    }

    @Override
	public void mouseExited(final MouseEvent e) {
    			frame.setCursor(Cursor.getDefaultCursor());
    }

    @Override
	public void mousePressed(final MouseEvent e) {
    	MOUSEBUTTONSTATE	=	MOUSEBUTTONDOWN;
        		drag_start	= this.getScreenLocation(e);
        		start_size	= frame.getSize();
    }

    @Override
	public void mouseReleased(final MouseEvent e) {
    	MOUSEBUTTONSTATE	=	MOUSEBUTTONUP;
    }

    @Override
	public void mouseDragged(final MouseEvent e) {
        final Point	current			=	this.getScreenLocation(e);
        final int		offsetWidth		=	(int)(drag_start.getX()-current.getX())*-1;
        final int		offsetHeight	=	(int)(drag_start.getY()-current.getY())*-1;
				frame.setSize(new Dimension(start_size.width+offsetWidth,start_size.height+offsetHeight));
				frame.validate();//getRootPane().repaint(); //getRootPane().repaint();
    }
    @Override
	public void mouseMoved(final MouseEvent e) {}

    private Point getScreenLocation(final MouseEvent e) {
        final Point	cursor			=	e.getPoint();
        final Point	target_location =	target.getLocationOnScreen();
        return new Point((int)(target_location.getX()+cursor.getX()),(int)(target_location.getY()+cursor.getY()));
    }
}