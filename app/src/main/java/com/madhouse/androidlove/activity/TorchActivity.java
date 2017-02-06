package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.madhouse.androidlove.R;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.util.List;
import android.hardware.Camera;

public class TorchActivity extends Activity {

    private Camera camera = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torch);
        torchSwitchClick();
    }

    public void torchSwitchClick() {
        Context context = this.getApplicationContext();
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            ToggleButton button = (ToggleButton) findViewById(R.id.b_torch);
            button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if (camera == null)
                            camera = Camera.open(findBackFacingCamera());
                        Camera.Parameters params = camera.getParameters();
                        List<String> flashModes = params.getSupportedFlashModes();
                        if (isChecked)
                            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        else
                            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(params);
                        camera.startPreview();
                    } catch (Exception e) {
                        e.printStackTrace();
                        camera.stopPreview();
                        camera.release();
                    }
                }
            });
        } else {
            showAlert();
        }
    }

    private void showAlert() {
        new AlertDialog.Builder(this).setTitle("Warning..").setMessage("Flashlight not support").create();
    }

    public static int findBackFacingCamera() {
        int cameraId = 0;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                Log.d(null, "Camera found.");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }
}
