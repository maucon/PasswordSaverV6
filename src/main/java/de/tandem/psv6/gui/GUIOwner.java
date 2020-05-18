package de.tandem.psv6.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.stage.Stage;

public interface GUIOwner {

    DoubleProperty hScale = new SimpleDoubleProperty();
    DoubleProperty vScale = new SimpleDoubleProperty();

    public default double getHScale() {
        return hScale.doubleValue();
    }
    public default double getVScale() {
        return vScale.doubleValue();
    }
    public abstract Stage getStage();
    public abstract String getStyle();
}
