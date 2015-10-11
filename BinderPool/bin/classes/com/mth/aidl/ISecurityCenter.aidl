package com.mth.aidl;

interface ISecurityCenter{
	String encrypt(String content);
	String decrypt(String content);
}