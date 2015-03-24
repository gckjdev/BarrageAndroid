package com.orange.barrage.android.user.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orange.barrage.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoshu on 2015/3/24.
 */
public class ActionSheetDialog {
    private Context mContext;
    private Dialog mDialog;
    private TextView mTextTitle;
    private TextView mTextContent;
    private LinearLayout mLayoutContent;
    private ScrollView mScrollerViewContet;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private Display mDisplay;

    public ActionSheetDialog(Context mContext) {
        this.mContext = mContext;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_invite_friend_item, null);
        view.setMinimumWidth(mDisplay.getWidth());
        mScrollerViewContet = (ScrollView) view.findViewById(R.id.sLayout_content);
        mLayoutContent = (LinearLayout) view
                .findViewById(R.id.lLayout_content);
        mTextTitle = (TextView) view.findViewById(R.id.txt_title);
        mTextContent = (TextView) view.findViewById(R.id.txt_cancel);
        mTextContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(view);
        //返回dialog所在的窗口
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        mTextTitle.setVisibility(View.VISIBLE);
        mTextTitle.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelabe(boolean cancle) {
        mDialog.setCancelable(cancle);
        return this;
    }

    /**
     * @param strItem  条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String strItem, SheetItemColor color,
                                          OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    //设置条目信息
    private void setSheetItems() {
        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }
        int size = sheetItemList.size();
        if (size > 7) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayoutContent.getLayoutParams();
            params.height = mDisplay.getHeight() / 2;
            mLayoutContent.setLayoutParams(params);
        }

        //循环添加条目
        for (int i = 1; i <= size; i++) {
            final int index = i;
            SheetItem sheetItem = sheetItemList.get(i-1);
            String strItem = sheetItem.name;
            SheetItemColor color = sheetItem.color;
            final OnSheetItemClickListener listener = (OnSheetItemClickListener) sheetItem.itemClickListener;
            TextView textView = new TextView(mContext);
            textView.setText(strItem);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            if (size == 1) {
                if (showTitle) {
                    textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                } else {
                    textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                }
            } else {
                if (showTitle) {
                    if (i >= 1 && i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                } else {
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    }
                }
            }
            if (color == null) {
                textView.setTextColor(Color.parseColor(SheetItemColor.Blue
                        .getName()));
            } else {
                textView.setTextColor(Color.parseColor(color.getName()));
            }
            //高度
            float scale = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (45 * scale + 0.5f);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height));
            // 点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(index);
                    mDialog.dismiss();
                }
            });
            mLayoutContent.addView(textView);
        }
    }

    public void show() {
        setSheetItems();
        mDialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }


    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    //设置颜色
    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");
        private String name;

        private SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
