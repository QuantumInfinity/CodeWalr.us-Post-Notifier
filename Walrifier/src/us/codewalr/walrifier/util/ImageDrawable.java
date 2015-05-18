package us.codewalr.walrifier.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import us.codewalr.walrifier.Walrifier;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class ImageDrawable
{	
	static GifDecoder gifDecoder = null;
	
	private ArrayList<Frame> frames;
	private long startTime;
	private int duration, width = 0 , height = 0;
	
	public ImageDrawable(InputStream stream)
	{
		frames = new ArrayList<Frame>();
		
		byte[] data = toArray(stream);
		if (fromGIF(data) != GifDecoder.STATUS_OK)
			fromSingle(data);
		
		computeDimensions();
		reset();
	}
	
	public void fromSingle(byte[] data)
	{
		Bitmap b = BitmapFactory.decodeByteArray(data , 0, data.length);
		b.setDensity(Bitmap.DENSITY_NONE);
		frames.add(new Frame(b, 0));
		duration = 0;
	}
	
	public int fromGIF(byte[] data)
	{
		if (gifDecoder == null)
			gifDecoder = new GifDecoder();
		
		int status = gifDecoder.read(data);
		
		if (status == GifDecoder.STATUS_OK)
			for(int i=0; i<gifDecoder.getFrameCount(); i++)
			{
				gifDecoder.setCurrentFrameIndex(i);
				frames.add(new Frame(gifDecoder.getNextFrame(), gifDecoder.getNextDelay()));
			}
		
		duration = getTotalTime();
		
		return status;
	}
	
	public void draw(Canvas canvas)
	{
		int delta = (int) (System.currentTimeMillis() - startTime);
		Frame f = getFrame(delta);
		if (f != null)
			canvas.drawBitmap(f.image, 0, 0, null);
	}
	
	public byte[] toArray(InputStream stream)
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		
		try{
		int nRead;
		byte[] data = new byte[1024];

		while ((nRead = stream.read(data, 0, data.length)) != -1)
			buffer.write(data, 0, nRead);

		buffer.flush();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return buffer.toByteArray();
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

		for (int i=0; i<frames.size(); i++)
		{
			total += frames.get(i).duration;
			if (total > time)
				return frames.get(i);
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
