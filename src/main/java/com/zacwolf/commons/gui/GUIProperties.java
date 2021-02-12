/* com.zacwolf.commons.gui.GUIProperties.java
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
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

/**
 *
 */
public class GUIProperties extends Properties{
final	static	private	long	serialVersionUID	=	-1610557451488248672L;

final			private	_GUI	gui;
	/**
	 *
	 */
	public GUIProperties(final _GUI gui){
		this.gui				=	gui;
		defaults				=	new Properties();
		try {
			defaults.load(getClass().getClassLoader().getResourceAsStream("default.props"));
		} catch (final IOException e) {
			//Shouldn't happen since this will be a jar provided file
		}
		try {
final	File		props		=	new File(System.getProperty("user.home")+File.separator+".zcommons"+File.separator+gui.getTitle()+".properties");
			if (props.exists()) {
final	InputStream	fin			=	new FileInputStream(props);
				try {
					load(fin);
				} finally {
					fin.close();
				}
			}
		} catch (final IOException e) {
			System.err.println("Unable to load gui properties for "+gui.getTitle()+".properties");
			e.printStackTrace();
		}
final	Rectangle	ui				=	getRectangle("ui");
		gui.setLocation(ui.x,ui.y);
		gui.setSize(new java.awt.Dimension(ui.width,ui.height));
	}


	   public void store() {
			try {
	final	File			props		=	new File(System.getProperty("user.home")+File.separator+".zcommons"+File.separator+gui.getTitle()+".properties");
				if (!props.getParentFile().exists() && !props.getParentFile().mkdirs()) {
					throw new IOException();
				}

				setProperty("ui.X",""+gui.getX());
				setProperty("ui.Y",""+gui.getY());
				setProperty("ui.Width",""+gui.getWidth());
				setProperty("ui.Height",""+gui.getHeight());

	final	OutputStream	fout		=	new FileOutputStream(props);
				try {
					store(fout,"GUI properties for com.zacwolf.commons.gui instance:"+gui.getTitle());
				} finally {
					fout.close();
				}
			} catch (final IOException e) {
				System.err.println("Unable to store gui properties for "+gui.getTitle()+".properties");
				e.printStackTrace();
			}
		}


	/**
    * @param The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
	public Rectangle getRectangle(final String key) {
final	Rectangle	result			=	new Rectangle();
		   			result.x		=	getInteger(key.concat(".X"));
		   			result.y		=	getInteger(key.concat(".Y"));
		   			result.width	=	getInteger(key.concat(".Width"));
		   			result.height	=	getInteger(key.concat(".Height"));
		 return result;
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param rect the value to store
    */
	public void setRectangle(final String key, final Rectangle rect){
		put(key.concat("-X"), String.valueOf(rect.x));
		put(key.concat("-Y"), String.valueOf(rect.y));
		put(key.concat("-Width"), String.valueOf(rect.width));
		put(key.concat("-Height"), String.valueOf(rect.height));
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
	public Color getColor(final String key){
		return new Color(getInteger(key.concat("-r")), getInteger(key.concat("-g")), getInteger(key.concat("-b")));
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param c the value to store
    */
	public void setColor(final String key, final Color c){
		put(key.concat("-r"), String.valueOf(c.getRed()));
		put(key.concat("-g"), String.valueOf(c.getGreen()));
		put(key.concat("-b"), String.valueOf(c.getBlue()));
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
	public String[] getStringArray(final String key){
final	String[]	result		=	new String[getInteger(key +"-length")];
		for (int i	=	0; i < result.length; i++) {
					result[i]	=	getProperty(key +"-"+ i);
		}
		return result;
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param sa the value to store
    */
	public void setStringArray(final String key, final String[] sa) {
		put( key +"-length", sa == null ? "0" : String.valueOf(sa.length) );
		if (sa != null) {
			for (int i	=	0; i < sa.length; i++) {
				put(key +"-"+ i, sa[i]);
			}
		}
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
	public boolean getBoolean(final String key) throws NullPointerException{
final	String	value	=	getProperty(key);
		if (value==null) {
			throw new NullPointerException("Not value stored for key:"+key);
		}
		return value.equalsIgnoreCase("true");
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param value the value to store
    */
	public void setBoolean(final String key, final boolean value){
		put(key,String.valueOf(value));
	}

   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
	public int getInteger(final String key){
final	String	s	=	getProperty(key);
		return s == null ? -1 : Integer.parseInt(s);
	}
   /**
    * @param The Properties set
    * @param key the key used to store this property
    * @param i the value to store
    */
	public void setInteger(final String key, final int i){
		put(key, String.valueOf(i));
	}

   /**
    * @param key the key used to store this property
    * @param def a default in case the property cannot be retrieved
    */
	public float getFloat(final String key){
final	String	s	=	getProperty(key);
		return s == null ? -1 : Float.parseFloat(s);
	}


	/**
    * @param The Properties set
    * @param key the key used to store this property
    * @param f the value to store
    */
	public void setFloat(final String key, final float f){
		put(key, String.valueOf(f));
	}

   /**
     * Load a URL from a properties file. If the URL begins with / it is taken to be a
     * system resource (i.e. on the classpath).
     */
	public URL getURL(final String key){
final	String	p		=	getProperty(key);
		try{
			return new URL(p);
		} catch (final java.net.MalformedURLException x) {
			return getClass().getResource(p);
		}
	}


	public void setURL(final String key, final URL url){
		put(key,url.toString());
	}
}
