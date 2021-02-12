/* com.zacwolf.commons.gui.JVectorButton.java
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


public class JVectorButton extends JButton implements MouseListener {
final	static	private	long	serialVersionUID	=	3142312329828978947L;

final	static	public	int		OBLONG			=	0;
final	static	public	int		ROUND			=	0;

				private int		left_x			=	1;
				private int		left_y			=	1;
				private	int		right_x			=	1;
				private	int		right_y			=	1;
				private Color	left_color		=	new Color(0,0,0,100);
				private Color	right_color		=	new Color(255,255,255,100);
				private	int		orientation		=	OBLONG;
				private float	scale			=	.2f;

    public JVectorButton() {
        this.addMouseListener(this);
    }

    public JVectorButton(final Icon icon) {
        this.addMouseListener(this);
        super.setIcon(icon);
    }

    public JVectorButton(final String text) {
        this.addMouseListener(this);
        super.setText(text);
    }

    public JVectorButton(final String text, final Icon icon) {
        this.addMouseListener(this);
        super.setIcon(icon);
        super.setText(text);
    }

    public JVectorButton(final int orientation) {
    	this.orientation	=	orientation;
        this.addMouseListener(this);
    }

    public JVectorButton(final int orientation, final Icon icon) {
    	this.orientation = orientation;
        this.addMouseListener(this);
        super.setIcon(icon);
    }

    public JVectorButton(final int orientation, final String text) {
    	this.orientation = orientation;
        this.addMouseListener(this);
        super.setText(text);
    }

    public JVectorButton(final int orientation, final String text, final Icon icon) {
    	this.orientation = orientation;
        this.addMouseListener(this);
        super.setIcon(icon);
        super.setText(text);
    }

    public JVectorButton(final float scaleratio) {
    	scale	=	scaleratio;
        this.addMouseListener(this);
    }

    public JVectorButton(final Icon icon, final float scaleratio) {
    	scale	=	scaleratio;
        this.addMouseListener(this);
        super.setIcon(icon);
    }

    public JVectorButton(final String text, final float scaleratio) {
    	scale	=	scaleratio;
        this.addMouseListener(this);
        super.setText(text);
    }

    public JVectorButton(final String text, final Icon icon, final float scaleratio) {
    	scale	=	scaleratio;
        this.addMouseListener(this);
        super.setIcon(icon);
        super.setText(text);
    }

    public JVectorButton(final int orientation, final float scaleratio) {
    	scale	=	scaleratio;
    	this.orientation	=	orientation;
        this.addMouseListener(this);
    }

    public JVectorButton(final int orientation, final Icon icon, final float scaleratio) {
    	scale	=	scaleratio;
    	this.orientation = orientation;
        this.addMouseListener(this);
        super.setIcon(icon);
    }

    public JVectorButton(final int orientation, final String text, final float scaleratio) {
    	scale	=	scaleratio;
    	this.orientation = orientation;
        this.addMouseListener(this);
        super.setText(text);
    }

    public JVectorButton(final int orientation, final String text, final Icon icon, final float scaleratio) {
    	scale	=	scaleratio;
    	this.orientation = orientation;
        this.addMouseListener(this);
        super.setIcon(icon);
        super.setText(text);
    }

    public void setLeftTextShadow(final int x, final int y, final Color color) {
        left_x = x;
        left_y = y;
        left_color = color;
    }

    public void setRightTextShadow(final int x, final int y, final Color color) {
        right_x = x;
        right_y = y;
        right_color = color;
    }

    @Override
	public Dimension getPreferredSize() {
final	String			text	=	getText();
final	FontMetrics		fm		=	this.getFontMetrics(getFont());
final	int				icon_w	=	getIcon()!=null?getIcon().getIconWidth():0;
final	int				icon_h	=	getIcon()!=null?getIcon().getIconHeight():0;
final	int				fw		=	fm.stringWidth(text);
final	int				fh		=	fm.getHeight();
		int				max_h	=	Math.max(fh,icon_h);
		int				max_w	=	Math.max(fw,fh)+Math.max(icon_w,icon_h)+(fw>0 && icon_w>0?getIconTextGap():0);
						max_w	+=	(int)(max_w*scale)*2;
						max_h	+=	(int)(max_h*scale)*2;

		if (orientation==ROUND){
						max_w	=	Math.max(max_h,max_w);
						max_h	=	Math.max(max_h,max_w);
		}
        return new Dimension(max_w,max_h);
    }

    @Override
	public void paintComponent(final Graphics g) {
final	Graphics2D		g2		=	(Graphics2D)g;
        				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        				g2.setColor(this.getBackground());
        				g2.fillRect(0,0,this.getWidth(),this.getHeight());
//	float				ratio	=	(50f/30f)*this.getFont().getSize2D();
final float  			ratio	=	5f/3f*Math.max(this.getFont().getSize2D(),this.getIcon().getIconHeight());
	        			drawLiquidButton(this.getForeground(), this.getWidth(), this.getHeight(), getText(), ratio, g2);
    }

    protected void drawLiquidButton(final Color base, final int width, final int height, final String text, final float ratio, final Graphics2D g2) {
final int				inset	=	(int)ratio/15;
final int				w		=	width - inset*2 - 3;
final int				h		=	height - (int)(3*(1+scale))*2 - 4;
						g2.translate(inset,0);
        drawDropShadow(w,h,ratio,g2);
        if(pressed) {
            			g2.translate(0, 0.04f*ratio);
        }
        drawButtonBody(w,h,base,ratio,g2);
        drawContent(w,h,text,g2);
        drawHighlight(w,h,base,ratio,g2);
        drawBorder(w,h,ratio,g2);
        if(pressed) {
            			g2.translate(0, 0.04f*ratio);
        }
        g2.translate(-inset,0);
    }

    protected void drawDropShadow(final int w, final int h, final float ratio, final Graphics2D g2) {
        g2.setColor(new Color(0,0,0,50));
        if (orientation==ROUND){
        	fillOval(g2,2*(1+scale), .02f*ratio, w+.08f*ratio, h+0.08f*ratio);
        } else {
        	fillRoundRect(g2, -.04f*ratio, .02f*ratio, w+.08f*ratio, h+0.08f*ratio, ratio*1.04f, ratio*1.04f);
        }
        g2.setColor(new Color(0,0,0,100));
        if (orientation==ROUND){
        	fillOval(g2,0,0.06f*ratio,w,h);
        } else {
        	fillRoundRect(g2,0,0.06f*ratio,w,h,ratio,ratio);
        }
    }

    protected void drawButtonBody(final int w, final int h, final Color base, final float ratio, final Graphics2D g2) {
final	Color 				grad_top	=	base.brighter();
final	Color 				grad_bot	=	base.darker();
final	GradientPaint 		bg			=	new GradientPaint(new Point(0,0), grad_top, new Point(0,h), grad_bot);
        			g2.setPaint(bg);
        if (orientation==ROUND){
        	fillOval(g2, 0, 0, w, h);
        } else {
        	fillRoundRect(g2, 0*ratio, 0*ratio, w,h,1*ratio,1*ratio);
        }

final	Color				inner 		=	alphaColor(base.brighter(),75);
        					g2.setColor(inner);
		if (orientation==ROUND){
        	fillOval(g2, ratio*.2f, ratio*.4f, w-ratio*.4f, h-ratio*.5f);
        } else {
        	fillRoundRect(g2, ratio*.4f, ratio*.4f, w-ratio*.8f, h-ratio*.5f, ratio*.6f,ratio*.4f);
        }

    }

    protected void drawContent(final int w, final int h, final String text, final Graphics2D g2) {
final int				fw		=	g2.getFontMetrics().stringWidth(text);
final int				iconx	=	w/2-(getIcon().getIconWidth()+(fw>0?getIconTextGap()+fw:0))/2;
final int				icony	=	h/2-getIcon().getIconHeight()/2;
		getIcon().paintIcon(this,g2,iconx,icony);
		if (fw>0){
final int				fh		=	g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent();
final int				textx	=	iconx+getIcon().getIconWidth()+getIconTextGap();
final int				texty	=	h/2+fh/2;
						g2.setColor(left_color);
						g2.drawString(text,textx-left_x,texty-left_y);
						g2.setColor(right_color);
						g2.drawString(text,textx+right_x,texty+right_y);
					    //g2.setColor(new Color(0,0,0,70));
					    //g2.drawString(text,(int)((float)textx+ratio*(0.04f)), (int)((float)texty + ratio*(0.04f)));
					    g2.setColor(new Color(0,0,0));
					    g2.drawString(text, textx, texty);
		}
    }


    protected void drawHighlight(final int w, final int h, final Color base, final float ratio, final Graphics2D g2) {
        // create the highlight
final GradientPaint	highlight;
        if (orientation==ROUND){
        			highlight = new GradientPaint(
				                    new Point2D.Float(ratio*0.2f,ratio*0.2f),
				                    new Color(255,255,255,155),
				                    new Point2D.Float(ratio*0.2f,ratio*0.55f),
				                    new Color(255,255,255,20)
			                    );
                	g2.setPaint(highlight);
        	fillArc(g2, ratio/15, ratio/15, w-ratio/15*1.5f, h, -10,200);
        	//drawOval(g2, ratio*0.2f, ratio*0.1f, w-ratio*0.4f, ratio*0.4f);
        } else {
        			highlight = new GradientPaint(
				                    new Point2D.Float(ratio*0.2f,ratio*0.2f),
				                    new Color(255,255,255,155),
				                    new Point2D.Float(ratio*0.2f,ratio*0.55f),
				                    new Color(255,255,255,0)
			                    );
                	g2.setPaint(highlight);
        	fillRoundRect(g2, ratio*0.2f, ratio*0.1f, w-ratio*0.4f, ratio*0.4f, ratio*0.8f, ratio*0.4f);
        	drawRoundRect(g2, ratio*0.2f, ratio*0.1f, w-ratio*0.4f, ratio*0.4f, ratio*0.8f, ratio*0.4f);
        }
    }

    protected void drawBorder(final int w, final int h, final float ratio, final Graphics2D g2) {
        // draw the border
        g2.setColor(new Color(0,0,0,150));
        if (orientation==ROUND){
        	drawOval(g2, 0, 0, w, h);
    	} else {
    		drawRoundRect(g2, ratio*0f, ratio*0f, w, h, ratio, ratio);
    	}
    }
    protected static void fillArc(final Graphics2D g2, final float x, final float y, final float w, final float h, final float startAng, final float endAng){
	    if (!SwingUtilities.isEventDispatchThread()) {
final	Runnable	run		=	new Runnable(){
						            @Override
									public void run(){
						            	fillArc(g2, x, y, w, h, startAng, endAng);
						            }
								};
			SwingUtilities.invokeLater(run);
		} else {
			g2.fillArc((int)x,(int)y,(int)w,(int)h,(int)startAng,(int)endAng);
		}
    }

    protected static void fillOval(final Graphics2D g2, final float x, final float y, final float w, final float h) {
    	if (!SwingUtilities.isEventDispatchThread()) {
final	Runnable	run		=	new Runnable(){
						            @Override
									public void run(){
						            	fillOval(g2, x, y, w, h);
						            }
								};
			SwingUtilities.invokeLater(run);
		} else {
			g2.fillOval((int)x, (int)y, (int)w, (int)h);
		}
    }

    protected static void drawOval(final Graphics2D g2, final float x, final float y, final float w, final float h) {
    	if (!SwingUtilities.isEventDispatchThread()) {
final	Runnable	run		=	new Runnable(){
						            @Override
									public void run(){
						            	drawOval(g2, x, y, w, h);
						            }
								};
			SwingUtilities.invokeLater(run);
		} else {
			g2.drawOval((int)x, (int)y, (int)w, (int)h);
		}
    }

    protected static void fillRoundRect(final Graphics2D g2, final float x, final float y, final float w, final float h, final float ax, final float ay) {
    	if (!SwingUtilities.isEventDispatchThread()) {
final Runnable		run		=	new Runnable(){
						            @Override
									public void run(){
						            	fillRoundRect(g2, x, y, w, h, ax, ay);
						            }
								};
			SwingUtilities.invokeLater(run);
		} else {
			g2.fillRoundRect((int)x, (int)y, (int)w, (int)h, (int)ax, (int)ay);
		}
    }

    protected static void drawRoundRect(final Graphics2D g2, final float x, final float y, final float w, final float h, final float ax, final float ay) {
    	if (!SwingUtilities.isEventDispatchThread()) {
final Runnable		run		=	new Runnable(){
						            @Override
									public void run(){
						            	fillArc(g2, x, y, w, h, ax, ay);
						            }
								};
			SwingUtilities.invokeLater(run);
		} else {
			g2.drawRoundRect((int)x, (int)y, (int)w, (int)h, (int)ax, (int)ay );
		}
    }

    protected static Color alphaColor(final Color color, final int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    /* mouse listener implementation */
    protected boolean pressed = false;
    @Override
	public void mouseExited(final MouseEvent evt) { }
    @Override
	public void mouseEntered(final MouseEvent evt) { }
    @Override
	public void mouseClicked(final MouseEvent evt) { }
    @Override
	public void mouseReleased(final MouseEvent evt) {
        pressed = false;
    }
    @Override
	public void mousePressed(final MouseEvent evt) {
        pressed = true;
    }

    public static void p(final String s) {
        System.out.println(s);
    }
}

