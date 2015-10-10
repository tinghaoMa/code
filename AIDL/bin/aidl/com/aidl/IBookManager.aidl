package com.aidl;
import com.aidl.Book;
import com.aidl.IOnNewBookListener;

interface IBookManager{
	void addBook(in Book book);
	List<Book> getBookList();
	void registerListener(IOnNewBookListener listener);
	void unRegisterListener(IOnNewBookListener listener);
}