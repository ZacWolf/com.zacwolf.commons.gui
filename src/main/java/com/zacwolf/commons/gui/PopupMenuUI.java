/* com.zacwolf.commons.gui.PopupMenuUI.java
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
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

public class PopupMenuUI extends BasicPopupMenuUI {
    public static ComponentUI createUI(final JComponent c) {
        return new PopupMenuUI();
    }

    @Override
	public Popup getPopup(final JPopupMenu popup, final int x, final int y) {
final	Popup	pp		=	super.getPopup(popup,x,y);
final	JPanel	panel	=	(JPanel)popup.getParent();
        		panel.setBorder(new ShadowBorder(3,3));
        		panel.setOpaque(false);
        return pp;
    }
}

class ShadowBorder extends AbstractBorder {
final	static	private	long	serialVersionUID = -1476363615176997510L;
final					int		xoff, yoff;
final					Insets	insets;

    public ShadowBorder(final int x, final int y) {
        xoff	=	x;
        yoff	=	y;
        insets	=	new Insets(0,0,xoff,yoff);
    }

    @Override
	public Insets getBorderInsets( final Component c ) {
        return insets;
    }

    @Override
	public void paintBorder(
		final Component comp,
		final Graphics g,
		final int x,
		final int y,
		final int width,
		final int height
		)
    {
        g.setColor(Color.black);
        g.translate(x,y);
        g.fillRect(width-xoff, yoff, xoff, height-yoff);
        g.fillRect(xoff, height-yoff, width-xoff, yoff);
        g.translate(-x,-y);
    }
}
