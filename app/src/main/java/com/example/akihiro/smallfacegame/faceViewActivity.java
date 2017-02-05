package com.example.akihiro.smallfacegame;

import android.content.Context;
import android.hardware.Camera.Size;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class faceViewActivity extends AppCompatActivity {

    private  Camera mCamera = null;
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21){
            CameraManager mCamera = null;
        }else{
            Camera mCamera = null;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_view);

    }
    private class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder = null;
        private Camera.Size mPreviewSize = null;

        public CameraPreview(Context context) {
            super(context);

            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //　カメラを開く
            mCamera = Camera.open();
            try {
                // プレビュー表示用のSurfaceHolderの設定
                mCamera.setPreviewDisplay(mHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // プレビューの画面サイズを取得
            Camera.Parameters param = mCamera.getParameters();
            List<Size> supportSize = param.getSupportedPreviewSizes();
            mPreviewSize = supportSize.get(0);	// １つ目のサイズを設定
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            // カメラのパラメータ設定
            Camera.Parameters param = mCamera.getParameters();
            param.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
            mCamera.setParameters(param);

            // プレビューの開始
            mCamera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // カメラのプレビュー終了
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }

        // シャッターが押された時に呼ばれるコールバック
        private Camera.ShutterCallback mShutterListener = new Camera.ShutterCallback() {
            @Override
            public void onShutter() {

            }
        };

        // JPEGイメージ生成後に呼び出されるコールバック
        private Camera.PictureCallback mPictureListener = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // SDカードにJPEGデータを保存する
                if (data != null) {
                    FileOutputStream myFOS = null;
                    try {
                        myFOS = new FileOutputStream("/sdcard/camera_test.jpg");
                        myFOS.write(data);
                        myFOS.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    camera.startPreview();
                }
            }
        };

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (mCamera != null) {
                    // 写真の撮影
                    mCamera.takePicture(mShutterListener, null, mPictureListener);
                    return true;
                }
            }

            return super.onTouchEvent(event);
        };
    }
}
