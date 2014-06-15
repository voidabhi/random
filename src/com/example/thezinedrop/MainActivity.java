package com.example.thezinedrop;

import java.util.ArrayList;
import java.util.Locale;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import app.thezineclient.models.*;
import app.thezineclient.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public static String ARTICLE_DETAIL_KEY = "article";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			
			DummySectionFragment fragment = new DummySectionFragment();
			fragment.setIssue(position+1);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	@SuppressLint("ValidFragment")
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		private ArticleArrayAdapter articleAdapter;
		
		private ProgressDialog p;

		private int issue;
		
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		public DummySectionFragment(){}	
		
		public void setIssue(int issue){
			this.issue = issue;
			Log.d("Issue SET",Integer.toString(issue));
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
			
			Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,container, false);
			ListView lv = (ListView)rootView.findViewById(R.id.listView1);
			
			ArrayList<Article> articles = new ArrayList<Article>();
			articleAdapter = new ArticleArrayAdapter(getActivity(),articles);
			
			lv.setAdapter(articleAdapter);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long rowId) {
					// TODO Auto-generated method stub
					
					
					Intent i = new Intent(getActivity(),ArticleActivity.class);
					i.putExtra(ARTICLE_DETAIL_KEY, articleAdapter.getItem(position));
					startActivity(i);
				}
				
			});
			this.articleAdapter.clear();
			fetchArticles();
			return rootView;
		}
		
	    private void fetchArticles() {
	       
	    	p=new ProgressDialog(getActivity());
	    	p.setTitle("Fetching Articles");
	    	p.show();
	        TheZineClient.getArticles(new JsonHttpResponseHandler() {
	            @Override
	            public void onSuccess(int code, JSONObject body) {
	            	
	            	Log.d("ARTICLES RECEIVED for issue "+issue,body.toString());
	                JSONArray items = null;
	                try {
	                	p.hide();
	                    // Get the movies json array
	                    items = body.getJSONObject("data").getJSONArray("articles");
	                    
	                    ArrayList<Article> articles = new ArrayList<Article>();
	                    
	                    for(int i=0;i<items.length();i++)
	                    {
	                    	Article a = new Article();
	                    	JSONObject jsonObject = items.getJSONObject(i);
	                    	a.setTitle(jsonObject.getString("title"));
	                    	a.setTagline(jsonObject.getString("tagline"));
	                    	a.setId(jsonObject.getString("id"));
	                    	a.setAuthor(Author.fromJson(jsonObject.getJSONObject("author")));
	                    	articles.add(a);
	                    }
	                    // Parse json array into array of model objects
	                    
	                    // Load model objects into the adapter
	                    for (Article article : articles) {
	                       articleAdapter.add(article);
	                    }	                	

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            @Override
	            public void onFailure(Throwable t)
	            {
	            	p.setTitle(t.getMessage());
	            }
	        },this.issue);
	    }		
		
	}

}
