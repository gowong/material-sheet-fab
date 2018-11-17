package com.gordonwong.materialsheetfab.sample.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.content.Context;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;
import android.text.format.DateFormat;

import com.gordonwong.materialsheetfab.sample.R;

/**
 * Created by Gordon Wong on 7/18/2015.
 *
 * Note model.
 */
public class Note {

	private static final String[] ACTIONS_PEOPLE = { "call", "email", "meet up with",
			"hang out with" };
	private static final String[] ACTIONS_OBJECTS = { "clean", "buy", "sell", "fix" };
	private static final String[] NAMES = { "Sherry", "Gordon", "Tom", "Kevin", "Brian", "Naomi",
			"Ali", "Jennifer" };
	private static final String[] OBJECTS = { "desk", "car", "motorcycle", "computer", "laptop" };
	private static final String WORDS = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.";
	private static final String[] CITIES = { "San Francisco", "Campbell", "Lincoln", "New York",
			"Silverton", "Scarface", "King Salmon" };

	private static final String[] LIST_TITLES = { "shopping", "to bring", "on sale", "look for",
			"buy", "get rid of" };
	private static final String[] LIST_DELIMITERS = { "•", "-" };
	private static final String[] LIST_GROCERIES = { "almond milk", "coconut water", "cucumber",
			"green apples" };
	private static final String[] LIST_CAMPING = { "lantern", "smores", "extra blankets",
			"warm socks", "first aid kit", "tent" };

	private static final int NUM_WORDS = 4;
	private static final int DATE_RANGE = 60;

	private String title;
	private String note;
	private String info;
	@DrawableRes
	private int infoImage;
	private int color;

	private Note(String title, String note, String info, int infoImage, int color) {
		this.title = title;
		this.note = note;
		this.info = info;
		this.infoImage = infoImage;
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

	public int getInfoImage() {
		return infoImage;
	}

	public void setInfoImage(int infoImage) {
		this.infoImage = infoImage;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public static Note randomNote(Context context) {
		double rand = Math.random();
		String title = "";
		String note = "";
		NoteInfo info = new NoteInfo("", 0);
		int color = getRandomColor(context);

		// Title only
		if (rand >= 0.65) {
			title = getRandomActivity();
			if (Math.random() >= 0.7) {
				info = getRandomDate(context);
			}
		}
		// Title and note
		else if (rand >= 0.3) {
			title = getRandomActivity();
			note = getRandomWords();
			if (Math.random() >= 0.7) {
				info = getRandomInfo(context);
			}
		}
		// Lists
		else {
			title = getRandomListTitle();
			note = getRandomList();
			if (Math.random() >= 0.7) {
				info = getRandomLocation();
			}
		}

		return new Note(capitalize(title), note, info.info, info.infoImage, color);
	}

	private static String getRandomActivity() {
		if (Math.random() >= 0.5) {
			return getRandomString(false, ACTIONS_PEOPLE) + " " + getRandomString(false, NAMES);
		} else {
			return getRandomString(false, ACTIONS_OBJECTS) + " " + getRandomString(false, OBJECTS);
		}
	}

	private static String getRandomWords() {
		int rand = (int) (Math.random() * NUM_WORDS) + 1;
		String words = "";
		for (int i = 0; i < rand; i++) {
			words += WORDS;
			if (i != rand - 1) {
				words += " ";
			}
		}
		return words;
	}

	private static String getRandomListTitle() {
		String title = getRandomString(true, LIST_TITLES);
		if (!TextUtils.isEmpty(title)) {
			title += ":";
		}
		return title;
	}

	private static String getRandomList() {
		String[] list = (String[]) getRandomItem(new Object[] { LIST_GROCERIES, LIST_CAMPING });
		String delimiter = getRandomString(true, LIST_DELIMITERS);
		if (!TextUtils.isEmpty(delimiter)) {
			delimiter += " ";
		}
		String listStr = "";
		for (int i = 0; i < list.length; i++) {
			listStr += delimiter + list[i];
			if (i != list.length - 1) {
				listStr += "\n";
			}
		}
		return listStr;
	}

	private static NoteInfo getRandomInfo(Context context) {
		NoteInfo[] infos = new NoteInfo[] { getRandomDate(context), getRandomLocation() };
		return (NoteInfo) getRandomItem(infos);
	}

	private static NoteInfo getRandomDate(Context context) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, (int) (Math.random() * DATE_RANGE));
		String date = DateFormat.getMediumDateFormat(context).format(cal.getTime());
		return new NoteInfo(date, R.drawable.ic_event_white_24dp);
	}

	private static NoteInfo getRandomLocation() {
		String location = getRandomString(false, CITIES);
		return new NoteInfo(location, R.drawable.ic_place_white_24dp);
	}

	private static int getRandomColor(Context context) {
		int[] colors;
		if (Math.random() >= 0.6) {
			colors = context.getResources().getIntArray(R.array.note_accent_colors);
		} else {
			colors = context.getResources().getIntArray(R.array.note_neutral_colors);
		}
		return colors[((int) (Math.random() * colors.length))];
	}

	private static String capitalize(String str) {
		if (TextUtils.isEmpty(str)) {
			return str;
		}
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	private static String getRandomString(boolean includeEmpty, String[] strings) {
		if (includeEmpty) {
			ArrayList<String> stringsWithEmpty = new ArrayList<>(Arrays.asList(strings));
			stringsWithEmpty.add("");
			return (String) getRandomItem(stringsWithEmpty.toArray());
		}
		return (String) getRandomItem(strings);
	}

	private static Object getRandomItem(Object[] objs) {
		return objs[((int) (Math.random() * objs.length))];
	}

	private static class NoteInfo {

		private String info;
		private int infoImage;

		private NoteInfo(String info, int infoImage) {
			this.info = info;
			this.infoImage = infoImage;
		}

	}
}
