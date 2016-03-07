package info.enjoycode.calendar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.joda.time.DateTime;

/**
 * Created by chenyuanming on 16/3/3.
 */
public class CalendarView extends GridView {
    private static final String TAG = "CalendarView";
    /**
     * 显示模式：单行|全部
     */
    RowsShowMode rowsShowMode = RowsShowMode.ALL;
    /**
     * 显示模式：单行|全部
     */
    MonthShowMode monthShowMode = MonthShowMode.ALL;
    /**
     * 显示模式：单行|全部
     */
    TitleShowMode titleShowMode = TitleShowMode.YES;
    /**
     * 当前时间
     */
    DateTime dateTime = DateTime.now();

    CalendarAdapter adapter;
    String[] weekTitles = {"日", "一", "二", "三", "四", "五", "六"};
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

        adapter = new CalendarAdapter();
        setAdapter(adapter);
    }

    public void setRowsShowMode(RowsShowMode rowsShowMode) {
        this.rowsShowMode = rowsShowMode;
        adapter.caculateItemCount();
    }

    public void setMonthShowMode(MonthShowMode monthShowMode) {
        this.monthShowMode = monthShowMode;
        adapter.caculateItemCount();
    }

    public void setTitleShowMode(TitleShowMode titleShowMode) {
        this.titleShowMode = titleShowMode;
        adapter.caculateItemCount();
    }


    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DateTime getDateTime() {
        return dateTime;
    }


    public class CalendarAdapter extends BaseAdapter {

        int itemCount;
        int firstIndex;
        DateTime firstDay;
        DateTime dateTimes[];

        public CalendarAdapter() {
            DateTime dateTime = DateTime.now();
            setDateTime(dateTime);
        }

        public void setDateTime(DateTime dateTime) {
            firstDay = dateTime.withDate(dateTime.getYear(), dateTime.getMonthOfYear(), 1);

            firstIndex = firstDay.getDayOfWeek() % 7;
            caculateItemCount();
        }

        int titleItemCount, firstDayBefore, dayOfMonth, lastDayAfter, detailItemCount;


        private void caculateItemCount() {
            titleItemCount = titleShowMode == TitleShowMode.YES ? 7 : 0;

            if (rowsShowMode == RowsShowMode.ALL) {
                //1号前面的空位
                firstDayBefore = firstDay.getDayOfWeek() % 7;
                //最后一天后面的空位数
                lastDayAfter = 7 - firstDay.dayOfMonth().withMaximumValue().getDayOfWeek() % 7 - 1;
                //当月显示的天数
                dayOfMonth = firstDay.dayOfMonth().withMaximumValue().getDayOfMonth();
            } else {
                detailItemCount = 7;
                //1号前面的空位
                firstDayBefore = firstDay.getDayOfWeek() % 7;
                //最后一天后面的空位数
                dayOfMonth = 7 - firstDayBefore;
                //当月显示的天数
                lastDayAfter = 0;

            }
            detailItemCount = firstDayBefore + dayOfMonth + lastDayAfter;
            itemCount = titleItemCount + detailItemCount;

            initDateDatas();

            notifyDataSetChanged();
        }


        private void initDateDatas() {
            dateTimes = new DateTime[detailItemCount];
            int index = 0;
            for (int dayBefore = firstDayBefore; dayBefore > 0; dayBefore--, index++) {
                dateTimes[index] = firstDay.minusDays(dayBefore);
            }

            for (int i = 0; i < dayOfMonth; i++, index++) {
                dateTimes[index] = firstDay.plusDays(i);
            }

            for (int i = 0; i < lastDayAfter; i++, index++) {
                dateTimes[index] = firstDay.dayOfMonth().withMaximumValue().plusDays(1).plusDays(i);
            }

        }

        @Override
        public int getCount() {
            return itemCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String text = "";
            if (convertView == null || !(convertView instanceof TextView)) {
                convertView = new TextView(getContext());
            }

            TextView tv = (TextView) convertView;
            tv.setVisibility(View.VISIBLE);
            if (position < titleItemCount) {
                text = weekTitles[position];
                tv.setTextColor(Color.GREEN);
            } else {
                DateTime currentDateTime = dateTimes[position - titleItemCount];
                if (currentDateTime.isBefore(firstDay) || currentDateTime.isAfter(firstDay.dayOfMonth().withMaximumValue())) {
                    if (monthShowMode == MonthShowMode.CURRENT_MONTH_ONLY) {
                        tv.setVisibility(View.GONE);
                    }
                    tv.setTextColor(Color.GRAY);

                } else {
                    tv.setTextColor(Color.BLUE);
                }
                text = String.format("%2d", currentDateTime.getDayOfMonth());
            }
            tv.setText(text);


            return tv;
        }
    }

    public enum RowsShowMode {
        ALL, SINGLE_ROW
    }

    public enum MonthShowMode {
        ALL, CURRENT_MONTH_ONLY
    }

    public enum TitleShowMode {
        YES, NO
    }



}
