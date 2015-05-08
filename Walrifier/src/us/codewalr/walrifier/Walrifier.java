package us.codewalr.walrifier;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class Walrifier extends Activity
{
	static Walrifier instance;
	
	View view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		instance = this;
		
		setView(new WalrifierView(this));
	}
	
	public static Walrifier getInstance()
	{
		return instance;
	}
	
	public static Context getContext()
	{
		return instance;
	}
	
	public void setView(View v)
	{
		this.view = v;
		setContentView(v);
	}
}
