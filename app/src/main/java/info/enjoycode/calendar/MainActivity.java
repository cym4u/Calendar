package info.enjoycode.calendar;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import info.enjoycode.calendar.view.CalendarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DateTime dateTime = DateTime.now();
    TextView tv;
    CalendarView calendarView;
    private static final String TAG = "MainActivity";
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();

        intDateData();

        initViewPager();
    }

    public <T extends View> T $(int resId) {
        return (T) findViewById(resId);
    }

    private void findViews() {
        tv = $(R.id.tv);
        viewPager = $(R.id.viewPager);
    }

    private void setListeners() {
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.minus).setOnClickListener(this);
        findViewById(R.id.allDateTime).setOnClickListener(this);
        findViewById(R.id.allRow).setOnClickListener(this);
        findViewById(R.id.withTitle).setOnClickListener(this);
        findViewById(R.id.currentMonthOnly).setOnClickListener(this);
        findViewById(R.id.singleRow).setOnClickListener(this);
        findViewById(R.id.noTitle).setOnClickListener(this);
    }

    List<CalendarView> viewContainter = new ArrayList<>();
    int maxCount = 100;

    private void intDateData() {
        DateTime startDateTime = new DateTime(1900,1,1,0,0);
        DateTime endDateTime = new DateTime(2100,1,1,0,0);
        CalendarView calendarView;
        for (int i = maxCount / 2; i > 0; i--) {
            calendarView = new CalendarView(this);
            calendarView.setNumColumns(7);
            calendarView.setDateTime(dateTime.minusMonths(i));
            viewContainter.add(calendarView);
        }

        for (int i = 0; i < maxCount / 2; i++) {
            calendarView = new CalendarView(this);
            calendarView.setNumColumns(7);
            calendarView.setDateTime(dateTime.plusMonths(i));
            viewContainter.add(calendarView);
        }
    }



    private void initViewPager() {
        viewPager.setAdapter(new PagerAdapter() {
            //viewpager中的组件数量
            @Override
            public int getCount() {
                return viewContainter.size();
            }

            //滑动切换的时候销毁当前的组件
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                ((ViewPager) container).removeView(viewContainter.get(position));
            }

            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(viewContainter.get(position));
                return viewContainter.get(position);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                calendarView = viewContainter.get(position);
                dateTime = calendarView.getDateTime();
                tv.setText(String.format("%d年%d月", dateTime.getYear(), dateTime.getMonthOfYear()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(maxCount / 2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % maxCount);
                break;
            case R.id.minus:
                viewPager.setCurrentItem((viewPager.getCurrentItem() - 1) % maxCount);
                break;
            case R.id.allDateTime://显示所有天的时间，包括上月或者下月
                for (int i = 0; i < maxCount; i++) {
                    viewContainter.get(i).setMonthShowMode(CalendarView.MonthShowMode.ALL);
                }
                break;
            case R.id.currentMonthOnly:
                for (int i = 0; i < maxCount; i++) {
                    viewContainter.get(i).setMonthShowMode(CalendarView.MonthShowMode.CURRENT_MONTH_ONLY);
                }
                break;
            case R.id.allRow:
                for (int i = 0; i < maxCount; i++) {
                    viewContainter.get(i).setRowsShowMode(CalendarView.RowsShowMode.ALL);
                }
                break;
            case R.id.singleRow:
                for (int i = 0; i < maxCount; i++) {
                    viewContainter.get(i).setRowsShowMode(CalendarView.RowsShowMode.SINGLE_ROW);
                }
                break;
            case R.id.withTitle:
                for (int i = 0; i < maxCount; i++) {
                    viewContainter.get(i).setTitleShowMode(CalendarView.TitleShowMode.YES);
                }
                break;
            case R.id.noTitle:
                for (int i = 0; i < maxCount; i++) {
                    viewContainter.get(i).setTitleShowMode(CalendarView.TitleShowMode.NO);
                }
                break;
        }

    }

}
