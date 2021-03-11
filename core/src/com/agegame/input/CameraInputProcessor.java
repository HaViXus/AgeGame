package com.agegame.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class CameraInputProcessor implements InputProcessor {

    public static enum CameraStates {MOVE_LEFT, STAY, MOVE_RIGHT}

    public CameraStates cameraState;
    private final float screenPartToMove = 0.2f;

    public CameraInputProcessor(){
        cameraState = CameraStates.STAY;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int screenWidth = Gdx.graphics.getWidth();
        System.out.println("CONTROLLER:" + screenX + " " + screenWidth + " " + ((float)screenX / screenWidth));

        if((float)screenX / screenWidth > 1 - screenPartToMove){
            cameraState = CameraStates.MOVE_RIGHT;
        }
        else if((float)screenX / screenWidth < screenPartToMove){
            cameraState = CameraStates.MOVE_LEFT;
        }
        else cameraState = CameraStates.STAY;
        System.out.println("MOUSE Moved: " + screenX + " " + screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
