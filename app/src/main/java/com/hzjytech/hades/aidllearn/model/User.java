package com.hzjytech.hades.aidllearn.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Hades on 2017/12/6.
 */

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
