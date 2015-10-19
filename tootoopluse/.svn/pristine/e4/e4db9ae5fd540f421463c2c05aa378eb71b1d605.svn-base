package com.ninetowns.tootoopluse.observe;

import java.util.Observable;


public class MyObservable extends Observable {

	private Object view = null;

	public String type;

	public static MyObservable instance;

	private MyObservable() {

	}

	public static MyObservable getInstance() {
		if (instance == null) {
			instance = new MyObservable();
		}
		return instance;
	}

	public Object getView() {
		return view;
	}

	public void setData(Object view) {
		setChanged();
		notifyObservers(view);
		// 只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干�?
	}

	public void setData() {
		setChanged();
		notifyObservers(view);
		// 只有在setChange()被调用后，notifyObservers()才会去调用update()，否则什么都不干�?
	}

}
