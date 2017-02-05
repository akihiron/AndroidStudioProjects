# Androidアプリの作成
## 小顔になろう！！の作成メモ
### １．必要なツール
開発環境
AndroidStadio2.2  
openCV for Android(ver3.2)  
AndroidStadioやopenCVは以下からダウンロードしました。  
[openCV公式サイト][openCV公式]  
[AndroidStadio公式サイト][AndroidStadio公式]   
設定方法  
以下を参考にする  
[OpenCV for AndroidをAndroid Studioに導入するメモ][openCVforAndroid]  
 ここまででAndroidでopenCVを使用する準備が整った。  
 一様、問題の解決だけ記述しておくと
 project/openCVLibrary320(version)のbuild.gradleのSDKのversionをapp/build.gradleと合わせる必要があった。  
 また、ここのサイトで書かれている通りminSdkVersion 14以上にするとerror logが表示された。  
 恐ろしいので一様14にしておくことにしました。  
 error内容  
> "Gradle sync failed: Failed to find target with hash string 'android-14' in: C:\android-sdk-windows
         Consult IDE log for more details (Help | Show Log)"  

 さらに、実機でapkを実行するにはそれに合ったAPKをインストールする必要があるそうです。  
 SOV32（Xpera）の場合は以下をapkのonCleate()内に追加して実行した所  
 ```java
for(int i=0;i<logs.length;i++) {
        Log.i("CPU_ABI" +Integer.toString(i), logs[i]);
}
```
>I/CPU_ABI0: arm64-v8a  
 I/CPU_ABI1: armeabi-v7a  
 I/CPU_ABI2: armeabi (ARMv5, ARMv6)

以上のように出てきたので一番上のapkをインストールして下のコマンドで  
```
adb install C:\android-sdk-windows\OpenCV-android-sdk\apk/OpenCV_3.2.0_Manager_3.20_arm64-v8a.apk
```
うまくインストールできた。  
これでOpenCVを使う準備ができました  
次に行うのがAndroidのUIの準備をする  
ここで詰まったのがカメラをsurfaceViewに表示する所です。  
まず、カメラのLibraryがＡndroid5.0以降、変更になっている。  
ちなみにAPI21以降はandroid.hardware.camera2.CameraManagerをimport
する必要があり、しばらくは待っていた（カメラのＣが大文字で見つからなかった）  
そこで、APIによって処理を変える[方法][APIによって変更]を調べた　　


[openCVforAndroid]:http://qiita.com/kodai100/items/6c9e8a34d0714913c017 "OpenCVをAndroidアプリで使用する"
[openCV公式]:http://opencv.org/
[AndroidStadio公式]:https://developer.android.com/studio/index.html
[APIによって変更]:http://nobuo-create.net/api/
