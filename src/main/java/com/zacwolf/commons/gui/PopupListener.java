/* com.zacwolf.commons.gui.PopupListener.java
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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

/**
 * A simple helper class for popping up popup menus.
 * This should be added as a mouse listener to the component to which
 * the popup menu should be associated.
 */
public class PopupListener extends MouseAdapter{
   private final JPopupMenu popup;

   public PopupListener(final JPopupMenu popup){
      this.popup = popup;
   }
   @Override
public void mousePressed(final MouseEvent e){
      maybeShowPopup(e);
   }
   @Override
public void mouseReleased(final MouseEvent e){
      maybeShowPopup(e);
   }
   protected void maybeShowPopup(final MouseEvent e){
      if (popup.isPopupTrigger(e)){
         popup.show(e.getComponent(), e.getX(), e.getY());
      }
   }
}