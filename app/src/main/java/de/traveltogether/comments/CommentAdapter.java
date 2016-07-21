package de.traveltogether.comments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.traveltogether.R;
import de.traveltogether.model.Comment;

/**
 * Created by Anna-Lena on 12.06.2016.
 */
public class CommentAdapter extends BaseAdapter {
    private final Comment[] comments;
    private final LayoutInflater inflater;

    public CommentAdapter(Context context, Comment[] _comments) {
        inflater = LayoutInflater.from(context);
        comments = _comments;
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
            convertView = inflater.inflate(R.layout.fragment_trip_list_item, parent, false);
            holder = new CommentViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.fragment_commentlist_list_item_name);
            holder.date= (TextView)convertView.findViewById(R.id.fragment_commentlist_list_item_date);
            holder.content=(TextView)convertView.findViewById(R.id.fragment_commentlist_list_item_content);
            convertView.setTag(holder);
        }
        else{
            holder = (CommentViewHolder)convertView.getTag();
        }

        Context context = parent.getContext();
        Comment comment = (Comment)getItem(position);
        holder.name.setText(comment.getAuthor());
        holder.date.setText(comment.getDate().toString());
        holder.content.setText(comment.getText());
        return convertView;
    }

    static class CommentViewHolder {
        TextView name, date, content;

    }
}
