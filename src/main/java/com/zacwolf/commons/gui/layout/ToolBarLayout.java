package com.zacwolf.commons.gui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.JToolBar;

/**
 * A ToolbarLayout arranges components in a left-to-right flow, much
 * like the FlowLayout which is supplied with the JDK.  However, it
 * fixes the problem with the FlowLayout that occurs when a FlowLayout
 * is for a North aligned component of a BorderLayout--namely, that
 * if the window is shrunk so that some of the components within the
 * FlowLayout wrap to the next line the component does not grow in
 * height to support this wrapping.  This bug was caused by the library
 * designers using the preferred size in recalculating, not the size
 * which is determined by the window width.  As such, the flow layout
 * would always want to be the height of one row.
 *
 * A ToolbarLayout lets each component assume its natural (preferred) size.
 *
 * NOTE: This class was initially a subclass of FlowLayout, but we
 *		 encountered problems using that approach.
 *
 * @version $Id: ToolBarLayout.java,v 1.3 2003/01/10 16:36:32 tonyj Exp $
 * @author	  Peter Armstrong
 * @author	  Tony Johnson
 */

public class ToolBarLayout implements LayoutManager {

final	static	public 	int LEFT	  	=	0;
final	static	public 	int CENTER		=	1;

	/**
	 * This value indicates that each row of components
	 * should be right-justified.
	 */
final	static	public 	int	RIGHT	  	=	2;
						int align;
						int hgap;
						int vgap;

	/**
	 * Constructs a new ToolbarLayout with a left alignment and a
	 * default 5-unit horizontal and vertical gap.
	 */
	public ToolBarLayout() {
		this(LEFT, 5, 5);
	}
	/**
	 * Constructs a new ToolbarLayout with the specified alignment and a
	 * default 5-unit horizontal and vertical gap.
	 * The value of the alignment argument must be one of
	 * <code>ToolbarLayout.LEFT</code>, <code>ToolbarLayout.RIGHT</code>,
	 * or <code>ToolbarLayout.CENTER</code>.
	 * @param align the alignment value
	 */
	public ToolBarLayout(final int align) {
		this(align, 5, 5);
	}
	/**
	 * Creates a new ToolbarLayout with the indicated alignment
	 * and the indicated horizontal and vertical gaps.
	 * <p>
	 * The value of the alignment argument must be one of
	 * <code>ToolbarLayout.LEFT</code>, <code>ToolbarLayout.RIGHT</code>,
	 * or <code>ToolbarLayout.CENTER</code>.
	 * @param		align	the alignment value.
	 * @param		hgap	 the horizontal gap between components.
	 * @param		vgap	 the vertical gap between components.
	 */
	public ToolBarLayout(final int align,final int hgap,final int vgap) {
		this.align = align;
		this.hgap = hgap;
		this.vgap = vgap;
	}
	/**
	 * Gets the alignment for this layout.
	 * Possible values are <code>ToolbarLayout.LEFT</code>,
	 * <code>ToolbarLayout.RIGHT</code>, or <code>ToolbarLayout.CENTER</code>.
	 * @return	  the alignment value for this layout.
	 * @see		  #setAlignment
	 */
	public int getAlignment() {
		return align;
	}
	/**
	 * Sets the alignment for this layout.
	 * Possible values are <code>ToolbarLayout.LEFT</code>,
	 * <code>ToolbarLayout.RIGHT</code>, and <code>ToolbarLayout.CENTER</code>.
	 * @param		align the alignment value.
	 * @see		  #getAlignment
	 */
	public void setAlignment(final int align) {
		this.align = align;
	}
	/**
	 * Gets the horizontal gap between components.
	 * @return	  the horizontal gap between components.
	 * @see		  #setHgap
	 */
	public int getHgap() {
		return hgap;
	}
	/**
	 * Sets the horizontal gap between components.
	 * @param hgap the horizontal gap between components
	 * @see		  #getHgap
	 */
	public void setHgap(final int hgap) {
		this.hgap = hgap;
	}
	/**
	 * Gets the vertical gap between components.
	 * @return	  the vertical gap between components.
	 * @see		  #setVgap
	 */
	public int getVgap() {
		return vgap;
	}
	/**
	 * Sets the vertical gap between components.
	 * @param vgap the vertical gap between components
	 * @see		  #getVgap
	 */
	public void setVgap(final int vgap) {
		this.vgap = vgap;
	}
	/**
	 * Adds the specified component to the layout.  Sets the orientation to be horizontal.
	 * @param name the name of the component
	 * @param comp the component to be added
	 */
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
		try {
			((JToolBar)comp).setOrientation(JToolBar.HORIZONTAL);
		} catch (final Exception e){
		}
	}
	/**
	 * Removes the specified component from the layout. Not used by
	 * this class.
	 * @param comp the component to remove
	 * @see		 java.awt.Container#removeAll
	 */
	@Override
	public void removeLayoutComponent(final Component comp) {
	}
	/**
	 * Returns the preferred dimensions for this layout given the components
	 * in the specified target container.  This method is the difference
	 * between ToolbarLayout and FlowLayout.
	 * @param target the component which needs to be laid out
	 * @return	 the preferred dimensions to lay out the
	 *						  subcomponents of the specified container.
	 * @see Container
	 * @see #minimumLayoutSize
	 * @see	 java.awt.Container#getPreferredSize
	 */
	@Override
	public Dimension preferredLayoutSize(final Container target) {
		synchronized (target.getTreeLock()) {
final	Dimension			dim			=	new Dimension(0, 0);
final	int					nmembers	=	target.getComponentCount();
final	Insets				insets		=	target.getInsets();
		int					numRows	  =	1;									 //the number of rows
		int					rowSumWidth =	insets.left + insets.right;	//the width of the row so far
		final int					rowMaxWidth =	target.getSize().width;		 //the width that the ToolbarLayout is in
		int					rowHeight	=	0;									 //the height of each row
		int					numOnRow	 =	0;									 //the number of components on the row

			for (int i = 0 ; i < nmembers ; i++) {
final	Component			m			=	target.getComponent(i);
					if (m.isVisible()) {
final	Dimension			d			=	m.getPreferredSize();
									rowHeight	=	Math.max(rowHeight, d.height);	 //make each row the height of the biggest component of all
							if (i > 0) {
									rowSumWidth	+=	hgap;		//add on the pre-spacing if this is not the first component
							}
									rowSumWidth +=	d.width;	 //add the width of the component
							//if it overflowed and if there are components already on this row then bump this component to next row
							if (rowSumWidth + hgap > rowMaxWidth) {
								if (numOnRow > 0) {
									numRows++;
									rowSumWidth =	insets.left + insets.right + d.width;
									numOnRow	=	0;//reset the number of components on the next row (we ++ no matter what later)
								}
							}
							numOnRow++;//add this component to the count of the number on the row
					}
			}
							dim.width		=	rowMaxWidth;
							dim.height		=	insets.top + insets.bottom + numRows*rowHeight + vgap*(numRows - 1);
				return dim;
			}
	}
	/**
	 * Returns the minimum dimensions needed to layout the components
	 * contained in the specified target container.
	 * @param target the component which needs to be laid out
	 * @return	 the minimum dimensions to lay out the
	 *						  subcomponents of the specified container.
	 * @see #preferredLayoutSize
	 * @see		 java.awt.Container
	 * @see		 java.awt.Container#doLayout
	 */
	@Override
	public Dimension minimumLayoutSize(final Container target) {
		synchronized (target.getTreeLock()) {
final	Dimension		dim			=	new Dimension(0, 0);
final	int				nmembers	=	target.getComponentCount();
		for (int i = 0 ; i < nmembers ; i++) {
final	Component		m			=	target.getComponent(i);
			if (m.isVisible()) {
final	Dimension		d = m.getMinimumSize();
								dim.height	=	Math.max(dim.height, d.height);
					if (i > 0) {
								dim.width	+=	hgap;
					}		dim.width	+=	d.width;
				}
			}
final	Insets			insets		=	target.getInsets();
						dim.width += insets.left + insets.right + hgap*2;
						dim.height += insets.top + insets.bottom;
			return dim;
		}
	}
	/**
	 * Centers the elements in the specified row, if there is any slack.
	 * @param target the component which needs to be moved
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param width the width dimensions
	 * @param height the height dimensions
	 * @param rowStart the beginning of the row
	 * @param rowEnd the the ending of the row
	 */
	private void moveComponents(final Container target, int x, final int y, final int width, final int height, final int rowStart, final int rowEnd) {
		synchronized (target.getTreeLock()) {
			switch (align) {
				case LEFT:
					break;
				case CENTER:
					x += width / 2;
					break;
				case RIGHT:
					x += width;
					break;
			}
			for (int i = rowStart ; i < rowEnd ; i++) {
	final	Component m = target.getComponent(i);
				if (m.isVisible()) {
					m.setLocation(x, y + (height - m.getSize().height) / 2);//JDK1.2 porting: replace with getHeight()
					x += hgap + m.getSize().width;									 //JDK1.2 porting: replace with getWidth()
				}
			}
		}
	}
	/**
	 * Lays out the container. This method lets each component take
	 * its preferred size by reshaping the components in the
	 * target container in order to satisfy the constraints of
	 * this <code>ToolbarLayout</code> object.
	 * @param target the specified component being laid out.
	 * @see Container
	 * @see		 java.awt.Container#doLayout
	 */
	@Override
	public void layoutContainer(final Container target) {
		synchronized (target.getTreeLock()) {
final	Insets		insets		=	target.getInsets();
final	int			maxwidth	=	target.getSize().width - (insets.left + insets.right + hgap*2);//JDK1.2 porting: replace with getWidth()
final	int			nmembers	=	target.getComponentCount();
		int 		x			=	0;
		int			y			=	insets.top;
		int			rowh		=	0;
		int			start		=	0;
			for (int i = 0 ; i < nmembers ; i++) {
final	Component	m			=	target.getComponent(i);
				if (m.isVisible()) {
final	Dimension	d			=	m.getPreferredSize();
					m.setSize(d.width, d.height);
					if (x == 0 || x + d.width <= maxwidth) {
						if (x > 0) {
							x 	+=	hgap;
						}
						x		+=	d.width;
						rowh	=	Math.max(rowh, d.height);
					} else {
						moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, i);
						x		=	d.width;
						y		+=	vgap + rowh;
						rowh	=	d.height;
						start	=	i;
					}
				}
			}
			moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, nmembers);
		}
	}
	/**
	 * Returns a string representation of this <code>ToolbarLayout</code>
	 * object and its values.
	 * @return	  a string representation of this layout.
	 */
	@Override
	public String toString() {
		String str = "";
		switch (align) {
			case LEFT:
				str		=	",align=left";
				break;
			case CENTER:
				str		=	",align=center";
				break;
			case RIGHT:
				str		=	",align=right";
				break;
		}
		return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + str + "]";
	}
}


