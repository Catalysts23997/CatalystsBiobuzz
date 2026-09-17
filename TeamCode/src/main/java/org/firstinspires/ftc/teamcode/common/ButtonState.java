package org.firstinspires.ftc.teamcode.common;

public class ButtonState {
    private boolean previousState = false;
    private boolean currentState = false;


    public void update(boolean state) {
        previousState = currentState;
        currentState = state;
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
}
