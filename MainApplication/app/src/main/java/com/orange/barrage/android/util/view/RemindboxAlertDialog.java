package com.orange.barrage.android.util.view;

import android.R;
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
    private AlertDialog alertDialog = null;

    /**
     * 提醒框
     */
    private Context context = null;

    /**
     * 每一个按钮的值
     */
    private String button[] = new String[]{"按钮1"};

    /**
     * 按钮的标题
     */
    private String title = "标题";

    /**
     * 显示的内容
     */
    private String Message = "消息";

    /**
     * 图标
     */
    private int Icon = -1;

    /**
     * 创建一个接口
     */
    private OnClickListener onClickListener = null;

    /**
     * 构造函数，调用该构造函数
     * @param context
     */
    public RemindboxAlertDialog(Context context){
        this.context = context;
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
        this.button = button;
        this.title = title;
        this.onClickListener = onClickListener;
    }

    /**
     * 显示对话框
     */
    public void showButton(){
        Installbutton();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    /**
     * 显示对话框
     * @param button  按钮的名称
     * @param title  主题
     * @param Message  消息
     * @param icon  图片
     */
    public void showButton(String button[] , String title,String Message,int icon , OnClickListener onClickListener){
        set( button, title, Message, onClickListener);
        showButton();
    }

    /**
     *重新设置消息
     * @param message 消息
     */
    public void show(String message){
        this.Message = message;
        showButton();
    }


    /**
     * 初始化alert
     */
    private void Installbutton(){
        if(alertDialog == null)
            //设置
            alertDialog = new AlertDialog.Builder(context).create();
        if(Icon == -1) Icon = R.drawable.alert_dark_frame;
        alertDialog.setIcon(Icon);
        alertDialog.setTitle(title);
        alertDialog.setMessage(Message);
        if(button == null || onClickListener == null) return;
        //设置按钮
        if(button.length == 1){
            setLeftButton(button[0]);
        }else if(button.length == 2){
            setLeftButton(button[0]);
            setRightButton(button[1]);
        }else if(button.length == 3){
            setLeftButton(button[0]);
            setRightButton(button[2]);
            setCenterButton(button[1]);
        }
    }

    /**
     * 设置左边的按钮
     * @param message  左边按钮的信息
     */
    private void setLeftButton(String message){
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, message,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                onClickListener.onClick(LEFTBUTTON);
            }
        });
    }

    /**
     * 设置中间的按钮
     * @param message  中间按钮的信息
     */
    private void setCenterButton(String message){
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, message,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                onClickListener.onClick(CENTERBUTTON);
            }
        });
    }

    /**
     * 设置右边的按钮
     * @param message  右边按钮的信息
     */
    private void setRightButton(String message){
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, message,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                onClickListener.onClick(RIGHTBUTTON);
            }
        });
    }

    /**
     * 关闭
     */
    public void close(){
        if(alertDialog == null) return;
        alertDialog.cancel();
    }


    /**
     * 接口
     * @author
     *
     */
    public interface OnClickListener{
        public void onClick(int position);
    }

}
