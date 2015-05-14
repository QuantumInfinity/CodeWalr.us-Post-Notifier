package us.codewalr.walrifier.util;

import java.util.ArrayList;

public class RBuffer<T>
{
	private T[] buffer;
	
	@SuppressWarnings("unchecked")
	public RBuffer(int size)
	{
		buffer = (T[]) new Object[size];
	}
	
	public T push(T o)
	{
		T r = buffer[buffer.length - 1];
		for (int i=buffer.length-2; i>=0; i--)
			buffer[i+1] = buffer[i];
		buffer[0] = o;
		return r;
	}
	
	public T getLast()
	{
		return buffer[buffer.length - 1];
	}
	
	public void fill(T o)
	{
		for (int i=0; i<buffer.length; i++)
			buffer[i] = o;
	}
	
	public int size()
	{
		return buffer.length;
	}
	
	public T[] getObjects()
	{
		return buffer;
	}
	
	public T get(int index)
	{
		return buffer[index];
	}
	
	public void setSize(int size)
	{
		@SuppressWarnings("unchecked")
		T[] tmp = (T[]) new Object[size];
		for (int i=0;i<tmp.length;i++)
			if (i <= buffer.length)
				tmp[i] = buffer[i];
			else
				tmp[i] = null;
		buffer = tmp;
	}
	
	public ArrayList<T> getList()
	{
		ArrayList<T> list = new ArrayList<T>();
		for (int i=0; i<size(); i++)
			if (get(i) != null)
				list.add(get(i));
		return list;
	}
}