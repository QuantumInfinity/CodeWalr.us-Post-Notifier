package us.codewalr.walrifier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
	private String[] mDataset;

	public MyAdapter(String[] myDataset)
	{
		mDataset = myDataset;
	}

	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.walrifiercard, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		((TextView) holder.mv.findViewById(R.id.title)).setText(mDataset[position]);
	}

	@Override
	public int getItemCount()
	{
    	return mDataset.length;
    }
    
	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public View mv;
		public ViewHolder(View v)
		{
			super(v);
			mv = v;
		}
	}
}
