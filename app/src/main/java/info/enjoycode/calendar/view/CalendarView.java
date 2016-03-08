package info.enjoycode.calendar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import info.enjoycode.calendar.view.BaseCalendarViewAdapter.RowsShowMode;
import info.enjoycode.calendar.view.BaseCalendarViewAdapter.TitleShowMode;

/**
 * Created by chenyuanming on 16/3/3.
 */
public class CalendarView extends GridView {
    private static final String TAG = "CalendarView";

    /**
     * 当前时间
     */
    DateTime dateTime = DateTime.now();

    BaseCalendarViewAdapter adapter;

    public CalendarView(Context context) {
        super(context);
        initView(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        adapter = new BaseCalendarViewAdapter(context) {
            @Override
            protected View getWeekTitleView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(getContext());
                tv.setText(weekTitles[position]);
                tv.setTextColor(Color.GREEN);
                tv.setTextSize(20);
                return tv;
            }

            @Override
            protected View getDetailViewWithTime(int position, View convertView, ViewGroup parent, final DateTime currentDateTime, boolean isDateInCurrentMonth) {

                TextView tv = new TextView(getContext());
                tv.setText(String.format("%2d", currentDateTime.getDayOfMonth()));
                if (isDateInCurrentMonth) {
                    tv.setTextColor(Color.BLUE);
                }else{
                    tv.setTextColor(Color.GRAY);
                }
                tv.setTextSize(20);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), currentDateTime.toString("yyyy-MM-dd"), Toast.LENGTH_SHORT).show();
                    }
                });
                return tv;
            }
        };
        setAdapter(adapter);
    }

    public void setAdapter(BaseCalendarViewAdapter adapter) {
        this.adapter = adapter;
        super.setAdapter(adapter);
    }

    public void setRowsShowMode(RowsShowMode rowsShowMode) {
        if (adapter != null) {
            adapter.setRowsShowMode(rowsShowMode);
        }
    }


    public void setTitleShowMode(TitleShowMode titleShowMode) {
        if (adapter != null) {
            adapter.setTitleShowMode(titleShowMode);
        }
    }


    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
        if (adapter != null) {
            adapter.setDateTime(dateTime);
        }
    }

    public DateTime getDateTime() {
        return dateTime;
    }


}
