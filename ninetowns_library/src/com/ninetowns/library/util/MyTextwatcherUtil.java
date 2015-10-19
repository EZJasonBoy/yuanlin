package com.ninetowns.library.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MyTextwatcherUtil implements TextWatcher{
    private EditText mEditText;
    private TextView textCount;
    private int maxLeangth;
    private int editStart;
    private int editEnd;
    private  Context context;
    public MyTextwatcherUtil(Context context,EditText editorText,TextView textCount,int maxLeangth) {
        this.mEditText=editorText;
        this.textCount=textCount;
        this.maxLeangth=maxLeangth;
        this.context=context;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        editStart = mEditText.getSelectionStart();
        editEnd = mEditText.getSelectionEnd();
        
        // 先去掉监听器，否则会出现栈溢出
        mEditText.removeTextChangedListener(this);

        // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
        // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
        while (calculateLength(s.toString()) > maxLeangth) { // 当输入字符个数超过限制的大小时，进行截断操作
            s.delete(editStart - 1, editEnd);
            editStart--;
            editEnd--;
        }
        mEditText.setText(s);
        mEditText.setSelection(editStart);

        // 恢复监听器
        mEditText.addTextChangedListener(this);

        setLeftCount();
    }

    /**
     * 
     * 
     * @param c
     * @return
     */
    private long calculateLength(CharSequence c) {/*//计内容的字数，一个汉字=两个英文字母，一个中文标点=两个英文标点 注意：该函数的不适用于对单个字符进行计算，因为单个字符四舍五入后都是1
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    */
        long len=c.length();
    return len;
    
    
    }

    /**
     * 刷新剩余输入字数
     */
    private void setLeftCount() {
        if(textCount!=null){
            textCount.setText(String.valueOf((maxLeangth - getInputCount()))); 
        }
        if(maxLeangth - getInputCount()<=0){
            ComponentUtil.showToast(context, "不超过"+maxLeangth+"个字");
        }
       
    }

    /**
     * 获取用户输入的内容字数
     * 
     * @return
     */
    private long getInputCount() {
        return calculateLength(mEditText.getText().toString());
    }
}
