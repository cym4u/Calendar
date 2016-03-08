package info.enjoycode.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import info.enjoycode.calendar.view.BaseCalendarViewAdapter;
import info.enjoycode.calendar.view.CalendarView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    BaseCalendarViewAdapter.RowsShowMode rowsShowMode = BaseCalendarViewAdapter.RowsShowMode.ALL;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = $(R.id.calendarView);
        $(R.id.changeMode).setOnClickListener(this);
        $(R.id.viewPagerDemo).setOnClickListener(this);
        calendarView.setMonthShowMode(BaseCalendarViewAdapter.MonthShowMode.CURRENT_MONTH_ONLY);
        calendarView.setRowsShowMode(rowsShowMode);
        calendarView.setTitleShowMode(BaseCalendarViewAdapter.TitleShowMode.YES);

        calendarView.setAdapter(new BaseCalendarViewAdapter(this) {
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
                        Toast.makeText(Main2Activity.this, currentDateTime.toString("yyyy-MM-dd"), Toast.LENGTH_SHORT).show();
                    }
                });
                return tv;
            }
        });
    }


    public <T extends View> T $(int resId) {
        return (T) findViewById(resId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeMode:
                rowsShowMode = rowsShowMode == BaseCalendarViewAdapter.RowsShowMode.ALL ? BaseCalendarViewAdapter.RowsShowMode.SINGLE_ROW : BaseCalendarViewAdapter.RowsShowMode.ALL;
                calendarView.setRowsShowMode(rowsShowMode);
                break;
            case R.id.viewPagerDemo:
               startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
}
