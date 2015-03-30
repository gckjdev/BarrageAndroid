package com.orange.barrage.android.util.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

/**
 * 提醒框
 * @author
 *
 * 需要添加下面权限
 * <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
 */
public class RemindboxAlertDialog {

    /**
     * 第一个按钮
     */
    public static final int LEFTBUTTON = 0;

    /**
     * 第二个按钮
     */
    public static final int CENTERBUTTON = 1;

    /**
     * 第三个按钮
     */
    public static final int RIGHTBUTTON = 2;

    /**
     * 提醒卡组件
     */
    private AlertDialog mAlertDialog = null;

    /**
     * 提醒框
     */
    private Context mContext = null;

    /**
     * 每一个按钮的值
     */
    private String mButton[] = new String[]{"按钮1","按钮2","按钮3"};

    /**
     * 按钮的标题
     */
    private String mTitle = "标题";

    /**
     * 显示的内容
     */
    private String mMessage = "消息";

    /**
     * 图标
     */
    private int mIcon = -1;

    /**
     * 创建一个接口
     */
    private OnClickListener mOnClickListener = null;

    /**
     * 构造函数，调用该构造函数
     * @param context
     */
    public RemindboxAlertDialog(Context context){
        this.mContext = context;
    }

    /**
     * 构造函数
     * @param context
     * @param button  按钮的名称
     * @param title  主题
     * @param Message  消息
     * @param Icon  图片
     * @param onClickListener 监听事件
     */
    public RemindboxAlertDialog(Context context, String button[], String title, String Message, int Icon, OnClickListener onClickListener){
        this(context);
        set( button, title, Message, onClickListener);
    }

    /**
     * 构造函数
     * @param context
     * @param button  按钮的名称
     * @param title  主题
     * @param Message  消息
     * @param onClickListener 监听事件
     */
    public RemindboxAlertDialog(Context context, String button[], String title, String Message, OnClickListener onClickListener){
        this(context, button, title, Message, -1, onClickListener);
    }

    private void set(String button[] , String title,String Message,OnClickListener onClickListener){
        this.mButton = button;
        this.mTitle = title;
        this.mOnClickListener = onClickListener;
        this.mMessage = Message;
    }

    /**
     * 显示对话框
     */
    public void show(){
        Installbutton();
        mAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.show();
    }
    /**
     * 显示对话框
     * @param button  按钮的名称
     * @param title  主题
     * @param Message  消息
     * @param icon  图片
     */
    public void show(String button[], String title, String Message, int icon, OnClickListener onClickListener){
        set( button, title, Message, onClickListener);
        show();
    }

    /**
     *重新设置消息
     * @param message 消息
     */
    public void show(String message){
        this.mMessage = message;
        show();
    }


    /**
     * 初始化alert
     */
    private void Installbutton(){
        if (mAlertDialog == null)
            //设置
            mAlertDialog = new AlertDialog.Builder(mContext).create();
        if (mIcon != -1)
            mAlertDialog.setIcon(mIcon);
        mAlertDialog.setTitle(mTitle);
        mAlertDialog.setMessage(mMessage);
        if (mButton == null || mOnClickListener == null) return;
        //设置按钮
        if (mButton.length == 1) {
            setLeftButton(mButton[0]);
        } else if (mButton.length == 2) {
            setLeftButton(mButton[0]);
            setRightButton(mButton[1]);
        }else if(mButton.length == 3){
            setLeftButton(mButton[0]);
            setRightButton(mButton[2]);
            setCenterButton(mButton[1]);
        }
    }

    /**
     * 设置左边的按钮
     * @param message  左边按钮的信息
     */
    private void setLeftButton(String message){
        mAlertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, message, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mOnClickListener.onRemindItemClick(LEFTBUTTON);
            }
        });
    }

    /**
     * 设置中间的按钮
     * @param message  中间按钮的信息
     */
    private void setCenterButton(String message){
        mAlertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, message, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mOnClickListener.onRemindItemClick(CENTERBUTTON);
            }
        });
    }

    /**
     * 设置右边的按钮
     * @param message  右边按钮的信息
     */
    private void setRightButton(String message){
        mAlertDialog.setButton(DialogInterface.BUTTON_POSITIVE, message, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mOnClickListener.onRemindItemClick(RIGHTBUTTON);
            }
        });
    }

    /**
     * 关闭
     */
    public void close(){
        if(mAlertDialog == null) return;
        mAlertDialog.cancel();
    }


    /**
     * 接口
     * @author
     *onRemindItemClick
     */
    public interface OnClickListener{
        public void onRemindItemClick(int position);
    }

}
