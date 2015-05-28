package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CreditsFragment extends Fragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View creditsView = inflater.inflate(R.layout.credits, container, false);
		ListView creditsList = (ListView) creditsView.findViewById(R.id.creditsListView);
		String[] items = getResources().getStringArray(R.array.credits_names);
		creditsList.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.creditsitem, items));
		return creditsView;
	}
}
