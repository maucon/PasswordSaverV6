package de.tandem.psv6.gui.dialogs;

import de.tandem.psv6.gui.GUIOwner;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public abstract class Dialog {
    Stage stage;
    GridPane grid;
    GUIOwner owner;
    EventHandler<WindowEvent> closeEventHandler;

    public Dialog(GUIOwner owner, String title, double width, double height) {
        this(owner, owner.getStage(), title, width, height);
    }

    public Dialog(GUIOwner owner, Stage ownerStage, String title, double width, double height) {
        this.owner = owner;

        grid = new GridPane();
        grid.setId("gridPane");
        grid.getStylesheets().add(owner.getStyle());
        grid.setAlignment(Pos.CENTER);

        stage = new Stage();
        stage.setTitle(title);
        stage.setWidth(width * owner.getHScale());
        stage.setHeight(height * owner.getVScale());
        stage.initStyle(StageStyle.UTILITY);
        stage.initOwner(ownerStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(new Scene(grid));

        initNodes();
        setCloseEvent();
        stage.setOnCloseRequest(closeEventHandler);
        stage.show();
    }

    protected void initNodes() {
    }

    protected void setCloseEvent() {
        closeEventHandler = windowEvent -> {
        };
    }

    protected TextInputControl addField(String labelText, int gridIndex) {
        return addField(labelText, gridIndex, false);
    }

    protected TextInputControl addField(String labelText, int gridIndex, boolean isPassword) {
        var label = new Label(labelText);
        var input = isPassword ? new PasswordField() : new TextField();
        grid.addRow(gridIndex, label, input);
        return input;
    }
}
