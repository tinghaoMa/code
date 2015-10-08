package com.mth.protocol;

public class BaiduProtocl extends BaseProtocol<String> {
	private static final String URL = "http://www.baidu.com";

	@Override
	public String makeRequest() {
		return URL;
	}

	@Override
	public String handResponse(String response) {
		return response;
	}

	@Override
	public void handError(int error) {

	}

}
