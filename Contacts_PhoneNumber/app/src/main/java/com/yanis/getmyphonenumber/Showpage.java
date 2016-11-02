package com.yanis.getmyphonenumber;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;

/**
 * Created by dongwei on 2016/10/28.
 */

public class Showpage extends Activity{

    private TextView nameText;
    private TextView numberText;
    private LinearLayout linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showpage);
        nameText = (TextView)findViewById(R.id.showname);
//        numberText = (TextView)findViewById(R.id.shownumber);

        linear_layout = (LinearLayout) findViewById(R.id.linear_layout);


//        TextView numberText = new TextView(this);
//        numberText.setText("111");
//        linear_layout.addView(numberText);

        Intent intent = getIntent();

        long contact_id = intent.getLongExtra("id",id);

        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contact_id, null, null);


        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            this.nameText.setText(name);
            TextView numberText = new TextView(this);
            numberText.setText(number);
            linear_layout.addView(numberText);
        }



//        int index = intent.getIntExtra("position",0);
//        cursor.moveToPosition(index);
//
//        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//        this.nameText.setText(name);
//        this.numberText.setText(number);


        cursor.close();

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){


        Intent intent = getIntent();
        long contact_id = intent.getLongExtra("id",id);

        switch (item.getItemId()){
            case R.id.delete:
                deleteContact(contact_id);
                return true;
            case R.id.edit:
                editContact(id);
        }
        return false;
    }

    public void deleteContact(long rawContactId){
        getContentResolver().delete(ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI,rawContactId),null,null);
        Intent intent = new Intent(Showpage.this, MainActivity.class);
        startActivity(intent);
    }

    public void editContact(long rawContactId){
        getContentResolver().delete(ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI,rawContactId),null,null);
        View dialog_edit = getLayoutInflater().inflate(R.layout.add,null);
        final EditText etName = (EditText)dialog_edit.findViewById(R.id.et_name);
        final EditText etPhone = (EditText)dialog_edit.findViewById(R.id.et_phone);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");

        etName.setText(name);
        etPhone.setText(number);

        AlertDialog.Builder dialog = new AlertDialog.Builder(Showpage.this);
        dialog.setTitle("编辑联系人")
                .setView(dialog_edit)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mName = etName.getText().toString();
                        String mPhone = etPhone.getText().toString();

                        if (mName.equals("")|mPhone.equals("")){
                            Toast.makeText(Showpage.this,"不能为空",Toast.LENGTH_SHORT).show();
                        }else {
                            editMyContact(mName,mPhone);
                            finish();
                        }
                    }
                })
                .setNegativeButton("取消",null).create().show();
    }

    public void editMyContact(String mName, String mPhone){
        ContentValues values = new ContentValues();

        Intent intent = getIntent();

        long contact_id = intent.getLongExtra("id",id);

        values.clear();
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,mName);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,mPhone);

        String Where = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "+ contact_id;

        getContentResolver().update(ContactsContract.Data.CONTENT_URI,values, Where,null);

        Toast.makeText(Showpage.this, "修改成功！",Toast.LENGTH_SHORT).show();

    }
}
