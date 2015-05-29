package us.codewalr.walrifier.ui;

import java.util.ArrayList;

import us.codewalr.walrifier.R;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CreditsAdapter extends BaseAdapter
{
	ArrayList<Credit> credits;
	Context ctx;
	
	public CreditsAdapter(Context ctx, String[] namelist, String[] actlist)
	{
		if (namelist.length != actlist.length)
			throw new IllegalArgumentException("Name list length != act list length");
		credits = new ArrayList<Credit>();
		for (int i=0; i<namelist.length; i++)
			credits.add(new Credit(namelist[i], actlist[i]));
		
		this.ctx = ctx;
	}

	@Override
	public int getCount()
	{
		return credits.size();
	}

	@Override
	public Object getItem(int index)
	{
		return credits.get(index);
	}

	@Override
	public long getItemId(int index)
	{
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent)
	{
		ViewHolder vh;
		if (view == null)
		{
			view = LayoutInflater.from(ctx).inflate(R.layout.creditsitem, parent, false);
			vh = new ViewHolder(view);
			view.setTag(vh);
		}else
			vh = (ViewHolder) view.getTag();
		
		vh.set(credits.get(index));
		
		return view;
	}
	
	public class ViewHolder
	{
		TextView title;
		
		public ViewHolder(View v)
		{
			title = (TextView) v;
		}
		
		public void set(Credit c)
		{
			title.setText(Html.fromHtml("<big><font color='#262626'>"+ c.contributor +"</font></big><br/>" + c.action));
		}
	}
	
	public class Credit
	{
		String contributor, action;
		
		public Credit(String contributor, String action)
		{
			this.contributor = contributor;
			this.action = action;
		}
	}
}
