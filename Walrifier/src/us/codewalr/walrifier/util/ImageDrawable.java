package us.codewalr.walrifier.util;

import java.io.InputStream;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

//TODO ION GIFS

public class ImageDrawable
{	
	private ArrayList<Frame> frames;
	private long startTime;
	private int duration, width = 0 , height = 0;
	
	public ImageDrawable(InputStream stream)
	{
		frames = new ArrayList<Frame>();
				
		fromSingle(stream);
		
		computeDimensions();
		reset();
	}
	
	public void fromSingle(InputStream stream)
	{
		Bitmap b = BitmapFactory.decodeStream(stream);
		b.setDensity(Bitmap.DENSITY_NONE);
		frames.add(new Frame(b, 0));
		duration = 0;
	}
	
	public int fromGIF(InputStream stream)
	{

		
		return 0;
	}
	
	public void draw(Canvas canvas)
	{
		int delta = (int) (System.currentTimeMillis() - startTime);
		Frame f = getFrame(delta);
		if (f != null)
			canvas.drawBitmap(f.image, 0, 0, null);
	}
	
	public void reset()
	{
		startTime = System.currentTimeMillis();
	}
	
	public void computeDimensions()
	{
		width = 0;
		height = 0;

		for (Frame f:frames)
		{
			width = Math.max(f.image.getWidth(), width);
			height = Math.max(f.image.getHeight(), height);
		}
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public Frame getFrame(int time)
	{
		if (frames.size() == 0)
			return null;
		if (duration == 0 || frames.size() == 1)
			return frames.get(0);
		
		time = time % duration;
		int total = 0;
		
		for (Frame f:frames)
		{
			total += f.duration;
			if (total > time)
				return f;
		}
		return null;
	}
	
	public int getTotalTime()
	{
		int total = 0;
		for (int i=0; i<frames.size(); i++)
			total += frames.get(i).duration;
		return total;
	}
	
	public static class Frame
	{
		Bitmap image;
		int duration;
		
		public Frame(Bitmap image, int duration)
		{
			this.image = image;
			this.duration = duration;
		}
	}
}
