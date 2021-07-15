package org.example.presentation;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.util.HashSet;
import java.util.Set;

// TODO: 15-07-2021 This class is very overkill for hvad i am doing
public class ViewProfile {

    private final Set<Button> buttons;
    private final Set<TextField> textFields;
    private final Set<ToggleButton> toggleButtons;

    public ViewProfile(){
        buttons = new HashSet<>();
        textFields = new HashSet<>();
        toggleButtons = new HashSet<>();
    }

    public void view(boolean view, boolean enable){
        buttons.forEach(b -> {
            b.setVisible(view);
            b.setDisable(!enable);
        });
        textFields.forEach(t -> {
            t.setVisible(view);
            t.setDisable(!enable);
        });
        toggleButtons.forEach(t -> {
            t.setVisible(view);
            t.setDisable(!enable);
        });
    }

    public void addButton(Button button){
        buttons.add(button);
    }

    public void addAllButtons(Set<Button> buttonSet){
        buttons.addAll(buttonSet);
    }

    public void addTextField(TextField textField){
        textFields.add(textField);
    }

    public void addAllTextFields(Set<TextField> textFieldSet){
        textFields.addAll(textFieldSet);
    }

    public void addToggleButton(ToggleButton toggleButton){
        toggleButtons.add(toggleButton);
    }

    public void addAllToggleButton(Set<ToggleButton> toggleButtonSet){
        toggleButtons.addAll(toggleButtonSet);
    }
}
