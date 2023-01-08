package org.firstinspires.ftc.teamcode;

import android.content.Context;
import android.widget.TextView;

import com.intel.realsense.librealsense.DeviceListener;
import com.intel.realsense.librealsense.RsContext;

public class CameraWrapper {

    private RsContext mRsContext;

    // Used to load the 'cameras' library on application startup.
    static {
        System.loadLibrary("cameras");
    }

public CameraWrapper(Context applicationContext){
    //RsContext.init must be called once in the application's lifetime before any interaction with physical RealSense devices.
    //For multi activities applications use the application context instead of the activity context
    RsContext.init(applicationContext);

    printMessage();

    //Register to notifications regarding RealSense devices attach/detach events via the DeviceListener.
    mRsContext = new RsContext();
    mRsContext.setDevicesChangedCallback(new DeviceListener() {
        @Override
        public void onDeviceAttach() {
            printMessage();
        }

        @Override
        public void onDeviceDetach() {
            printMessage();
        }
    });
}

    private void printMessage(){
        // Example of a call to native methods
        int cameraCount = nGetCamerasCountFromJNI();
        final String version = nGetLibrealsenseVersionFromJNI();
        final String cameraCountString;
        if(cameraCount == 0)
            cameraCountString = "No cameras are currently connected.";
        else
            cameraCountString = "Camera is connected";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(R.id.sample_text);
                tv.setText("This app use librealsense: " + version + "\n" + cameraCountString);
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private static native String nGetLibrealsenseVersionFromJNI();
    private static native int nGetCamerasCountFromJNI();
}
