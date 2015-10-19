package com.ninetowns.library.util;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherUtil implements TextWatcher{
	private int editextstart;
	private int editextend;
	private EditText editext;
	private int maxTextConut;
	private int minTextCount;
	private CharSequence temp;

	public TextWatcherUtil(EditText editext,int maxTextConut,int minTextCount){
		this.editext=editext;
		this.maxTextConut=maxTextConut;
		this.minTextCount=minTextCount;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {//
		
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		 Editable editable = editext.getText();  
	        int len = editable.length();  
	        //大于最大长度  
	        if(len > maxTextConut&&len<minTextCount){  
	            int selEndIndex = Selection.getSelectionEnd(editable);  
	            String str = editable.toString();  
	            //截取新字符串  
	            String newStr = str.substring(0, maxTextConut);  
	            editext.setText(newStr);  
	            editable = editext.getText();  
	            //新字符串长度  
	            int newLen = editable.length();  
	            //旧光标位置超过字符串长度  
	            if(selEndIndex > newLen){  
	                selEndIndex = editable.length();  
	            }  
	            //设置新的光标所在位置  
	            Selection.setSelection(editable, selEndIndex);  
	        }  
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}


  
}  

