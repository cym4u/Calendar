package info.enjoycode.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.joda.time.DateTime;

import info.enjoycode.calendar.view.CalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DateTime dateTime;
    TextView tv;
    CalendarView calendarView;
    private static final String TAG = "MainActivity";
    CalendarView.CalendarShowMode showMode = CalendarView.CalendarShowMode.ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.minus).setOnClickListener(this);
        findViewById(R.id.changeMode).setOnClickListener(this);
        dateTime = DateTime.now();
        calendarView.setDateTime(dateTime);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                dateTime = dateTime.plusMonths(1);
                calendarView.setDateTime(dateTime);
                break;
            case R.id.minus:
                dateTime = dateTime.plusMonths(-1);
                calendarView.setDateTime(dateTime);
                break;
            case R.id.changeMode:
                showMode = showMode == CalendarView.CalendarShowMode.ALL ? CalendarView.CalendarShowMode.SINGLE_ROW : CalendarView.CalendarShowMode.ALL;
                calendarView.setShowMode(showMode);
                break;
        }
        tv.setText(String.format("%d年%d月", dateTime.getYear(), dateTime.getMonthOfYear()));
    }
}
