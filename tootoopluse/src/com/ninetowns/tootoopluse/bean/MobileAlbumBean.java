package com.ninetowns.tootoopluse.bean;


public class MobileAlbumBean {
	
	private int mob_album_photo_id;
	
	private String mob_album_folder_name;
	
	private String mob_album_photo_count;
	
	private String mob_album_photo_path;


	public String getMob_album_folder_name() {
		return mob_album_folder_name;
	}

	public void setMob_album_folder_name(String mob_album_folder_name) {
		this.mob_album_folder_name = mob_album_folder_name;
	}

	public String getMob_album_photo_count() {
		return mob_album_photo_count;
	}

	public void setMob_album_photo_count(String mob_album_photo_count) {
		this.mob_album_photo_count = mob_album_photo_count;
	}

	public String getMob_album_photo_path() {
		return mob_album_photo_path;
	}

	public void setMob_album_photo_path(String mob_album_photo_path) {
		this.mob_album_photo_path = mob_album_photo_path;
	}

	public int getMob_album_photo_id() {
		return mob_album_photo_id;
	}

	public void setMob_album_photo_id(int mob_album_photo_id) {
		this.mob_album_photo_id = mob_album_photo_id;
	}

	@Override
	public String toString() {
		return "MobileAlbumBean [mob_album_photo_id=" + mob_album_photo_id
				+ ", mob_album_folder_name=" + mob_album_folder_name
				+ ", mob_album_photo_count=" + mob_album_photo_count
				+ ", mob_album_photo_path=" + mob_album_photo_path + "]";
	}

//	@Override
//	public int compare(File lhs, File rhs) {
//		if (lhs.lastModified() < rhs.lastModified()) {
//			return 1;// 最后修改的照片在前
//		} else {
//			return -1;
//		}
//	}

	
	

}
