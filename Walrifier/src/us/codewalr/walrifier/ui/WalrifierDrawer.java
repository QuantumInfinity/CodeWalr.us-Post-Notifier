package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.R;
import us.codewalr.walrifier.Walrifier;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WalrifierDrawer implements ListView.OnItemClickListener
{
	private ArrayAdapter<String> adapter;
	private String[] items, classes;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private Fragment currentFragment;
	private FragmentManager fm;
	private ActionBar bar;
	
	public WalrifierDrawer(FragmentManager fm, ActionBar ab)
	{
		this.fm = fm;
		this.bar = ab;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		setFragment(position);
	}
	
	private void setFragment(int position)
	{
		instantiateFragment(position);
		fm.beginTransaction().replace(R.id.drawer_content_frame, currentFragment).commitAllowingStateLoss();
		drawerList.setItemChecked(position, true);
		bar.setTitle(items[position]);
		drawerLayout.closeDrawer(drawerList);
	}
	
	private void instantiateFragment(int position)
	{
		try {
			currentFragment = (Fragment) Class.forName(classes[position]).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			Log.e(Walrifier.TAG, "Error instiating class: " + classes[position]);
		}
	}
	
	public void setView(Activity a)
	{
		a.setContentView(R.layout.walrifierdrawer);
		
		items = a.getResources().getStringArray(R.array.drawer_options_names);
		classes = a.getResources().getStringArray(R.array.drawer_options_classes);
		if (items.length != classes.length)
			throw new IllegalArgumentException("Error: Names and classes lists should be the same length!");
		adapter = new ArrayAdapter<>(a, R.layout.draweritem, items);
		
		drawerLayout = (DrawerLayout) a.findViewById(R.id.drawer_layout);
		drawerList = (ListView) a.findViewById(R.id.left_drawer);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(this);
				
		//setFragment(0);
	}
}
