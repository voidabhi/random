/**
 * 
 */
package com.example.thezinedrop;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import app.thezineclient.TheZineClient;
import app.thezineclient.models.Article;
import app.thezineclient.models.Author;

/**
 * @author ABHIJEET
 *
 */
public class ArticleActivity extends Activity {

	TextView title;
	TextView author;
	TextView body;
	
	ProgressDialog p;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
		this.setContentView(R.layout.article_activity);
		
		title = (TextView)findViewById(R.id.article_title);
		author = (TextView)findViewById(R.id.article_author);
		body = (TextView)findViewById(R.id.article_body);
		p = new ProgressDialog(this);
		
		Article article = (Article)getIntent().getSerializableExtra(MainActivity.ARTICLE_DETAIL_KEY);
		
		loadArticle(article);

	}
	
	public void loadArticle(final Article article){
		
			p.setTitle("Fetching "+article.getTitle());
			p.show();
		  TheZineClient.getArticle(new JsonHttpResponseHandler() {
	            @Override
	            public void onSuccess(int code, JSONObject jsonbody) {

	                try {
	                	p.hide();
	                	Article a= Article.fromJson(jsonbody.getJSONObject("data"));
	                	
	            		title.setText(a.getTitle());
	            		author.setText(a.getAuthor().getName());
	            		body.setText(Html.fromHtml(a.getBody()));

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
		  },article.getId());
	}	

}
