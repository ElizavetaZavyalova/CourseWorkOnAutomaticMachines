package com.example.javafx;


import com.example.javafx.mashine.VendingMachine;
import javafx.scene.control.Button;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
public class ButtonInfo {
    final Button button;
    final VendingMachine.State defaultState;
    final VendingMachine.State state;
    @Getter
    final String url;
    @Getter
    @Setter
    int currentIndex = -1;

    VendingMachine.State getState(VendingMachine.State state) {
        if (state == VendingMachine.State.SERVICE) {
            return this.defaultState;
        }
        return this.state;
    }

}
