package com.zacwolf.commons.gui.statusbar.windowresize;

import java.awt.*;

public class TriangleSquareWindowsCornerIcon extends ResizeIcon {

    //RGB values discovered using ZoomIn
    private static final Color	THREE_D_EFFECT_COLOR		= new Color(255, 255, 255);
    private static final Color	SQUARE_COLOR_LEFT			= new Color(184, 180, 163);
    private static final Color	SQUARE_COLOR_TOP_RIGHT		= new Color(184, 180, 161);
    private static final Color	SQUARE_COLOR_BOTTOM_RIGHT	= new Color(184, 181, 161);
    private static final int	COLOR_OFFSET				= 80; // Color offset



    public TriangleSquareWindowsCornerIcon(int height){
    	super();
    	setIconHeight(height);
    	setOpaque(false);
    	setPreferredSize(new Dimension(18,height));
    }



    protected void paintComponent(Graphics g){
        //Layout a row and column "grid"
 	int		firstRow 		=	super.getIconHeight()-12>0?super.getIconHeight()-12:0;
	int		firstColumn 	=	6;
	int		rowDiff 		=	4;
	int		columnDiff 		=	4;

	int		secondRow		=	firstRow + rowDiff;
	int 	secondColumn	=	firstColumn + columnDiff;
 	int 	thirdRow		=	secondRow + rowDiff;
   	int 	thirdColumn		=	secondColumn + columnDiff;


        //Draw the white squares first, so the gray squares will overlap
        draw3dSquare(g, firstColumn+1, thirdRow+1);

        draw3dSquare(g, secondColumn+1, secondRow+1);
        draw3dSquare(g, secondColumn+1, thirdRow+1);

        draw3dSquare(g, thirdColumn+1, firstRow+1);
        draw3dSquare(g, thirdColumn+1, secondRow+1);
        draw3dSquare(g, thirdColumn+1, thirdRow+1);

        //draw the gray squares overlapping the white background squares
        drawSquare(g, firstColumn, thirdRow);

        drawSquare(g, secondColumn, secondRow);
        drawSquare(g, secondColumn, thirdRow);

        drawSquare(g, thirdColumn, firstRow);
        drawSquare(g, thirdColumn, secondRow);
        drawSquare(g, thirdColumn, thirdRow);
        
    	// Add with a line seperater
        int 	red				=	java.awt.SystemColor.control.getRed();
        int 	green			=	java.awt.SystemColor.control.getGreen();
        int 	blue			=	java.awt.SystemColor.control.getBlue();
        Color	oldColor		=	g.getColor(); //cache the old color
        Color	leftColor		=	new Color((red-COLOR_OFFSET<0?0:red-COLOR_OFFSET), (green-COLOR_OFFSET<0?0:green-COLOR_OFFSET), (blue-COLOR_OFFSET<0?0:blue-COLOR_OFFSET));
        Color	rightColor		=	new Color((red+COLOR_OFFSET>255?255:red+COLOR_OFFSET), (green+COLOR_OFFSET>255?255:green+COLOR_OFFSET), (blue+COLOR_OFFSET>255?255:blue+COLOR_OFFSET));
    	    	g.setColor(leftColor);
    			g.drawLine(0,2,0,getIconHeight()-2);
    			g.setColor(rightColor);
    			g.drawLine(1,2,1,getIconHeight()-2);
    			g.setColor(oldColor);

    }

    private void draw3dSquare(Graphics g, int x, int y){
	Color	oldColor	=	g.getColor(); //cache the old color
        	g.setColor(THREE_D_EFFECT_COLOR); //set the white color
        	g.fillRect(x,y,2,2); //draw the square
        	g.setColor(oldColor); //reset the old color
    }


    private void drawSquare(Graphics g, int x, int y){
	Color	oldColor	=	g.getColor();
        	g.setColor(SQUARE_COLOR_LEFT);
        	g.drawLine(x,y, x,y+1);
        	g.setColor(SQUARE_COLOR_TOP_RIGHT);
        	g.drawLine(x+1,y, x+1,y);
        	g.setColor(SQUARE_COLOR_BOTTOM_RIGHT);
        	g.drawLine(x+1,y+1, x+1,y+1);
        	g.setColor(oldColor);
    }

}
