package de.traveltogether.comments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringEscapeUtils;

import de.traveltogether.R;
import de.traveltogether.StaticData;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Comment;


class CommentAdapter extends BaseAdapter {
    private Comment[] comments;
    private final LayoutInflater inflater;

    public CommentAdapter(Context context, Comment[] _comments) {
        inflater = LayoutInflater.from(context);
        if(_comments!=null){
            comments = _comments;
        }
    }

    @Override
    public int getCount() {
        return comments.length;
    }

    @Override
    public Object getItem(int position) {
        if(comments.length>0) {
            return comments[position];
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_commentlist_list_item, parent, false);
            holder = new CommentViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_commentlist_list_item_name);
            holder.date= (TextView)convertView.findViewById(R.id.fragment_commentlist_list_item_date);
            holder.content=(TextView)convertView.findViewById(R.id.fragment_commentlist_list_item_content);
            holder.icon = (FrameLayout)convertView.findViewById(R.id.fragment_commentlist_list_item_icon);
            convertView.setTag(holder);
        }
        else{
            holder = (CommentViewHolder)convertView.getTag();
        }

        Log.d("holder", holder.toString());
        Context context = parent.getContext();
        Comment comment = (Comment)getItem(position);
        if(StaticTripData.getNameById((int)comment.getId())!=null){
            holder.name.setText(StaticTripData.getNameById((int)comment.getId()));
        }
        else{
            //TODO: Problem!!
        }
        holder.date.setText(comment.getDate());
        holder.content.setText(StringEscapeUtils.unescapeJava(comment.getText()));
        ((ImageView)holder.icon.findViewById(R.id.fragment_commentlist_list_item_icon_circle))
                .setBackgroundResource(StaticData.getIdForColor(StaticTripData.getColorById((int)comment.getId())));
        ((TextView)holder.icon.findViewById(R.id.fragment_commentlist_list_item_icon_initial))
                .setText(StaticTripData.getNameById((int)comment.getId()).substring(0,1));
        return convertView;
    }

    static class CommentViewHolder {
        TextView name, date, content;
        FrameLayout icon;
    }
}
