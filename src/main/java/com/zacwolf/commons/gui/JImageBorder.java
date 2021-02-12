/* com.zacwolf.commons.gui.JImageBorder.java
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
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.border.AbstractBorder;

public class JImageBorder extends AbstractBorder {

final	static	private	long serialVersionUID = 4407799218521181500L;

final	Image	top_center, top_left, top_right;
final	Image	left_center, right_center;
final	Image	bottom_center, bottom_left, bottom_right;
		Insets	insets		=	null;

    public JImageBorder(
    	final Image top_left,
    	final Image top_center,
    	final Image top_right,
        final Image left_center,
        final Image right_center,
        final Image bottom_left,
        final Image bottom_center,
        final Image bottom_right
        )
    {
        this.top_left		= top_left;
        this.top_center		= top_center;
        this.top_right		= top_right;
        this.left_center	= left_center;
        this.right_center	= right_center;
        this.bottom_left	= bottom_left;
        this.bottom_center	= bottom_center;
        this.bottom_right	= bottom_right;
    }

    public void setInsets(final Insets insets) {
        this.insets = insets;
    }

    @Override
	public Insets getBorderInsets(final Component c) {
        if(insets != null) {
            return insets;
        }
        return new Insets(top_center.getHeight(null),left_center.getWidth(null),
            bottom_center.getHeight(null), right_center.getWidth(null));
    }


    @Override
	public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
        			g.setColor(Color.white);
        			g.fillRect(x,y,width,height);

final	Graphics2D	g2		=	(Graphics2D)g;

final	int 		tlw		=	top_left.getWidth(null);
final	int			tlh		=	top_left.getHeight(null);
//final	int			tcw		=	top_center.getWidth(null);
final	int			tch		=	top_center.getHeight(null);
final	int			trw		=	top_right.getWidth(null);
final	int			trh		=	top_right.getHeight(null);

final	int			lcw		=	left_center.getWidth(null);
//final	int			lch		=	left_center.getHeight(null);
final	int			rcw		=	right_center.getWidth(null);
//final	int			rch		=	right_center.getHeight(null);

final	int			blw		=	bottom_left.getWidth(null);
final	int			blh		=	bottom_left.getHeight(null);
//final	int			bcw		=	bottom_center.getWidth(null);
final	int			bch		=	bottom_center.getHeight(null);
final	int			brw		=	bottom_right.getWidth(null);
final	int			brh		=	bottom_right.getHeight(null);

        fillTexture(g2, top_left, x, y, tlw, tlh);
        fillTexture(g2, top_center, x+tlw, y, width-tlw-trw, tch);
        fillTexture(g2, top_right, x+width-trw, y, trw, trh);

        fillTexture(g2, left_center, x, y+tlh, lcw, height-tlh-blh);
        fillTexture(g2, right_center, x+width-rcw, y+trh, rcw, height-trh-brh);

        fillTexture(g2, bottom_left, x, y+height-blh, blw, blh);
        fillTexture(g2, bottom_center, x+blw, y+height-bch, width-blw-brw, bch);
        fillTexture(g2, bottom_right, x+width-brw, y+height-brh, brw, brh);
    }

    public void fillTexture(final Graphics2D g2, final Image img, final int x, final int y, final int w, final int h) {
final	BufferedImage	buff	=	createBufferedImage(img);
final	Rectangle		anchor	=	new Rectangle(x, y, img.getWidth(null), img.getHeight(null));
final	TexturePaint	paint	=	new TexturePaint(buff,anchor);
        				g2.setPaint(paint);
        				g2.fillRect(x,y,w,h);
    }

    public BufferedImage createBufferedImage(final Image img) {
final	BufferedImage	buff	=	new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
final	Graphics		gfx		=	buff.createGraphics();
						gfx.drawImage(img, 0, 0, null);
						gfx.dispose();
        return buff;
    }
}


