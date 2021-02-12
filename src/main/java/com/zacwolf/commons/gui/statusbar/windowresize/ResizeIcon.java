package com.zacwolf.commons.gui.statusbar.windowresize;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ResizeIcon extends JPanel {
	
    //Dimensions
    private int				WIDTH		=	18;
    private int 			HEIGHT		=	25;
    private boolean			isdragging	=	false;
    
	public ResizeIcon(){
		super();
		MouseResizeListener	mrl	=	new MouseResizeListener(this);
		super.addMouseListener(mrl);
		super.addMouseMotionListener(mrl);
	}
	
    public int getIconHeight() {
        return HEIGHT;
    }

    public int getIconWidth() {
        return WIDTH;
    }
    
    public void setIconHeight(int height) {
        this.HEIGHT=height;
    }

    public void setIconWidth(int width) {
        this.WIDTH=width;
    }
    
    public boolean isResizing(){
    	return isdragging;
    }
	
	public class MouseResizeListener implements MouseListener, MouseMotionListener {
	    private					JComponent	target;
	    private					Point		drag_start;
	    private					Dimension	start_size;
	    private					JFrame		frame;
		
	    public MouseResizeListener(JComponent target) {
	        		this.target 	=	target;
	        		
	    }

	    public JFrame getFrame(){
	    	return getFrame(this.target);
	    }
	    
	    private JFrame getFrame(Container target) {
	    	if (this.frame==null){
		        if(target instanceof JFrame) {
		            return (JFrame)target;
		        }
		        return getFrame(target.getParent());
	    	}
	    	return this.frame;
	    }
		
	    public void mouseClicked(MouseEvent e) {}
	    
	    public void mouseEntered(MouseEvent e) {
	    	getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
	    }
	    
	    public void mouseExited(MouseEvent e) {
	    	getFrame().setCursor(Cursor.getDefaultCursor());
	    }
	    
	    public void mousePressed(MouseEvent e) {
	        		this.drag_start	= this.getScreenLocation(e);
	        		this.start_size	= getFrame().getSize();
	    }

	    public void mouseReleased(MouseEvent e) {
	    			isdragging		=	false;
	    }

	    public void mouseDragged(MouseEvent e) {
	    			isdragging		=	true;
	        Point	current			=	this.getScreenLocation(e);
	        int		offsetWidth		=	(int)(this.drag_start.getX()-current.getX())*-1;
	        int		offsetHeight	=	(int)(this.drag_start.getY()-current.getY())*-1;
	        getFrame().setSize(new Dimension(this.start_size.width+offsetWidth,this.start_size.height+offsetHeight));
	        getFrame().validate();//getRootPane().repaint(); //getRootPane().repaint();
	    }
	    public void mouseMoved(MouseEvent e) {}
	    
	    private Point getScreenLocation(MouseEvent e) {
	        Point	cursor			=	e.getPoint();
	        Point	target_location =	this.target.getLocationOnScreen();
	        return new Point((int)(target_location.getX()+cursor.getX()),(int)(target_location.getY()+cursor.getY()));
	    }
	}
	
}

