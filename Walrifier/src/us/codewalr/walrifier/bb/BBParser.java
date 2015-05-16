package us.codewalr.walrifier.bb;

import java.util.HashMap;
import java.util.Map;

import us.codewalr.walrifier.Walrifier;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

public class BBParser
{
	private Map<String, String> bbMap;

	public BBParser()
	{
		bbMap = new HashMap<String , String>();
			
		bbMap.put("(\r\n|\r|\n|\n\r)", "<br/>");
		bbMap.put("\\[tt\\](.+?)\\[/tt\\]", "<tt>$1</tt>");
		bbMap.put("\\[s\\](.+?)\\[/s\\]", "<s>$1</s>");
		bbMap.put("\\[b\\](.+?)\\[/b\\]", "<b>$1</b>");
		bbMap.put("\\[i\\](.+?)\\[/i\\]", "<i>$1</i>");
		bbMap.put("\\[u\\](.+?)\\[/u\\]", "<u'>$1</u>");
		bbMap.put("\\[h1\\](.+?)\\[/h1\\]", "<h1>$1</h1>");
		bbMap.put("\\[h2\\](.+?)\\[/h2\\]", "<h2>$1</h2>");
		bbMap.put("\\[h3\\](.+?)\\[/h3\\]", "<h3>$1</h3>");
		bbMap.put("\\[h4\\](.+?)\\[/h4\\]", "<h4>$1</h4>");
		bbMap.put("\\[h5\\](.+?)\\[/h5\\]", "<h5>$1</h5>");
		bbMap.put("\\[h6\\](.+?)\\[/h6\\]", "<h6>$1</h6>");
		bbMap.put("\\[move\\](.+?)\\[/move\\]", "<marquee>$1</marquee>");
		bbMap.put("\\[quote\\](.+?)\\[/quote\\]", "<blockquote>$1</blockquote>");
		bbMap.put("\\[code\\](.+?)\\[/code\\]", "<blockquote>$1</blockquote>");
		bbMap.put("\\[p\\](.+?)\\[/p\\]", "<p>$1</p>");
		bbMap.put("\\[p=(.+?),(.+?)\\](.+?)\\[/p\\]", "<p style='text-indent:$1px;line-height:$2%;'>$3</p>");
		bbMap.put("\\[center\\](.+?)\\[/center\\]", "<div align='center'>$1</div>");
		bbMap.put("\\[left\\](.+?)\\[/left\\]", "<div align='left'>$1</div>");
		bbMap.put("\\[right\\](.+?)\\[/right\\]", "<div align='right'>$1</div>");
		bbMap.put("\\[align=(.+?)\\](.+?)\\[/align\\]", "<div align='$1'>$2</div>");
		bbMap.put("\\[color=(.+?)\\](.+?)\\[/color\\]", "<font color='$1'>$2</font>");
		bbMap.put("\\[size=(.+?)\\](.+?)\\[/size\\]", "<font size='$1'>$2</font>");
		bbMap.put("\\[img\\](.+?)\\[/img\\]", "<img src='$1'/>");
		bbMap.put("\\[img=(.+?),(.+?)\\](.+?)\\[/img\\]", "<img width='$1' height='$2' src='$3'/>");
		bbMap.put("\\[email\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$1</a>");
		bbMap.put("\\[email=(.+?)\\](.+?)\\[/email\\]", "<a href='mailto:$1'>$2</a>");
		bbMap.put("\\[url\\](.+?)\\[/url\\]", "<a href='$1'>$1</a>");
		bbMap.put("\\[url=(.+?)\\](.+?)\\[/url\\]", "<a href='$1'>$2</a>");
		bbMap.put("\\[youtube\\](.+?)\\[/youtube\\]", "<a href='http://www.youtube.com/v/$1'>http://www.youtube.com/v/$1</a>");
	}
	
	public Spanned parse(View v, String text)
	{
		String html = text;

		for (Map.Entry<String, String> entry: bbMap.entrySet())
			html = html.replaceAll(entry.getKey().toString(), entry.getValue().toString());

		return Html.fromHtml(html, new BBImageGetter(v, Walrifier.resources()), null);
    }
}
