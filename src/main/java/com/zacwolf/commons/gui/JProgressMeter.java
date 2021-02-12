/* com.zacwolf.commons.gui.JProgressMeter.java
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

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.zacwolf.commons.gui.imagehelpers.ImageHandler;
import com.zacwolf.commons.gui.statusbar.JStatusBar;

/**
 * A progress meter designed to slot into a StatusBar.
 * @see JStatusBar
 * @author tonyj
 * @version $Id: JProgressMeter.java,v 1.5 2003/05/10 20:21:01 tonyj Exp $
 */

public class JProgressMeter extends JPanel{
final	static	private	long						serialVersionUID	=	7231435844584407538L;

final			private	AsnycListener				listener			=	new AsnycListener();
final			private	JLabel						m_stopButton;
final			private DefaultBoundedRangeModel	m_realModel 		=	new DefaultBoundedRangeModel();
final			private JProgressBar				m_meter				=	new JProgressBar(m_realModel);
				private	Stoppable					m_stop;
				private BoundedRangeModel			m_model;

	public JProgressMeter() {
		this(true);
	}

	public JProgressMeter(final boolean showStopButton) {
		super(new FlowLayout(FlowLayout.RIGHT,5,0));
		setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
final	Icon		icon			=	ImageHandler.getIcon("/toolbarButtonGraphics/general/Stop16.gif",getClass());
					m_stopButton	=	new JLabel(icon); // Note: We use a label since it takes much less space than a button
					m_stopButton.setEnabled(false);
					m_stopButton.addMouseListener(
									new java.awt.event.MouseAdapter(){
										@Override
										public void mouseClicked(final java.awt.event.MouseEvent e){
final	Stoppable	stop 			=	m_stop;// Thread safe
											if (stop != null) {
												stop.stop();
											}
										}
									});
					m_meter.setBorderPainted(false);
					add(m_meter);
					add(m_stopButton);
					m_stopButton.setVisible(showStopButton);
					setAlignmentX(0.9f);
	}

	public void setShowStopButton(final boolean showStopButton) {
		m_stopButton.setVisible(showStopButton);
	}

	/**
	 * Set the model for the progress bar
	 * @param model The model, or null to clear the progress bar
	 */
	public void setModel(final BoundedRangeModel model){
		if (m_model != null) {
			m_model.removeChangeListener(listener);
		}	m_model	= model;
		if (m_model != null){
			m_model.addChangeListener(listener);
			setProgress(m_model);
		} else {
			m_realModel.setRangeProperties(0,0,0,0,false);
		}
	}
	/**
	 * Set a stoppable, which will be stopped if the user presses the stop button
	 * @param stop The stoppable, or null to clear
	 */
	public void setStoppable(final Stoppable stop){
		if (stop == null){
			setModel(null);
			m_stopButton.setEnabled(false);
			m_stopButton.repaint();
			m_stop = stop;
		} else {
			m_stop = stop;
			setModel(stop.getModel());
			m_stopButton.setEnabled(true);
			m_stopButton.repaint();
		}
	}
	/**
	 * Get the model attached to the progress bar
	 */
	public BoundedRangeModel getModel() {
		return m_model;
	}
	public void setIndeterminate(final boolean ind){
		m_meter.setIndeterminate(ind);
	}
	public boolean isIndeterminate(){
		return m_meter.isIndeterminate();
	}
	public void setStopEnabled(final boolean enabled){
		m_stopButton.setEnabled(enabled);
	}
	public boolean getStopEnabled(){
		return m_stopButton.isEnabled();
	}
	private void setProgress(final BoundedRangeModel model){
		m_realModel.setRangeProperties(
			model.getValue(),
			model.getExtent(),
			model.getMinimum(),
			model.getMaximum(),
			model.getValueIsAdjusting()
		);
	}

	private class AsnycListener implements ChangeListener {
		@Override
		public void stateChanged(final ChangeEvent e){
final	BoundedRangeModel	model	=	(BoundedRangeModel) e.getSource();
			if (SwingUtilities.isEventDispatchThread()) {
				setProgress(model);
			} else {
final	Runnable			run		=	new Runnable(){
											@Override
											public void run(){
												setProgress(model);
											}
										};
				SwingUtilities.invokeLater(run);
			}
		}
	}
}