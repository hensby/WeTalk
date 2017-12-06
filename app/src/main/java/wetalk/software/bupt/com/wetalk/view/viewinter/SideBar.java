package wetalk.software.bupt.com.wetalk.view.viewinter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import wetalk.software.bupt.com.wetalk.R;

/**
 * Created by Administrator on 2017/11/29.
 */

public class SideBar extends View {
    private String[] chars={"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private TextView mdialog;
    private OnTextViewChange changelistener;
    private Paint paint;

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    public void setTextView(TextView mdialog) {
        this.mdialog = mdialog;
    }

    public interface OnTextViewChange{
        void onTextChange(String s);
    }

    public void setOnTextViewChange(OnTextViewChange change) {
        this.changelistener = change;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = (int) event.getX();//获取触摸到控件上的X坐标值
        float y = (int) event.getY();//获致触摸到控件上的Y坐标值
        switch (event.getAction()) {//判断触摸事件的类型
            case MotionEvent.ACTION_DOWN: {//当类型为按下
                setBackgroundColor(getResources().getColor(R.color.light_notes_color));//设置控件的背景
                if (this.mdialog.getVisibility() == View.INVISIBLE) {//当TextView为隐藏
                    int vi = (int) ((y / (float) getHeight()) * (float) chars.length);//获取具体按下哪个字母
                    this.mdialog.setText(chars[vi]);//设置弹出文本
                    this.mdialog.setVisibility(View.VISIBLE);//显示控件
                    this.changelistener.onTextChange(this.mdialog.getText().toString());//通知ListView
                }
                break;
            }
            case MotionEvent.ACTION_UP://当类型为抬起
                setBackgroundColor(getResources().getColor(R.color.transparent));//设置背景
                if (this.mdialog.getVisibility() == View.VISIBLE) {//判断TextView是否为显示
                    this.mdialog.setText("");//设置文本为空
                    this.mdialog.setVisibility(View.INVISIBLE);//当手指离开控件，隐藏TextView
                }
                break;
            case MotionEvent.ACTION_MOVE: {//当类型为移动
                int vi = (int) ((y / (float) getHeight()) * (float) chars.length);////获取具体按下哪个字母
                if(vi>=0&&vi<=25){
                    this.mdialog.setText(chars[vi]);////设置弹出文本
                    this.changelistener.onTextChange(this.mdialog.getText().toString());//通知ListView
                    break;
                }
            }
            default:
                break;
        }
        return true;
    }



    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint = new Paint();//创建一个画笔
        int x = getWidth();//获取控件的宽度
        int y = getHeight();//获取控件的高度
        int singleHeight = y / chars.length;//获取每个字母的高度
        for (int i = 0; i < chars.length; i++) {
            this.paint.setColor(getResources().getColor(R.color.popbg_color));//设置画笔的颜色为黑色
            this.paint.setTextSize(40.0f);//设置字体的大小为20f
            canvas.drawText(chars[i], 10, singleHeight * (i + 1), paint);//画字符到控件上
        }
    }
}
