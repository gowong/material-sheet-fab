package com.gordonwong.materialsheetfab.sample.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.gordonwong.materialsheetfab.sample.R;

/**
 * Created by Gordon Wong on 7/18/2015.
 * 
 * Note model.
 */
public class Note {

	private String title;
	private String note;
	private String info;
	private int color;

	private Note(String title, String note, String info, int color) {
		this.title = title;
		this.note = note;
		this.info = info;
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public static Note randomNote(Context context) {
			return new Note("", "", "", 0);
	}

}
