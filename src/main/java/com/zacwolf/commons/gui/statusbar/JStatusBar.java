package com.zacwolf.commons.gui.statusbar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.zacwolf.commons.gui.SwingWorker;
import com.zacwolf.commons.gui.statusbar.windowresize.ResizeIcon;
import com.zacwolf.commons.gui.statusbar.windowresize.TriangleSquareWindowsCornerIcon;


/**
 * A status bar typically displayed at the bottom of an application window.
 * The status bar has an area for text messages.
 *
 * Areas can be also added to either the left or right sides that may contain:
 * Application specific status areas
 * A progress bar which displays the progress of extended operations,
 * A "stop" button that can be used to abort certain prolonged actions.
 */
public class JStatusBar extends JPanel implements WindowStateListener {
final	static	private	long 		serialVersionUID	=	-2952140141820449011L;
final			private JLabel		statusMsg			=	new JLabel();

				private final	int			height				=	25; //height in pixels
				private	ResizeIcon	resizeIcon			=	null;

				private	SwingWorker	fader				=	null;
				private final int			fadesteps			=	10;

final	static	public	int			CORNERICON_DOTS		=	1;
final	static	public	int			CORNERICON_LINES	=	2;

	public JStatusBar(final JFrame parent){
		this(parent, CORNERICON_DOTS);
	}

	public JStatusBar(final JFrame parent, final int resizeicontype) {
		initialize(resizeicontype);
	}

	private void initialize(final int icontype){
		if (icontype==CORNERICON_LINES){
			//TODO add lines resizer
		} else {
			resizeIcon	=	new TriangleSquareWindowsCornerIcon(height);
		}
		super.setLayout(new StatusLayout());
		super.setPreferredSize(new Dimension(1, height));
		super.add(statusMsg);
		super.add(resizeIcon);
	}

	public void hideResizeIcon(){
		resizeIcon.setVisible(false);
	}

	public void showResizeIcon(){
		resizeIcon.setVisible(true);
	}

	public ResizeIcon getResizeIcon(){
		return resizeIcon;
	}

	@Override
	public Container getParent(){
		return super.getParent();
	}

	@Override
	protected void paintComponent(final Graphics g) {
				super.paintComponent(g);
final	int 	red			=	java.awt.SystemColor.control.getRed();
final	int 	green		=	java.awt.SystemColor.control.getGreen();
final	int 	blue		=	java.awt.SystemColor.control.getBlue();
		int		y			=	0;
		int 	m			=	80;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y++;
		        m		=	60;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y++;
		        m		=	40;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y++;
		        m		=	20;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y		=	getHeight() - 5;
		        m		=	10;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y		=	getHeight() - 4;
		        m		=	20;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y		=	getHeight() - 3;
		        m		=	30;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y		=	getHeight() - 2;
		        m		=	40;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);

		        y		=	getHeight() - 1;
		        m		=	50;
		        g.setColor(new Color(red-m<0?0:red-m, green-100<0?0:green-m, blue-100<0?0:blue-m));
		        g.drawLine(0, y, getWidth(), y);
	}

final
   /**
    * Set the message to display in the status bar.
    * This message is thread safe and can be called from any thread.
    * @param message The message to display, or null to clear the message
    */
	public void setMessage(final String message){
		setMessage(message,0,new Color(0,0,0));
	}

final
   /**
    * Set the message to display in the status bar.
    * This message is thread safe and can be called from any thread.
    * @param message The message to display, or null to clear the message
    * @param millisTillClear Time in millisecond till the message is auto-cleared (fades out)
    */
   public void setMessage(final String message, final int millisTillClear){
	   setMessage(message,millisTillClear,new Color(0,0,0));
   }

final

	public void setMessage(final String message, final Color textColor){
	   setMessage(message,0,textColor);
   }

final

   public void setMessage(final String message, final int millisTillClear, final Color textColor){
	   if (!SwingUtilities.isEventDispatchThread()) {
final	Runnable	run		=	new Runnable(){
						            @Override
									public void run(){
						               setMessage(message,millisTillClear,textColor);
						            }
								};
			SwingUtilities.invokeLater(run);
	   } else {
		   		statusMsg.setForeground(textColor);
		   if (!statusMsg.getText().equals(message)){
			   if (fader!=null){
				   fader.interrupt();
				   while(!fader.getValue().equals("Done")){
					   try{
						   Thread.sleep(100);
					   } catch (final InterruptedException i){

					   } finally {
						   fader	=	null;
					   }
				   }
			   }
			   if (message == null){
				   statusMsg.setText(" ");
			   } else {
				   statusMsg.setText(message);
				   if(millisTillClear>0){
					   //new MessageFade(millisTillClear,this.statusMsg).start();
					   fader	= new FadeWorker(millisTillClear);
					   fader.start();
				   }
			   }
		   }
	   }
   }

	private class StatusLayout implements LayoutManager2, Comparator<Object>{

	   @Override
	   public void addLayoutComponent(final String name,final Component comp){ }

	   @Override
	   public void addLayoutComponent(final Component comp, final Object constraints){ }

	   @Override
	   public void removeLayoutComponent(final Component comp){ }

	   @Override
	   public void invalidateLayout(final Container target){ }

	   @Override
	   public float getLayoutAlignmentX(final Container target){
		   return 0.5f;
	   }

	   @Override
	   public float getLayoutAlignmentY(final Container target){
		   return 0.5f;
	   }

	   @Override
	   public void layoutContainer(final Container parent){
final	Insets		insets		=	parent.getInsets();
final	Dimension	size		=	parent.getSize();
				/*
				 * We need to sort the elements based on their XAlignment
				 * Components with XAlignment < .5 go final to the left of the label
				 * Components with XAlignment >= .5 go to teh right of the label
				 * The label takes all remaining space
				 */
final	Component[]	children	=	parent.getComponents();
         			Arrays.sort(children,this);
		int			x1			=	insets.left;
			for (final Component c : children) {
				if (c == statusMsg) {
					continue;
				}
final	float		align		=	c.getAlignmentX();
				if (align >= .5) {
					break;
				}
final	Dimension	s			=	c.getPreferredSize();
            		s.height	=	size.height-insets.top-insets.bottom;
            		c.setSize(s);
            		c.setLocation(x1,insets.top);
            		x1			+=	s.width;
			}
		int			x2			=	size.width - insets.right;
			for (int i=children.length; i-->0; ){
final	Component	c			=	children[i];
				if (c == statusMsg) {
					continue;
				}
final	float		align		=	c.getAlignmentX();
				if (align < .5) {
					break;
				}
final	Dimension	s			=	c.getPreferredSize();
            		s.height 	= size.height-insets.top-insets.bottom;
            		c.setSize(s);
            		x2			-=	s.width;
            		c.setLocation(x2,insets.top);
			}
final	Dimension	s			= new Dimension(x2-x1,size.height-insets.top-insets.bottom);
         			statusMsg.setSize(s);
         			statusMsg.setLocation(x1,insets.top);
	}

		@Override
		public Dimension maximumLayoutSize(final Container target){
final	Insets		insets	=	target.getInsets();
		int			width	=	insets.left + insets.right;
		int			height	=	Integer.MAX_VALUE;
final	int			count	=	target.getComponentCount();
		for (int i=0; i<count; i++){
final	Component	c		=	target.getComponent(i);
			if (!c.isVisible()) {
				continue;
			}
final	Dimension	size	=	c.getMaximumSize();
					width	+=	size.width;
					height	=	Math.min(height,size.height);
			}
					height	+=	insets.top + insets.bottom;
					return new Dimension(width, height);
		}

		@Override
		public Dimension minimumLayoutSize(final Container parent){
final	Insets		insets	=	parent.getInsets();
		int			width	=	insets.left + insets.right;
		int			height	=	0;
final	int			count	=	parent.getComponentCount();
			for (int i=0; i<count; i++){
final	Component	c		=	parent.getComponent(i);
				if (!c.isVisible()) {
					continue;
				}
final	Dimension	size	=	c.getMinimumSize();
            		width	+=	size.width;
            		height	=	Math.max(height,size.height);
			}			height	+=	insets.top + insets.bottom;
			return new Dimension(width, height);
		}

		@Override
		public Dimension preferredLayoutSize(final Container parent){
final	Insets		insets		=	parent.getInsets();
		int			width		=	insets.left + insets.right;
		int			height		=	0;
		final int			count		=	parent.getComponentCount();
			for (int i=0; i<count; i++){
final	Component	c			=	parent.getComponent(i);
				if (!c.isVisible()) {
					continue;
				}
final	Dimension	size		=	c.getPreferredSize();
            		width		+=	size.width;
            		height		=	Math.max(height,size.height);
			}			height		+=	insets.top + insets.bottom;
			return new Dimension(width, height);
		}

		@Override
		public int compare(final Object o1, final Object o2){
final	float		delta		= ((Component) o1).getAlignmentX() - ((Component) o2).getAlignmentX();
			if(delta == 0){
				return 0;
			} else if (delta > 0){
				return 1;
			}
			return -1;
		}
	}

	@Override
	public void windowStateChanged(final WindowEvent e){
		  if (e.getOldState() != Frame.MAXIMIZED_BOTH &&
			  e.getNewState() == Frame.MAXIMIZED_BOTH
		  	  ){
			  hideResizeIcon();
		  }

		  if (e.getOldState() == Frame.MAXIMIZED_BOTH &&
			  e.getNewState() != Frame.MAXIMIZED_BOTH
			  ){
			  showResizeIcon();
		  }
	}

	private class FadeWorker extends SwingWorker{

final	int		rStep =	(java.awt.SystemColor.control.getRed()-statusMsg.getForeground().getRed())/fadesteps;
final	int		gStep =	(java.awt.SystemColor.control.getGreen()-statusMsg.getForeground().getGreen())/fadesteps;
final	int		bStep =	(java.awt.SystemColor.control.getBlue()-statusMsg.getForeground().getBlue())/fadesteps;

		int		millisTillClear;

		FadeWorker(final int millisTillClear){
			this.millisTillClear	=	millisTillClear;
		}

		@Override
		public Object construct() {
			try {
				if (millisTillClear>0){
final	int		loops	=	millisTillClear/500;
				   for (int l=0;l<loops;l++){
					   if (Thread.interrupted()){
						   throw new InterruptedException();
					   }
					   Thread.sleep(500);
				   }
			   }
			   for(int s=0;s<fadesteps;s++){
				   if (Thread.interrupted()){
					   throw new InterruptedException();
				   }
				   Thread.sleep(1000/fadesteps);
				   setMessage(statusMsg.getText(),getNextColor());
			   }
		   } catch (final InterruptedException e){

		   }
		   return "Done";
		}
		@Override
		public void finished(){
			setMessage(null,new Color(0,0,0));
		}

		public Color getNextColor() {
			return new Color(statusMsg.getForeground().getRed()+rStep,
							 statusMsg.getForeground().getGreen()+gStep,
							 statusMsg.getForeground().getBlue()+bStep
				   			 );
		}
	}
}