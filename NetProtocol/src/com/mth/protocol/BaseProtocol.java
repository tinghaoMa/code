package com.mth.protocol;

import org.apache.http.HttpStatus;

import com.mth.engine.HttpEngine;

public abstract class BaseProtocol<T> {
	public T t;

	public abstract String makeRequest();

	public abstract T handResponse(String response);

	public abstract void handError(int error);

	public T request() {
		int code = HttpEngine.getInstance().excuteGet(this);
		if (code == HttpStatus.SC_OK) {
			return t;
		} else {
			handError(code);
			return null;
		}
	}

	public void setResult(T t) {
		this.t = t;
	}
}
