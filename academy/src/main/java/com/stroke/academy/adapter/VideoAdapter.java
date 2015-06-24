package com.stroke.academy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.stroke.academy.R;
import com.stroke.academy.model.VideoItem;

import java.util.ArrayList;

public class VideoAdapter extends BaseAdapter{

	private Context context = null;

	private ArrayList<VideoItem> items = null;

	private DisplayImageOptions options;

	public VideoAdapter(Context context, ArrayList<VideoItem> items){
		this.context = context;
		this.items = items;

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisk(true).build();
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_video, parent, false);
			viewHolder.indexView = (ImageView) convertView.findViewById(R.id.im_index);
			viewHolder.titleView = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.contentView = (TextView) convertView.findViewById(R.id.tv_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder)convertView.getTag();
		}

		ImageLoader.getInstance().displayImage(items.get(position).getImgURL(), viewHolder.indexView, options);
		viewHolder.titleView.setText(items.get(position).getName());
		viewHolder.contentView.setText(items.get(position).getDescription());
		return convertView;
	}

	private static class ViewHolder{
		private ImageView indexView;
		private TextView titleView;
		private TextView contentView;
	}
	
}
