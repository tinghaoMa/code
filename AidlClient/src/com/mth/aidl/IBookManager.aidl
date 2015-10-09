package com.mth.aidl;

import com.mth.aidl.book;

interface IBookManager{
	void addBook(in Book book);
	List<Book> getAllBook();
}