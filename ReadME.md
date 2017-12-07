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
[2 使用多线程会造成的问题](#2)

<span id="2">2 使用多线程会造成的问题<span>
1. 静态成员和单例模式完成失效
2. 线程同步机制完全失效
3. SharedPreferences的可靠性下降
4. Application会多次创建

[3 接口](#3)

<span id="3">3 接口<span>

[3.1 SerialVersionUID](#31)

<span id="31">3.1 SerialVersionUID<span>
1. SerialVersionUID是用来辅助序列化和反序列化过程的，原则上序列化后的数据中的serialVersionUID最近偶皮金额当前类的
SerialVersionUID相同才能正常地被反序列化。
2. 序列化的时候系统会去检测文件中的serialVersionUID，看它是否和当前类的serialVersionUID一致，如果一致就说明序列化的类的版本和
当前类的版本是相同的，这个时候可以成功的反序列化；否则就说明当前类和序列化的类相比发生了某些变化
3. 静态成员变量属于类不属于对象，所以不会参与序列化过程，用transient关键字标记的成员变量不参与序列化过程；

[3.2 Parcelable接口](#32)

<span id="32">3.2 Parcelable接口<span>

```User.Class
public class User implements Parcelable,Serializable{

    public int userId;
    public String userName;
    public boolean isMale;
    public Book book;

    transient public String tansient;


    /**
     * 从序列化后的对象中创建原始对象
     * @param in
     */
    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    /**
     * 将当前对象写入序列化结构中
     * @param dest
     * @param flags 0 或者 1
     *              1 标志当前对象需要作为返回值返回，不能立即释放资源
     *              0 几乎所有情况都为0
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(book, flags);
    }

    /**
     * 返回当前对象的内容描述
     * 如果含有文件描述符，返回1否则返回0
     * 几乎所有情况都返回0
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        /**
         * 从序列化后的对象中创建原始对象
         * @param in
         * @return
         */
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        /**
         * 创建指定长度的原始对象数组
         * @param size
         * @return
         */
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                ", book=" + book +
                '}';
    }
}

```
[3.3 Parcelable与Serializable对比](#33)

<span id="33">3.3 Parcelable与Serailizable对比<span>
- Parcelable主要用在内存序列化上，更适合用在Android平台上；
- Serializable适合 __将对象序列化到存储设备中__ 或者 __将对象序列化后通过网络传输__ ；

[4 Binder](#4)
<span id="4"><span>