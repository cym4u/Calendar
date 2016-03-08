package info.enjoycode.calendar.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.joda.time.DateTime;

/**
 * Created by chenyuanming on 16/3/8.
 */
public abstract  class BaseCalendarViewAdapter extends BaseAdapter {

    int itemCount;
    int firstIndex;
    DateTime firstDay;
    DateTime dateTimes[];
    Context mContext;

    public String[] weekTitles = {"日", "一", "二", "三", "四", "五", "六"};
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
    public BaseCalendarViewAdapter(Context context,DateTime dateTime) {
        this.mContext = context;
        setDateTime(dateTime);
    }

    public Context getContext() {
        return mContext;
    }

    public void setRowsShowMode(RowsShowMode rowsShowMode) {
        this.rowsShowMode = rowsShowMode;
        caculateItemCount();
    }

    public void setTitleShowMode(TitleShowMode titleShowMode) {
        this.titleShowMode = titleShowMode;
        caculateItemCount();
    }

    public void setMonthShowMode(MonthShowMode monthShowMode) {
        this.monthShowMode = monthShowMode;
        caculateItemCount();
    }

    public BaseCalendarViewAdapter(Context context) {
        this.mContext = context;
        DateTime dateTime = DateTime.now();
        setDateTime(dateTime);
    }
    public void setDateTime(DateTime dateTime) {
        firstDay = dateTime.withDate(dateTime.getYear(), dateTime.getMonthOfYear(), 1);

        firstIndex = firstDay.getDayOfWeek() % 7;
        caculateItemCount();
    }

    int titleItemCount, firstDayBefore, dayOfMonth, lastDayAfter, detailItemCount;


    public void caculateItemCount() {
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
        if (position < titleItemCount) {
            return getWeekTitleView(position,convertView,parent);
        } else {
            DateTime currentDateTime = dateTimes[position - titleItemCount];
            boolean isDateInCurrentMonth = !(currentDateTime.isBefore(firstDay) || currentDateTime.isAfter(firstDay.dayOfMonth().withMaximumValue()));

           return getDetailViewWithTime(position,convertView,parent,currentDateTime,isDateInCurrentMonth);
        }
    }

    /**
     *
     * @param position 0-6,表示周日，周一。。周五，周六
     * @param convertView
     * @param parent
     * @return
     */
    protected abstract View getWeekTitleView(int position, View convertView, ViewGroup parent);

    protected abstract View getDetailViewWithTime(int position, View convertView, ViewGroup parent, DateTime currentDateTime, boolean isDateInCurrentMonth);


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
