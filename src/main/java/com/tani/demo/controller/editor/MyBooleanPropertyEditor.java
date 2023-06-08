package com.tani.demo.controller.editor;

import java.beans.*;

public class MyBooleanPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("===========================================");
		System.out.println(text);
		System.out.println("===========================================");
		if(text.equals("1") || text.equals("true") || text.equals("TEXT"))
			setValue(true);
		else if(text.equals("2") || text.equals("false") || text.equals("TEXT"))
			setValue(false);
	}
}
