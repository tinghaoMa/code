package com.aidl;
import com.aidl.Book;

interface IOnNewBookListener{
	void OnNewBookArrviedListener(in Book book);
}