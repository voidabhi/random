package com.example.thezinedrop;

import java.util.ArrayList;

import com.example.thezinedrop.MainActivity.DummySectionFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.thezineclient.models.Article;

@SuppressLint("ResourceAsColor")
public class ArticleArrayAdapter extends ArrayAdapter<Article> {
	
	Context ctx;

	public ArticleArrayAdapter(Context context, ArrayList<Article> articles) {
		// TODO Auto-generated constructor stub
		super(context,0,articles);
		this.ctx = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
                // TODO: Complete the definition of the view for each movie
        // Get the data item for this position
        Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
        	LayoutInflater inflater = LayoutInflater.from(getContext());
        	convertView = inflater.inflate(R.layout.list_item_row, parent, false);
        }
        // Lookup views within item layout
        TextView headingTitle = (TextView) convertView.findViewById(R.id.title);
        TextView authorTitle = (TextView) convertView.findViewById(R.id.author);
        // Setting font
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Merriweather-Regular.otf");
        headingTitle.setTypeface(font);
        
        // Populate the data into the template view using the data object
        headingTitle.setText(article.getTitle());
        authorTitle.setText(article.getAuthor().getName());
        // Return the completed view to render on screen
        return convertView;
	}	

}
