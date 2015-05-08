package us.codewalr.walrifier;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Walrifier extends Activity
{
	static Walrifier instance;
	
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter<MyAdapter.ViewHolder> mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		setContentView(R.layout.walrifierlayout);
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

		mRecyclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		mAdapter = new MyAdapter(new String[]{"test0", "test1", "test2", "test3", "test4", "test5", "test6", "test7"});
		mRecyclerView.setAdapter(mAdapter);
	}
	
	public static Walrifier getInstance()
	{
		return instance;
	}
	
	public static Context getContext()
	{
		return (Context) instance;
	}
}
