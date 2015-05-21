package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.feed.FeedView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedFragment extends Fragment
{
	private FeedView feedView;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		feedView = new FeedView();	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (container == null)
			return null;
		View v = feedView.setFragment(container, inflater);
		feedView.updateFeed();
		return v;
	}
}
