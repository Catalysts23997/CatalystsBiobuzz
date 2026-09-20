package org.firstinspires.ftc.teamcode.common;

public class ButtonState {
    private boolean previousState = false;
    private boolean currentState = false;
    private boolean toggled = false;


    public void update(boolean state) {
        previousState = currentState;
        currentState = state;

        if (wasPressed()) { toggled = !toggled; }
    }

    public boolean wasPressed(){
        return currentState && !previousState;
    }

    public boolean wasReleased(){
        return !currentState &&  previousState;
    }

    public boolean isHeld(){
        return currentState;
    }

    public boolean isToggled() { return toggled; }
}
