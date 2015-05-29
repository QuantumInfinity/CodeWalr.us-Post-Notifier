package us.codewalr.walrifier.bb;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.XMLReader;

import us.codewalr.walrifier.feed.WalrusAdapter;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Html.TagHandler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.View;

public class BBParser
{
	private Map<String, String> bbMap;
	private WalrusAdapter adapter;
	private Context ctx;
	
	public BBParser(Context ctx, WalrusAdapter adapter)
	{
		this.adapter = adapter;
		this.ctx = ctx;

		bbMap = new HashMap<String , String>();
		bbMap.put("(\r\n|\r|\n|\n\r)", "<br/>");
		
		putTagsSimple("tt", "s", "b", "i", "u", "h1", "h2", "h3", "h4", "h5", "h6", "p", "sub", "sup");
		putTagPlain("move", "marquee");
		putTagPlain("center", "div align='center'", "div");
		putTagPlain("left", "div align='left'", "div");
		putTagPlain("right", "div align='right'", "div");
		
		bbMap.put("\\[url\\](.+?)\\[/url\\]", "<a href='$1'>$1</a>");
		bbMap.put("\\[url=(.+?)\\](.+?)\\[/url\\]", "<a href='$1'>$2</a>");	
		bbMap.put("\\[email=(.+?)\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$2</a>");
		bbMap.put("\\[email\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");
		bbMap.put("\\[img\\](.+?)\\[/img\\]", "<img src='$1'/>");
		bbMap.put("\\[img=(.+?),(.+?)\\](.+?)\\[/img\\]", "<img width='$1' height='$2' src='$3'/>");
		bbMap.put("\\[youtube\\](.+?)\\[/youtube\\]", "<a href='http://www.youtube.com/v/$1'>http://www.youtube.com/v/$1</a>");
		bbMap.put("\\[size=(.+?)\\]", "<font size='$1'>");
		bbMap.put("\\[/size\\]", "</font>");
		bbMap.put("\\[align=(.+?)\\]", "<div align='$1'>");
		bbMap.put("\\[/align\\]", "</div>");
		bbMap.put("\\[color=(.+?)\\]", "<font color='$1'>");
		bbMap.put("\\[/color\\]", "</font>");
		bbMap.put("\\[quote\\]", "<blockquote>");
		bbMap.put("\\[quote author=(.+?)(\\s+(.*?))?\\]", "<blockquote><font color='#696969'>Quote by $1:\n</font>");
		bbMap.put("\\[/quote\\]", "</blockquote>");
		bbMap.put("\\[code\\]", "<blockquote><font color='#696969'>Code:\n</font>");
		bbMap.put("\\[/code\\]", "</blockquote>");
	}
	
	public void putTagPlain(String tag, String htmlOpen, String htmlClose)
	{
		bbMap.put("\\["+tag+"\\]", "<"+htmlOpen+">");
		bbMap.put("\\[/"+tag+"\\]", "</"+htmlClose+">");
	}
	
	public void putTagPlain(String tag, String htmlTag)
	{
		putTagPlain(tag, htmlTag, htmlTag);
	}
	
	public void putTagSimple(String tag)
	{
		putTagPlain(tag, tag);
	}
	
	public void putTagsSimple(String... tags)
	{
		for (int i=0; i<tags.length; i++)
			putTagSimple(tags[i]);
	}
	
	public Spanned parse(String text, int post, View container)
	{
		String html = text;
		for (Map.Entry<String, String> entry: bbMap.entrySet())
			html = html.replaceAll(entry.getKey().toString(), entry.getValue().toString());
		html = html.replace("<br />", "");
		return Html.fromHtml(html, new BBImageGetter(ctx, adapter, post, container), null);
    }
	
	@Deprecated
	public class BBTagHandler implements TagHandler
	{
		@Override
		public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader)
		{
			if(tag.equalsIgnoreCase("bbquote"))
				handleBBQuote(opening, output);
			if(tag.equalsIgnoreCase("wtf"))
				output.append((char) 00);
		}
		
		public void handleBBQuote(boolean opening, Editable output)
		{
			if (opening)
				start((SpannableStringBuilder) output, new BBQuote());
			else
				end((SpannableStringBuilder) output, BBQuote.class, new BackgroundColorSpan(Color.RED));
		}
		
		private <T> Object getLast(Spanned text, Class<T> kind)
		{
			Object[] objs = text.getSpans(0, text.length(), kind);
		    if (objs.length == 0)
		    	return null;
		    else
		    	return objs[objs.length - 1];
		}
		
		private void start(SpannableStringBuilder text, Object mark)
		{ 
			int len = text.length();
			text.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK);
		}

		private <T> void end(SpannableStringBuilder text, Class<T> kind, Object repl)
		{
			int len = text.length();
			Object obj = getLast(text, kind);
			int where = text.getSpanStart(obj);

			text.removeSpan(obj);
			
			if (where != len && where != 0)
		    	text.setSpan(repl, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		private class BBQuote{}
	}
}
