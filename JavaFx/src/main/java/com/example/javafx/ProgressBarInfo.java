package com.example.javafx;

import com.example.javafx.mashine.VendingMachine;
import javafx.scene.control.ProgressBar;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProgressBarInfo {
    ProgressBar bar;
    VendingMachine.Ingredients.IngredientName name;

    public void setBarValue(int currentValue) {
        bar.setProgress(name.progress(currentValue));
    }
}
