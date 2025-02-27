package com.example.myapplicationcomposesample.wikitude;

import com.wikitude.NativeStartupConfiguration;
import com.wikitude.WikitudeSDK;
import com.wikitude.common.WikitudeError;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.rendering.ExternalRendering;
import com.example.myapplicationcomposesample.rendering.external.CustomSurfaceView;
import com.example.myapplicationcomposesample.rendering.external.Driver;
import com.example.myapplicationcomposesample.rendering.external.GLRenderer;
import com.example.myapplicationcomposesample.rendering.external.OccluderCube;
import com.example.myapplicationcomposesample.rendering.external.StrokedCube;
import com.wikitude.tracker.ObjectTarget;
import com.wikitude.tracker.ObjectTracker;
import com.wikitude.tracker.ObjectTrackerListener;
import com.wikitude.tracker.TargetCollectionResource;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ObjectTrackingActivity extends Activity implements ObjectTrackerListener, ExternalRendering {

    private static final String TAG = "SimpleObjectTracking";

    private WikitudeSDK wikitudeSDK;
    private CustomSurfaceView customSurfaceView;
    private Driver driver;
    private GLRenderer glRenderer;
    private TargetCollectionResource targetCollectionResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wikitudeSDK = new WikitudeSDK(this);
        NativeStartupConfiguration startupConfiguration = new NativeStartupConfiguration();
        startupConfiguration.setLicenseKey(WikitudeSDKConstants.WIKITUDE_SDK_KEY);
        startupConfiguration.setCameraPosition(CameraSettings.CameraPosition.BACK);
        startupConfiguration.setCameraResolution(CameraSettings.CameraResolution.AUTO);

        wikitudeSDK.onCreate(getApplicationContext(), this, startupConfiguration);

        targetCollectionResource = wikitudeSDK.getTrackerManager().createTargetCollectionResource("file:///android_asset/firetruck.wto");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        5);

                wikitudeSDK.getTrackerManager().createObjectTracker(targetCollectionResource, ObjectTrackingActivity.this, null);
            }
        } else {
            wikitudeSDK.getTrackerManager().createObjectTracker(targetCollectionResource, ObjectTrackingActivity.this, null);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wikitudeSDK.onResume();
        customSurfaceView.onResume();
        driver.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        customSurfaceView.onPause();
        driver.stop();
        wikitudeSDK.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wikitudeSDK.onDestroy();
    }

    @Override
    public void onRenderExtensionCreated(final RenderExtension renderExtension) {
        glRenderer = new GLRenderer(renderExtension);
        wikitudeSDK.getCameraManager().setRenderingCorrectedFovChangedListener(glRenderer);
        customSurfaceView = new CustomSurfaceView(getApplicationContext(), glRenderer);
        driver = new Driver(customSurfaceView, 30);
        setContentView(customSurfaceView);
    }

    @Override
    public void onTargetsLoaded(ObjectTracker tracker) {
        Log.v(TAG, "Object tracker loaded");
    }

    @Override
    public void onErrorLoadingTargets(ObjectTracker tracker, WikitudeError error) {
        Log.v(TAG, "Unable to load image tracker. Reason: " + error.getMessage());
    }

    @Override
    public void onObjectRecognized(ObjectTracker tracker, final ObjectTarget target) {
        Log.v(TAG, "Recognized target " + target.getName());

        StrokedCube strokedCube = new StrokedCube();
        OccluderCube occluderCube = new OccluderCube();

        glRenderer.setRenderablesForKey(target.getName(), strokedCube, occluderCube);
    }

    @Override
    public void onObjectTracked(ObjectTracker tracker, final ObjectTarget target) {
        StrokedCube strokedCube = (StrokedCube) glRenderer.getRenderableForKey(target.getName());
        if (strokedCube != null) {
            strokedCube.viewMatrix = target.getViewMatrix();

            strokedCube.setXScale(target.getTargetScale().x);
            strokedCube.setYScale(target.getTargetScale().y);
            strokedCube.setZScale(target.getTargetScale().z);
        }

        OccluderCube occluderCube = (OccluderCube) glRenderer.getOccluderForKey(target.getName());
        if (occluderCube != null) {
            occluderCube.viewMatrix = target.getViewMatrix();

            occluderCube.setXScale(target.getTargetScale().x);
            occluderCube.setYScale(target.getTargetScale().y);
            occluderCube.setZScale(target.getTargetScale().z);
        }
    }

    @Override
    public void onObjectLost(ObjectTracker tracker, final ObjectTarget target) {
        Log.v(TAG, "Lost target " + target.getName());
        glRenderer.removeRenderablesForKey(target.getName());
    }

    @Override
    public void onExtendedTrackingQualityChanged(final ObjectTracker tracker, final ObjectTarget target, final int oldTrackingQuality, final int newTrackingQuality) {

    }
}
