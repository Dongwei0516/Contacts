package com.yanis.getmyphonenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView listView;
	private MyAdapter adapter;

	private static final int add = Menu.FIRST;
	private static final int delete = Menu.FIRST+1;
	private static final int edit = Menu.FIRST+2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GetNumber.getNumber(this);

		initView();
	}


	private void initView() {

		listView =(ListView) findViewById(R.id.listView);
		adapter = new MyAdapter(GetNumber.lists, this);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//				Uri uri1 = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
//				startActivity(new Intent(Intent.ACTION_VIEW,uri));

				Intent intent = new Intent(MainActivity.this, Showpage.class);
//				intent.setData(uri1);
//				intent.putExtra("position",position);
//				List<String> name = new ArrayList<String>();
//				name.get(position);
//				intent.putExtra("name", name.get(position));

				String name = ((TextView)view.findViewById(R.id.tv_Name)).getText().toString();
				String number = ((TextView)view.findViewById(R.id.tv_Number)).getText().toString();
				intent.putExtra("id",id);
				intent.putExtra("name", name);
				intent.putExtra("number", number);
				startActivity(intent);

			}
		});

	}


	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		menu.add(0,add , 0,"add");
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case add:
//				Intent intent = new Intent();
//				intent.setAction(Intent.ACTION_INSERT);
//				String data = "content://com.android.contacts/contacts";
//				Uri uri = Uri.parse(data);
//				intent.setData(uri);
//				startActivity(intent);
//				GetNumber.getNumber(this);
				addContact();
		}
		return super.onOptionsItemSelected(item);
	}

	private void addContact(){
		View dialog_add = getLayoutInflater().inflate(R.layout.add,null);
		final EditText etName = (EditText)dialog_add.findViewById(R.id.et_name);
		final EditText etPhone = (EditText)dialog_add.findViewById(R.id.et_phone);

		AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
		dialog.setTitle("添加联系人")
				.setView(dialog_add)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						String mName = etName.getText().toString();
						String mPhone = etPhone.getText().toString();

						if (mName.equals("")|mPhone.equals("")){
							Toast.makeText(MainActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
						}else {
							addMyContact(mName,mPhone);
						}
						Intent intent = new Intent(MainActivity.this, MainActivity.class);
						startActivity(intent);
					}
				})
				.setNegativeButton("取消",null).create().show();
	}

	private void addMyContact(String mName, String mPhone){
		ContentValues values = new ContentValues();
		Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,values);
		long rawContactId = ContentUris.parseId(rawContactUri);

		values.clear();
		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
		values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,mName);
		getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);



		values.clear();
		values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
		values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
		values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,mPhone);
		values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
		getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);

		Toast.makeText(MainActivity.this, "添加成功！",Toast.LENGTH_SHORT).show();
	}

//	@Override
//	public boolean onContextItemSelected(MenuItem item){
//		AdapterView.AdapterContextMenuInfo info;
//		try {
//			info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//		}catch (ClassCastException e){
//			return false;
//		}
//
//		switch (item.getItemId()){
//			case delete:
//				Uri noteUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,info.id);
//				getContentResolver().delete(noteUri, null , null);
//				GetNumber.getNumber(this);
//				return true;
//			case edit:
//				Uri editUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,info.id);
//				startActivity(new Intent(Intent.ACTION_EDIT,editUri));
//				GetNumber.getNumber(this);
//		}
//		return false;
//	}



	@Override
	protected void onResume(){
		super.onResume();
		Intent service = new Intent(this ,ServiceSynchContract.class );
		startService(service);
		initView();

	}


}
