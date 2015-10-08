package com.mth.bean;

public class FolderBean {
	/**
	 * 当前文件夹的路径
	 */
	private String dir;
	private String firstImgPath;
	private String name;
	private int count;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
		int lastIndex = this.dir.lastIndexOf("/");
		this.name = this.dir.substring(lastIndex);
	}

	public String getFirstImgPath() {
		return firstImgPath;
	}

	public void setFirstImgPath(String firstImgPath) {
		this.firstImgPath = firstImgPath;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "FolderBean [dir=" + dir + ", firstImgPath=" + firstImgPath
				+ ", name=" + name + ", count=" + count + "]";
	}

}
