package com.mingjie.jf.view.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mingjie.jf.R;
import com.mingjie.jf.utils.UnitConversionUtil;

/**
 * <p>项目名称：CgkxForHtjf
 * <p>包   名： com.minghao.cgkx.View.progressview
 * <p>版   权： 深圳市铭淏网络科技有限公司 ©2016
 * <p>描   述：
 * <p>创 建 人： wuxianai
 * <p>邮箱地址： wuxianai@ming-hao.cn
 * <p>创建时间： 2016/6/25 13:51
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class ArcProgressView extends View
{
    private Paint paint;
    protected Paint textPaint;

    private RectF rectF = new RectF();
    private Rect textRect = new Rect();

    private float strokeWidth;
    private float suffixTextSize;
    private float bottomTextSize;
    private String bottomText;
    private float textSize;
    private int textColor;
    private int progress = 0;
    private int max;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float arcAngle;
    private String suffixText = "%";
    private float suffixTextPadding;
    private float middleTextPadding;
    private float bottomTextPadding;

    private boolean isShowCustomText = false;

    /**
     * 圆弧中间文字大小
     */
    private float middleTextSize;
    /**
     * 圆弧中间文字
     */
    private String middleText;

    /**
     * 用于置换progress文本
     */
    private String customText;

    /**
     * 圆弧底部文字离圆环距离
     */
    private float arcBottomHeight;

    private final int default_finished_color = Color.WHITE;
    private final int default_unfinished_color = Color.rgb(72, 106, 176);
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final float default_suffix_text_size;
    private final float default_suffix_padding;
    private final float default_bottom_text_size;

    /**
     * 默认圆弧中间文字大小
     */
    private final float default_middle_text_size;

    /**
     * 默认圆弧中间文字大小与进度文字间隔
     */
    private final float default_middle_text_padding;

    /**
     * 默认圆弧底部文字离圆环距离
     */
    private final float default_bottom_text_padding;

    private final float default_stroke_width;
    private final String default_suffix_text;
    private final int default_max = 100;
    private final float default_arc_angle = 360 * 0.8f;
    private float default_text_size;
    private final int min_size;

    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
    private static final String INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size";
    private static final String INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding";
    private static final String INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size";
    private static final String INSTANCE_BOTTOM_TEXT = "bottom_text";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_ARC_ANGLE = "arc_angle";
    private static final String INSTANCE_SUFFIX = "suffix";

    private float progressTextBaseline;
    private float bottomTextBaseline;
    private float middleTextBaseline;

    public ArcProgressView(Context context)
    {
        this(context, null);
    }

    public ArcProgressView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ArcProgressView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        default_text_size = UnitConversionUtil.sp2px(getContext(), 18);
        min_size = (int) UnitConversionUtil.dp2px(getContext(), 100);

        default_suffix_text_size = UnitConversionUtil.sp2px(getContext(), 15);
        default_suffix_padding = UnitConversionUtil.dp2px(getContext(), 0);
        default_suffix_text = "%";
        default_bottom_text_size = UnitConversionUtil.sp2px(getContext(), 8);
        default_stroke_width = UnitConversionUtil.dp2px(getContext(), 4);

        default_middle_text_size = UnitConversionUtil.sp2px(getContext(), 8);

        default_middle_text_padding = UnitConversionUtil.dp2px(getContext(), 0);
        default_bottom_text_padding = UnitConversionUtil.dp2px(getContext(), 10);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgressView,
                defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initByAttributes(TypedArray attributes)
    {
        finishedStrokeColor = attributes.getColor(R.styleable.ArcProgressView_arc_finished_color,
                default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.ArcProgressView_arc_unfinished_color,
                default_unfinished_color);
        textColor = attributes.getColor(R.styleable.ArcProgressView_arc_text_color, default_text_color);
        textSize = attributes.getDimension(R.styleable.ArcProgressView_arc_text_size, default_text_size);
        arcAngle = attributes.getFloat(R.styleable.ArcProgressView_arc_angle, default_arc_angle);
        setMax(attributes.getInt(R.styleable.ArcProgressView_arc_max, default_max));
        setProgress(attributes.getInt(R.styleable.ArcProgressView_arc_progress, 0));
        strokeWidth = attributes.getDimension(R.styleable.ArcProgressView_arc_stroke_width, default_stroke_width);

        suffixTextSize = attributes.getDimension(R.styleable.ArcProgressView_arc_suffix_text_size,
                default_suffix_text_size);
        suffixText = TextUtils.isEmpty(attributes.getString(R.styleable.ArcProgressView_arc_suffix_text)) ?
                default_suffix_text : attributes.getString(R.styleable.ArcProgressView_arc_suffix_text);
        suffixTextPadding = attributes.getDimension(R.styleable.ArcProgressView_arc_suffix_text_padding,
                default_suffix_padding);

        bottomTextPadding = attributes.getDimension(R.styleable.ArcProgressView_arc_bottom_text_padding,
                default_bottom_text_padding);
        bottomTextSize = attributes.getDimension(R.styleable.ArcProgressView_arc_bottom_text_size,
                default_bottom_text_size);
        bottomText = attributes.getString(R.styleable.ArcProgressView_arc_bottom_text);

        middleTextPadding = attributes.getDimension(R.styleable.ArcProgressView_arc_middle_text_padding,
                default_middle_text_padding);
        middleTextSize = attributes.getDimension(R.styleable.ArcProgressView_arc_middle_text_size,
                default_middle_text_size);
        middleText = attributes.getString(R.styleable.ArcProgressView_arc_middle_text);

        customText = attributes.getString(R.styleable.ArcProgressView_arc_custom_text);
        isShowCustomText = attributes.getBoolean(R.styleable.ArcProgressView_arc_is_show_custom_text, false);
    }

    protected void initPainters()
    {
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(default_unfinished_color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void invalidate()
    {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth()
    {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public float getSuffixTextSize()
    {
        return suffixTextSize;
    }

    public void setSuffixTextSize(float suffixTextSize)
    {
        this.suffixTextSize = suffixTextSize;
        this.invalidate();
    }

    public String getBottomText()
    {
        return bottomText;
    }

    public void setBottomText(String bottomText)
    {
        this.bottomText = bottomText;
        this.invalidate();
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;
        if (this.progress > getMax())
        {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(int max)
    {
        if (max > 0)
        {
            this.max = max;
            invalidate();
        }
    }

    public float getBottomTextSize()
    {
        return bottomTextSize;
    }

    public void setBottomTextSize(float bottomTextSize)
    {
        this.bottomTextSize = bottomTextSize;
        this.invalidate();
    }

    public float getTextSize()
    {
        return textSize;
    }

    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getFinishedStrokeColor()
    {
        return finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int finishedStrokeColor)
    {
        this.finishedStrokeColor = finishedStrokeColor;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor()
    {
        return unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor)
    {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
        this.invalidate();
    }

    public float getArcAngle()
    {
        return arcAngle;
    }

    public void setArcAngle(float arcAngle)
    {
        this.arcAngle = arcAngle;
        this.invalidate();
    }

    public String getSuffixText()
    {
        return suffixText;
    }

    public void setSuffixText(String suffixText)
    {
        this.suffixText = suffixText;
        this.invalidate();
    }

    public String getMiddleText()
    {
        return middleText;
    }

    public void setMiddleText(String middleText)
    {
        this.middleText = middleText;
        this.invalidate();
    }

    public String getCustomText()
    {
        return customText;
    }

    public void setCustomText(String customText)
    {
        this.customText = customText;
        this.invalidate();
    }

    public float getSuffixTextPadding()
    {
        return suffixTextPadding;
    }

    public void setSuffixTextPadding(float suffixTextPadding)
    {
        this.suffixTextPadding = suffixTextPadding;
        this.invalidate();
    }

    @Override
    protected int getSuggestedMinimumHeight()
    {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth()
    {
        return min_size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        float width;
        float height;

        textPaint.setTextSize(middleTextSize);
        float bottomTextHeight = getTextBoundsHeight(bottomText, bottomTextSize);

        /**
         * 设置宽度
         */
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        width = widthSpecSize;

        float radius = width / 2f;
        float angle = (360 - arcAngle) / 2f;

        arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));

        rectF.set(strokeWidth / 2f, strokeWidth / 2f, width - strokeWidth / 2f, width - strokeWidth / 2f);

        /***
         * 设置高度
         */
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        float computedHeightSize = widthSpecSize + bottomTextHeight + bottomTextPadding - arcBottomHeight;

        if (heightSpecSize > computedHeightSize)
        {
            height = heightSpecSize;
        }
        else
        {
            height = computedHeightSize;
        }

        setMeasuredDimension((int) width, (int) height);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //绘制未完成及已完成圆环
        drawArc(canvas);
        // 绘制进度文字及进度后缀文字
        String progressText;
        if (isShowCustomText)
        {
            progressText = customText;
        }
        else
        {
            progressText = String.valueOf(getProgress());
        }
        textPaint.setTextSize(textSize);
        float progressTextHeight = textPaint.descent() + textPaint.ascent();
        float progressTextWidth = textPaint.measureText(progressText);

        textPaint.setTextSize(suffixTextSize);
        float suffixTextWidth = textPaint.measureText(suffixText);
        float progressAndSuffixTextSizeSum = progressTextWidth + suffixTextWidth;

        progressTextBaseline = rectF.centerY();
        middleTextBaseline = (rectF.height() - progressTextHeight) / 2.0f - progressTextHeight - middleTextPadding;
        bottomTextBaseline = getHeight() - arcBottomHeight - (textPaint.descent() + textPaint.ascent()) / 2;

        if (isShowCustomText)
        {
            drawCustomAndSuffixText(canvas, progressAndSuffixTextSizeSum);
        }
        else
        {
            drawProgressAndSuffixText(canvas, progressAndSuffixTextSizeSum);
        }

        drawMiddleText(canvas);
        drawBottomText(canvas);
    }

    /**
     * 绘制未完成及已完成圆环
     *
     * @param canvas
     */
    protected void drawArc(Canvas canvas)
    {
        float startAngle = 270 - arcAngle / 2f;
        float finishedSweepAngle = progress / (float) getMax() * arcAngle;
        float finishedStartAngle = startAngle;
        paint.setColor(unfinishedStrokeColor);
        canvas.drawArc(rectF, startAngle, arcAngle, false, paint);
        paint.setColor(finishedStrokeColor);
        canvas.drawArc(rectF, finishedStartAngle, finishedSweepAngle, false, paint);

    }

    /**
     * 绘制自定义文字及后缀文字
     *
     * @param canvas
     */
    protected void drawCustomAndSuffixText(Canvas canvas, float customAndSuffixTextSizeSumWidth)
    {

        if (!TextUtils.isEmpty(customText))
        {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            int customTextBoundsWidth = getTextBoundsWidth(customText, textSize);
            canvas.drawText(customText, (getWidth() - customAndSuffixTextSizeSumWidth) / 2.0f, progressTextBaseline,
                    textPaint);

            textPaint.setTextSize(suffixTextSize);
            canvas.drawText(suffixText, (getWidth() - customAndSuffixTextSizeSumWidth) / 2.0f + customTextBoundsWidth
                    + suffixTextPadding, progressTextBaseline, textPaint);
        }
    }

    /**
     * 绘制进度文字及后缀文字
     *
     * @param canvas
     */
    protected void drawProgressAndSuffixText(Canvas canvas, float progressAndSuffixTextSizeSumWidth)
    {

        String progressText = String.valueOf(getProgress());
        if (!TextUtils.isEmpty(progressText))
        {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            int progressTextBoundsWidth = getTextBoundsWidth(progressText, textSize);
            canvas.drawText(progressText, (getWidth() - progressAndSuffixTextSizeSumWidth) / 2.0f,
                    progressTextBaseline, textPaint);

            textPaint.setTextSize(suffixTextSize);
            canvas.drawText(suffixText, (getWidth() - progressAndSuffixTextSizeSumWidth) / 2.0f +
                    progressTextBoundsWidth + suffixTextPadding, progressTextBaseline, textPaint);
        }
    }

    /**
     * 绘制中间文字
     *
     * @param canvas
     */
    protected void drawMiddleText(Canvas canvas)
    {

        if (!TextUtils.isEmpty(getMiddleText()))
        {

            int middleTextBoundsWidth = getTextBoundsWidth(getMiddleText(), middleTextSize);
            textPaint.setTextSize(middleTextSize);
            canvas.drawText(getMiddleText(), (getWidth() - middleTextBoundsWidth) / 2.0f, middleTextBaseline,
                    textPaint);
        }
    }

    /**
     * 绘制底部文字
     *
     * @param canvas
     */
    protected void drawBottomText(Canvas canvas)
    {
        //绘制底部文字
        if (!TextUtils.isEmpty(getBottomText()))
        {

            int bottomTextBoundsWidth = getTextBoundsWidth(getBottomText(), bottomTextSize);
            textPaint.setTextSize(bottomTextSize);
            canvas.drawText(getBottomText(), (getWidth() - bottomTextBoundsWidth) / 2.0f, bottomTextBaseline,
                    textPaint);
        }
    }

    /**
     * 获取文本Bounds所占高度
     *
     * @param text
     * @param textSize
     * @return
     */
    protected int getTextBoundsHeight(String text, float textSize)
    {
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        return textRect.height();

    }

    /**
     * 获取文本Bounds所占宽度
     *
     * @param text
     * @param textSize
     * @return
     */
    protected int getTextBoundsWidth(String text, float textSize)
    {
        textPaint.setTextSize(textSize);
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        return textRect.width();
    }

    @Override
    protected Parcelable onSaveInstanceState()
    {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, getSuffixTextSize());
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, getSuffixTextPadding());
        bundle.putFloat(INSTANCE_BOTTOM_TEXT_SIZE, getBottomTextSize());
        bundle.putString(INSTANCE_BOTTOM_TEXT, getBottomText());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle());
        bundle.putString(INSTANCE_SUFFIX, getSuffixText());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (state instanceof Bundle)
        {
            final Bundle bundle = (Bundle) state;
            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
            suffixTextSize = bundle.getFloat(INSTANCE_SUFFIX_TEXT_SIZE);
            suffixTextPadding = bundle.getFloat(INSTANCE_SUFFIX_TEXT_PADDING);
            bottomTextSize = bundle.getFloat(INSTANCE_BOTTOM_TEXT_SIZE);
            bottomText = bundle.getString(INSTANCE_BOTTOM_TEXT);
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
            suffixText = bundle.getString(INSTANCE_SUFFIX);
            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
