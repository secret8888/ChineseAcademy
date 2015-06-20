package com.stroke.academy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.model.MeetingItem;
import com.stroke.academy.model.MessageItem;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter{

	private Context context = null;

	private ArrayList<MessageItem> items = null;

	public MessageAdapter(Context context, ArrayList<MessageItem> items){
		this.context = context;
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_message, parent, false);
			viewHolder.typeView = (TextView) convertView.findViewById(R.id.tv_day);
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.typeView.setText(items.get(position).getType());
		viewHolder.titleView.setText(items.get(position).getTitle());
		return convertView;
	}

	private static class ViewHolder{
		private TextView typeView;
		private TextView titleView;
	}
	
}
