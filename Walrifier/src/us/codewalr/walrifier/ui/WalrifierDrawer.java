package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.R;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WalrifierDrawer implements ListView.OnItemClickListener
{
	private ArrayAdapter<String> adapter;
	private String[] items;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private Fragment currentFragment;
	private FragmentManager fm;
	private ActionBar bar;
	private SparseArray<Fragment> fragments;
	
	public WalrifierDrawer(FragmentManager fm, ActionBar ab)
	{
		this.fm = fm;
		this.bar = ab;
		fragments = new SparseArray<Fragment>();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		setFragment(position);
	}
	
	private void setFragment(int position)
	{
		currentFragment = fragments.get(position);
		fm.beginTransaction().replace(R.id.drawer_content_frame, currentFragment).commitAllowingStateLoss();
		drawerList.setItemChecked(position, true);
		bar.setTitle(items[position]);
		drawerLayout.closeDrawer(drawerList);
	}
	
	public void setView(Activity a)
	{
		a.setContentView(R.layout.walrifierdrawer);
		
		items = a.getResources().getStringArray(R.array.drawer_options_names);

		adapter = new ArrayAdapter<>(a, R.layout.draweritem, items);
		
		drawerLayout = (DrawerLayout) a.findViewById(R.id.drawer_layout);
		drawerList = (ListView) a.findViewById(R.id.left_drawer);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(this);
		
		loadFragments(a);
		
		setFragment(0);
	}
	
	public void loadFragments(Activity a)
	{
		fragments.put(0, new FeedFragment());
		fragments.put(1, new SettingsFragment(a));
		fragments.put(2, new CreditsFragment());
	}
}
