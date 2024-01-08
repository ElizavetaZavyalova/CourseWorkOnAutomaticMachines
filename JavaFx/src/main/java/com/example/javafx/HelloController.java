/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

package com.example.javafx;

/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

import java.net.URL;

import java.util.ResourceBundle;

import com.example.javafx.mashine.VendingMachine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import static com.example.javafx.mashine.VendingMachine.Ingredients.IngredientName.*;
import static com.example.javafx.mashine.VendingMachine.State.*;

public class HelloController {
    @FXML // fx:id="creamButton"
    private Button creamButton; // Value injected by FXMLLoader

    @FXML // fx:id="endButton"
    private Button endButton; // Value injected by FXMLLoader

    @FXML // fx:id="milkButton"
    private Button milkButton; // Value injected by FXMLLoader
    @FXML // fx:id="serviceButton"
    private Button serviceButton; // Value injected by FXMLLoader
    @FXML // fx:id="waitingButton"
    private Button waitingButton; // Value injected by FXMLLoader

    @FXML // fx:id="waterButton"
    private Button waterButton; // Value injected by FXMLLoader

    @FXML // fx:id="coffeeButton"
    private Button coffeeButton; // Value injected by FXMLLoader

    @FXML // fx:id="iseCreamButton"
    private Button iseCreamButton; // Value injected by FXMLLoader
    @FXML // fx:id="surgeButton"
    private Button surgeButton; // Value injected by FXMLLoader
    @FXML // fx:id="teaButton"
    private Button teaButton; // Value injected by FXMLLoader
    @FXML // fx:id="currentImg"
    private ImageView currentImg; // Value injected by FXMLLoader


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="milkBar"
    private ProgressBar milkBar; // Value injected by FXMLLoader

    @FXML // fx:id="milkImg"
    private ImageView milkImg; // Value injected by FXMLLoader

    @FXML // fx:id="coffeeBar"
    private ProgressBar coffeeBar; // Value injected by FXMLLoader

    @FXML // fx:id="coffeeImg"
    private ImageView coffeeImg; // Value injected by FXMLLoader

    @FXML // fx:id="creamBar"
    private ProgressBar creamBar; // Value injected by FXMLLoader

    @FXML // fx:id="creamImg"
    private ImageView creamImg; // Value injected by FXMLLoader

    @FXML // fx:id="iseCreamBar"
    private ProgressBar iseCreamBar; // Value injected by FXMLLoader

    @FXML // fx:id="iseCreamImg"
    private ImageView iseCreamImg; // Value injected by FXMLLoader

    @FXML // fx:id="surgeBar"
    private ProgressBar surgeBar; // Value injected by FXMLLoader

    @FXML // fx:id="surgeImg"
    private ImageView surgeImg; // Value injected by FXMLLoader

    @FXML // fx:id="teaBar"
    private ProgressBar teaBar; // Value injected by FXMLLoader

    @FXML // fx:id="teaImg"
    private ImageView teaImg; // Value injected by FXMLLoader

    @FXML // fx:id="waterBar"
    private ProgressBar waterBar; // Value injected by FXMLLoader

    @FXML // fx:id="waterImg"
    private ImageView waterImg; // Value injected by FXMLLoader
    VendingMachine machine;
    ProgressBarInfo[] info;
    ButtonInfo[] buttonsInfo;
    @FXML // fx:id="stateVbox"
    private VBox stateVbox; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        machine = new VendingMachine();
        initProgressBars();
        initButtons();
    }

    void initButtons() {
        buttonsInfo = new ButtonInfo[machine.getStates().length];
        buttonsInfo[ADDS_COFFEE.getIndex()] = new ButtonInfo(coffeeButton, ADDS_COFFEE, SERVICE, "com/example/javafx/image/coffee.jpg");
        buttonsInfo[ADDS_CREAM.getIndex()] = new ButtonInfo(creamButton, ADDS_CREAM, SERVICE, "com/example/javafx/image/cream.jpg");
        buttonsInfo[ADDS_MILK.getIndex()] = new ButtonInfo(milkButton, ADDS_MILK, SERVICE, "com/example/javafx/image/milk.jpg");
        buttonsInfo[ADDS_SURGE.getIndex()] = new ButtonInfo(surgeButton, ADDS_SURGE, SERVICE, "com/example/javafx/image/surge.jpg");
        buttonsInfo[ADDS_TEA.getIndex()] = new ButtonInfo(teaButton, ADDS_TEA, SERVICE, "com/example/javafx/image/tea.jpg");
        buttonsInfo[ADDS_ISE_CREAM.getIndex()] = new ButtonInfo(iseCreamButton, ADDS_ISE_CREAM, SERVICE, "com/example/javafx/image/iseCream.jpg");
        buttonsInfo[ADDS_WATER.getIndex()] = new ButtonInfo(waterButton, ADDS_WATER, SERVICE, "com/example/javafx/image/water.jpg");
        buttonsInfo[WAITING.getIndex()] = new ButtonInfo(waitingButton, WAITING, SERVICE, "com/example/javafx/image/machine.jpg");
        buttonsInfo[SERVICE.getIndex()] = new ButtonInfo(serviceButton, SERVICE, WAITING, "com/example/javafx/image/settings.png");
        buttonsInfo[COUNT.getIndex()] = new ButtonInfo(endButton, COUNT, COUNT, "com/example/javafx/image/count.jpg");
        changeIndex();
        for (ButtonInfo buttonInfo : buttonsInfo) {
            buttonInfo.button.setOnAction(value -> clickOnButton(buttonInfo));
        }
    }

    void changeIndex() {
        stateVbox.getChildren().clear();
        int index = 0;
        for (VendingMachine.State state : machine.getCurrentState()) {
            if (state != NO) {
                buttonsInfo[state.getIndex()].button.setDisable(false);
                stateVbox.getChildren().add(buttonsInfo[state.getIndex()].button);
                buttonsInfo[state.getIndex()].setCurrentIndex(index);
                if (!machine.isIngredientEnough(index)) {
                    buttonsInfo[state.getIndex()].button.setDisable(true);
                }
            }

            index++;
        }
    }

    void clickOnButton(ButtonInfo buttonInfo) {
        machine.changeState(buttonInfo.currentIndex);
        setBarValue();
        changeIndex();
        setImg();
    }

    void setImg() {
        currentImg.setImage(new Image(buttonsInfo[machine.getState().getIndex()].url));
    }

    void setBarValue() {
        int[] ingredients = machine.getIngredients().getIngredients();
        int indexOfIngredients = 0;
        for (ProgressBarInfo bar : info) {
            bar.setBarValue(ingredients[indexOfIngredients++]);
        }
    }

    void initProgressBars() {
        info = new ProgressBarInfo[VendingMachine.Ingredients.COUNT_OF_INGREDIENTS];
        info[MILK.getIndex()] = new ProgressBarInfo(milkBar, MILK);
        info[TEA.getIndex()] = new ProgressBarInfo(teaBar, TEA);
        info[SURGE.getIndex()] = new ProgressBarInfo(surgeBar, SURGE);
        info[CREAM.getIndex()] = new ProgressBarInfo(creamBar, CREAM);
        info[ISE_CREAM.getIndex()] = new ProgressBarInfo(iseCreamBar, ISE_CREAM);
        info[WATER.getIndex()] = new ProgressBarInfo(waterBar, WATER);
        info[COFFEE.getIndex()] = new ProgressBarInfo(coffeeBar, COFFEE);
        setBarValue();
    }

}
