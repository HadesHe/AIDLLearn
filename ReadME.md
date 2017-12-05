[1 开启多线程](#1)

<span id="1">1 开启多线程<span>
>四大组件在AndroidManifest中指定android:process属性
```
<manifest
    package="com.example.hades">

    //私有进程，其他应用的组件不可以和他
    <activity
      android:name="com.example.hades.RemoteActivity"
      android:process=":remote"
    />
    <activity
      android:name="com.example.hades.Remote2Activity"
     android:process="com.example.hades.remote2"
    />
</manifest>
```