package apps.phani.com.listviewexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import apps.phani.com.listviewexample.R;

class MyAdapter extends ArrayAdapter<String>{

    public MyAdapter(Context context, String[] resource) {
        super(context, R.layout.row_layout2, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.row_layout2, parent, false);
        String name = getItem(position);
        TextView theTextView = (TextView) theView.findViewById(R.id.textView2);
        theTextView.setText(name);

        ImageView theImageView = (ImageView) theView.findViewById(R.id.theImageView1);
        theImageView.setImageResource(R.mipmap.dot);
        return theView;
    }
}