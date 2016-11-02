package com.yanis.getmyphonenumber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

	private List<PhoneInfo> lists;
	private Context context;
	
	public MyAdapter(List<PhoneInfo> lists,Context context)
	{
		this.lists = lists;
		this.context = context;
	}




	@Override
	public int getCount() {
		return lists.size();
	}


	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}


	@Override
	public long getItemId(int position) {
		return lists.get(position).getContactId();
}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		if(convertView==null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.call,null);
			holder = new ViewHolder();
			holder.tvName =  (TextView)convertView.findViewById(R.id.tv_Name);
			holder.tvNumber =  (TextView)convertView.findViewById(R.id.tv_Number);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tvName.setText(lists.get(position).getPhoneName());
		holder.tvNumber.setText(lists.get(position).getPhoneNumber());
		return convertView;
	}
	
	private static class ViewHolder{
		TextView tvName;
		TextView tvNumber;
	}

}
