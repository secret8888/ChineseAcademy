package com.stroke.academy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stroke.academy.R;
import com.stroke.academy.model.MeetingDayItem;
import com.stroke.academy.model.MeetingItem;

import java.util.ArrayList;

public class MeetingAdapter extends BaseAdapter{

	private Context context = null;

	private ArrayList<MeetingItem> items = null;

	public MeetingAdapter(Context context, ArrayList<MeetingItem> items){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_meeting_day, parent, false);
			viewHolder.dayView = (TextView) convertView.findViewById(R.id.tv_day);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.dayView.setText(items.get(position).getName());
		return convertView;
	}

	private class ViewHolder{
		private TextView dayView = null;
	}
	
}
