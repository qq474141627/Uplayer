package com.opar.mobile.uplayer.ui;



import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.opar.mobile.uplayer.R;
import com.opar.mobile.uplayer.ui.adapter.MainFragmentAdapter;

public class MainActivity extends SherlockFragmentActivity implements OnClickListener{
	public static final int TAB_MINE = 0;
	public static final int TAB_CATAGORY = 1;
	public static final int TAB_CLASSIFY = 2;

	private ViewPager vPager;
	private RadioButton main_tab_mine, main_tab_catagory, main_tab_classify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		main_tab_mine = (RadioButton) findViewById(R.id.main_tab_mine);
		main_tab_catagory = (RadioButton) findViewById(R.id.main_tab_catagory);
		main_tab_classify = (RadioButton) findViewById(R.id.main_tab_classify);
		main_tab_mine.setOnClickListener(this);
		main_tab_catagory.setOnClickListener(this);
		main_tab_classify.setOnClickListener(this);
		
		List<SherlockFragment> list=new ArrayList<SherlockFragment>();
		list.add(new Fragment_Mine());
		list.add(new Fragment_Playlist());
		list.add(new Fragment_Show());
		MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(),list);
		
		vPager = (ViewPager) findViewById(R.id.vPager);
		vPager.setAdapter(adapter);
		vPager.setOffscreenPageLimit(list.size());
		addListener();
		
	}

	private void addListener() {
		vPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int id) {
				switch (id) {
				case TAB_MINE:
					main_tab_mine.setChecked(true);
					break;
				case TAB_CATAGORY:
					main_tab_catagory.setChecked(true);
					break;
				case TAB_CLASSIFY:
					main_tab_classify.setChecked(true);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_tab_mine:
			vPager.setCurrentItem(TAB_MINE,false);
			break;
		case R.id.main_tab_catagory:
			vPager.setCurrentItem(TAB_CATAGORY,false);
			break;
		case R.id.main_tab_classify:
			vPager.setCurrentItem(TAB_CLASSIFY,false);
			break;
		default:
			break;
		}		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
    	         break;
            case R.id.menu_search:
            	startActivity(new Intent(this, Activity_Search.class));
    	         break;
               }
         return super.onOptionsItemSelected(item);
     }
    
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
     	   if ((System.currentTimeMillis() - exitTime) > 2000)
            {
               Toast.makeText(this, getString(R.string.exit_click), Toast.LENGTH_SHORT).show();
               exitTime = System.currentTimeMillis();
            } else
            {
         	   System.exit(0);
            }
    		return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
}
