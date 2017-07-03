package cn.ffb.path;

import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 路径显示
 */
public class PathLayout extends HorizontalScrollView implements OnClickListener {
    private FrameLayout mFrameLayout;
    private PathOnClickListener mPathOnClickListener;
    private List<String> mPaths;

    public interface PathOnClickListener {
        void onPathClick(int position);
    }

    public PathLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PathLayout(Context context) {
        super(context);
        initView(context);
    }

    public PathLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mFrameLayout = new FrameLayout(context);
        this.addView(mFrameLayout);
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.mPaths = new ArrayList<String>();
    }

    static class SavedState extends BaseSavedState {

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel in) {
            super(in);
            in.readList(mPaths, List.class.getClassLoader());
        }

        List<String> mPaths;

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeList(mPaths);
        }

    }

    public void onBack() {
        mPaths.remove(mPaths.size() - 1);
        mFrameLayout.removeViewAt(0);
    }

    public void initPaths() {
        mPaths.removeAll(mPaths);
        mFrameLayout.removeAllViews();
    }

    public void onForward(final String path) {
        mPaths.add(path);
        TextView PathTextView = getPathTextView();
        PathTextView.setText(path);
    }

    public void setPathOnClickListener(PathOnClickListener listener) {
        this.mPathOnClickListener = listener;
    }

    private TextView getPathTextView() {
        TextView textView = new TextView(this.getContext());
        textView.setBackgroundResource(R.drawable.file_location_item_bg);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        textView.setGravity(Gravity.RIGHT);
        textView.setTextColor(this.getContext().getResources().getColor(R.color.text_color));
        int padding = DisplayUtils.dp2Px(this.getContext(), 10);
        int paddingRight = DisplayUtils.dp2Px(this.getContext(), 16);
        textView.setPadding(padding, padding, paddingRight, padding);
        textView.setOnClickListener(this);
        int position = mFrameLayout.getChildCount();
        textView.setTag(position);
        textView.getViewTreeObserver().addOnGlobalLayoutListener(
                new TextViewLayoutListener(textView, mFrameLayout.getChildAt(0)));
        mFrameLayout.addView(textView, 0);
        return textView;
    }

    class TextViewLayoutListener implements OnGlobalLayoutListener {
        private View view;
        private View pView;

        public TextViewLayoutListener(View view, View pView) {
            this.view = view;
            this.pView = pView;
        }

        @Override
        public void onGlobalLayout() {
            int width = view.getWidth();
            LayoutParams lp =
                    new LayoutParams(pView == null ? width : pView.getWidth() + width,
                            LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(lp);
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            new Handler().postDelayed((new Runnable() {
                @Override
                public void run() {
                    smoothScrollTo(mFrameLayout.getWidth() - getChildAt(0).getLeft(), 0);
                }
            }), 5);
        }

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        int viewTotal = mFrameLayout.getChildCount();
        for (int i = 0; i < viewTotal - position - 1; i++) {
            mFrameLayout.removeViewAt(0);
        }
        for (int i = viewTotal - 1; i > position; i--) {
            this.mPaths.remove(i);
        }
        if (mPathOnClickListener != null) {
            mPathOnClickListener.onPathClick(position);
        }
    }

}
