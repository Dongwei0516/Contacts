package com.yanis.getmyphonenumber;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;


public class GetNumber {
	public static List<PhoneInfo> lists = new ArrayList<PhoneInfo>();
	public static String getNumber(Context context) {

		lists.clear();
		Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);


		while (cursor.moveToNext()) {
			String phoneNumber;
			String phoneName;
			long id;
//			phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
//			phoneName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
//			id =cursor.getInt(cursor.getColumnIndex(Phone.CONTACT_ID));
			phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//			PhoneInfo info = new PhoneInfo(phoneName, phoneNumber
			PhoneInfo info = new PhoneInfo(phoneName, id);
			lists.add(info);
		}

		return null;
	}
}

