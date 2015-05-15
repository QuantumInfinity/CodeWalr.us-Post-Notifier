package us.codewalr.walrifier.util;

import java.util.LinkedList;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AnimationQueue extends LinkedList<Animation>
{
	private static final long serialVersionUID = 2106557912463548904L;

	View view;
	AnimationListener onFinish;
	
	public AnimationQueue(View v)
	{
		this.view = v;
		onFinish = new AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation a) {}
			
			@Override
			public void onAnimationRepeat(Animation a) {}
			
			@Override
			public void onAnimationEnd(Animation a)
			{
				next();
			}
		};
	}
	
	@Override
	public boolean add(Animation a)
	{
		a.setAnimationListener(onFinish);
		a.reset();
		super.add(a);
		if (!peek().hasStarted() || size() == 1)
			view.startAnimation(peek());
		else if (peek().hasEnded())
			next();
		return true;
	}
	
	public void next()
	{
		poll();
		if (peek() != null)
			view.startAnimation(peek());
	}
}
