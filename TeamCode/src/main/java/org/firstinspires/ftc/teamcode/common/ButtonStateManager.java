package org.firstinspires.ftc.teamcode.common;

import org.firstinspires.ftc.teamcode.enums.ButtonEnum;

import java.util.HashMap;
import java.util.Map;

public class ButtonStateManager {
    Map<ButtonEnum, ButtonState> buttonStateManager = new HashMap<>();

    private ButtonState getOrCreate(ButtonEnum key){ return buttonStateManager.computeIfAbsent(key, k -> new ButtonState()); }

    public void update(ButtonEnum key, boolean state){ getOrCreate(key).update(state); }

    public boolean wasPressed(ButtonEnum key){
        ButtonState buttonState = buttonStateManager.get(key);
        return buttonState != null && buttonState.wasPressed();
    }

    public boolean wasReleased(ButtonEnum key){
        ButtonState buttonState = buttonStateManager.get(key);
        return buttonState != null && buttonState.wasReleased();
    }

    public boolean isHeld(ButtonEnum key){
        ButtonState buttonState = buttonStateManager.get(key);
        return buttonState != null && buttonState.isHeld();
    }

    public boolean toggled(ButtonEnum key) {
        ButtonState buttonState = buttonStateManager.get(key);
        return buttonState != null && buttonState.isToggled();
    }
}
