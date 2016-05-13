package com.test.huangxingli.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huangxingli on 2015/3/27.
 */
public class Girl  implements Parcelable{

    String name;
    int age;

    public Girl() {

    }

    public String getName() {

        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);

    }

    public static final Creator<Girl> CREATOR=new Creator<Girl>() {
        @Override
        public Girl createFromParcel(Parcel source) {
            Girl girl=new Girl();
            girl.setName(source.readString());
            girl.setAge(source.readInt());
            return girl;
        }

        @Override
        public Girl[] newArray(int size) {
            return new Girl[size];
        }
    };
}
