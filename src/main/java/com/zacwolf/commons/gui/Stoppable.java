package com.zacwolf.commons.gui;

import javax.swing.BoundedRangeModel;

public interface Stoppable {
    BoundedRangeModel getModel();
    void stop();
}
