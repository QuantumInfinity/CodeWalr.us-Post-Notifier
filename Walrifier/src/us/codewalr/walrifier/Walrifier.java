package us.codewalr.walrifier;

import us.codewalr.walrifier.service.WalriiService;
import us.codewalr.walrifier.ui.WalrifierDrawer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Walrifier extends AppCompatActivity
{
	public static final String TAG = "Walrifier";

	private WalrifierDrawer walrusDrawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		System.out.println(startService(new Intent(this, WalriiService.class)));
		
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setIcon(R.drawable.walrii_0);
		
		walrusDrawer = new WalrifierDrawer(getSupportFragmentManager(), getSupportActionBar());
		walrusDrawer.setView(this);
	}
	
	public static int getDP(DisplayMetrics m, int pixels)
	{
		return (pixels * m.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}
	
	public static int getPixels(DisplayMetrics m, float dp)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, m);
	}
}
