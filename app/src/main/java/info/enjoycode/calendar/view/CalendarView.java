package info.enjoycode.calendar.view;

import android.annotation.TargetApi;
import android.content.Context;
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
public class CalendarView  extends GridView{
    private static final String TAG = "CalendarView";
    CalendarShowMode showMode = CalendarShowMode.ALL;
    public CalendarView(Context context) {
        super(context);
        initView(context,null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs);
    }
    CalendarAdapter adapter;
    private void initView(Context context, AttributeSet attrs) {

        adapter = new CalendarAdapter();
        setAdapter(adapter);
    }

    public void setShowMode(CalendarShowMode showMode) {
        adapter.setShowMode(showMode);
    }

    String []weekTitles = {"日","一","二","三","四","五","六"};

    public void setDateTime(DateTime dateTime){
        adapter.setDateTime(dateTime);
    }

    public class CalendarAdapter extends BaseAdapter{

        int itemCount;
        int firstIndex ;
        DateTime firstDay;
        CalendarShowMode showMode = CalendarShowMode.ALL;
        public CalendarAdapter(){
            DateTime dateTime = DateTime.now();
           setDateTime(dateTime);
        }

        public void setDateTime(DateTime dateTime) {
            firstDay = dateTime.withDate(dateTime.getYear(),dateTime.getMonthOfYear(),1);

            firstIndex = firstDay.getDayOfWeek()%7;
            setItemCount();
        }

        public void setShowMode(CalendarShowMode showMode){
            this.showMode = showMode;
            setItemCount();
        }

        private void setItemCount() {
            if(showMode== CalendarShowMode.ALL){
                itemCount  = 7+firstDay.getDayOfWeek()%7+firstDay.dayOfMonth().getMaximumValue();
            }else{
                itemCount = 14;
            }
            notifyDataSetChanged();
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
            if(position<7){
                text = weekTitles[position];
            }else{
                int day = (position-7)-firstIndex+1;
                text = day<=0?"  ":String.format("%2d ",day);
            }
            TextView tv = new TextView(getContext());
            tv.setText(text);
            return tv;
        }
    }

    public enum CalendarShowMode{
        ALL,SINGLE_ROW;
    }
}
