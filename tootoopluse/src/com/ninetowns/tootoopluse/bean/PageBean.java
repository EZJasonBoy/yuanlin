package com.ninetowns.tootoopluse.bean;

import java.io.Serializable;

public class PageBean implements Serializable {
	// [{"PageId":21,"Template":1,"ItemIndex":0,"ElementType":1,"Location":1},
	private String PageId;
	private String Template;
	private int ItemIndex;
	private int ElementType;
	private int Location;

	public String getPageId() {
		return PageId;
	}

	public void setPageId(String pageId) {
		PageId = pageId;
	}

	public String getTemplate() {
		return Template;
	}

	public void setTemplate(String template) {
		Template = template;
	}

	public int getItemIndex() {
		return ItemIndex;
	}

	public void setItemIndex(int itemIndex) {
		ItemIndex = itemIndex;
	}

	public int getElementType() {
		return ElementType;
	}

	public void setElementType(int elementType) {
		ElementType = elementType;
	}

	public int getLocation() {
		return Location;
	}

	public void setLocation(int location) {
		Location = location;
	}

	@Override
	public String toString() {
		return "PageBean [PageId=" + PageId + ", Template=" + Template
				+ ", ItemIndex=" + ItemIndex + ", ElementType=" + ElementType
				+ ", Location=" + Location + "]";
	}
}
