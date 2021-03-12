package com.agegame;

import com.agegame.map.MapParameters;
import com.agegame.input.CameraInputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameCameraController {
    private CameraInputProcessor cameraInput;
    private  OrthographicCamera camera;

    private final float cameraSpeed = 280.0f;
    private MapParameters mapParameters;

    public GameCameraController(CameraInputProcessor cameraInput, MapParameters mapParameters){
        this.cameraInput = cameraInput;
        this.mapParameters = mapParameters;

        camera = new OrthographicCamera(320,180);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void updateCamera(SpriteBatch batch, float delta){
        updateCameraPosition(delta);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    private void updateCameraPosition(float delta){

        if(cameraInput.cameraState == CameraInputProcessor.CameraStates.MOVE_LEFT){
            camera.position.x -= cameraSpeed * delta;
            correctCameraPosition();
        }
        else if(cameraInput.cameraState == CameraInputProcessor.CameraStates.MOVE_RIGHT){
            camera.position.x += cameraSpeed * delta;
            correctCameraPosition();
            System.out.println("CAMERA_MOVE_RIGHT: " + camera.position.x);
        }
    }

    private void correctCameraPosition(){
        float cameraLeftCornerX = camera.position.x - camera.viewportWidth / 2f;
        float cameraRightCornerX = camera.position.x + camera.viewportWidth / 2f;

        if(cameraLeftCornerX < 0){
            camera.position.x = camera.viewportWidth / 2f;
        }
        else if(cameraRightCornerX > mapParameters.width){
            camera.position.x = mapParameters.width - camera.viewportWidth / 2f;
        }
    }
}
