package us.codewalr.walrifier.ui;

import us.codewalr.walrifier.R;
import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class PreferenceCategoryColored extends PreferenceCategory
{
    public PreferenceCategoryColored(Context context)
    {
        super(context);
    }

    public PreferenceCategoryColored(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PreferenceCategoryColored(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onBindView(View view)
    {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        titleView.setTextColor(getContext().getResources().getColor(R.color.settings_text));
    }
}