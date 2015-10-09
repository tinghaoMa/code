/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\mth\\worksapce\\test\\AidlServer\\src\\com\\mth\\aidl\\IBookManager.aidl
 */
package com.mth.aidl;
public interface IBookManager extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.mth.aidl.IBookManager
{
private static final java.lang.String DESCRIPTOR = "com.mth.aidl.IBookManager";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.mth.aidl.IBookManager interface,
 * generating a proxy if needed.
 */
public static com.mth.aidl.IBookManager asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.mth.aidl.IBookManager))) {
return ((com.mth.aidl.IBookManager)iin);
}
return new com.mth.aidl.IBookManager.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_addBook:
{
data.enforceInterface(DESCRIPTOR);
com.mth.aidl.Book _arg0;
if ((0!=data.readInt())) {
_arg0 = com.mth.aidl.Book.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.addBook(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAllBook:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.mth.aidl.Book> _result = this.getAllBook();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.mth.aidl.IBookManager
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void addBook(com.mth.aidl.Book book) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((book!=null)) {
_data.writeInt(1);
book.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<com.mth.aidl.Book> getAllBook() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.mth.aidl.Book> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAllBook, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.mth.aidl.Book.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getAllBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void addBook(com.mth.aidl.Book book) throws android.os.RemoteException;
public java.util.List<com.mth.aidl.Book> getAllBook() throws android.os.RemoteException;
}
