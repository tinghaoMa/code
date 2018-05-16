package com.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String name;
    private int price;

    public Book(Parcel source) {
        name = source.readString();
        price = source.readInt();
    }

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Creator<Book>() {

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }

        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }
    };

    @Override
    public String toString() {
        return "Book [name=" + name + ", price=" + price + "]";
    }
}
