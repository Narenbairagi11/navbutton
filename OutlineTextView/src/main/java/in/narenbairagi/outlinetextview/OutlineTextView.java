package in.narenbairagi.outlinetextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.AutoSizeableTextView;

/**
 * Created by @NarenBairagi11 on 08/sep/24.
 */
@SuppressLint("RestrictedApi")
public class OutlineTextView extends AppCompatTextView implements AutoSizeableTextView {

    private static final float DEFAULT_STROKE_WIDTH = 0F; // Default stroke width
    private boolean isDrawing = false; // Flag to check if it's drawing

    private int strokeColor; // Stroke color
    private float strokeWidth = DEFAULT_STROKE_WIDTH; // Stroke width

    public OutlineTextView(Context context) {
        super(context);
        initResources(context, null);
    }

    public OutlineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResources(context, attrs);
    }

    public OutlineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initResources(context, attrs);
    }

    private void initResources(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = null; // Initialize TypedArray outside the try block
            try {
                a = context.obtainStyledAttributes(attrs, R.styleable.outlineAttrs);
                strokeColor = a.getColor(R.styleable.outlineAttrs_outlineColor, getCurrentTextColor());
                strokeWidth = a.getFloat(R.styleable.outlineAttrs_outlineWidth, DEFAULT_STROKE_WIDTH);
            } finally {
                if (a != null) {
                    a.recycle(); // Recycle TypedArray to avoid memory leaks
                }
            }
        } else {
            strokeColor = getCurrentTextColor();
            strokeWidth = DEFAULT_STROKE_WIDTH;
        }
        setStrokeWidth(strokeWidth);
    }

    public void setStrokeColor(int color) {
        strokeColor = color;
        invalidate();  // Redraw view with new stroke color
    }

    /**
     * Give value in sp.
     */
    public void setStrokeWidth(float width) {
        strokeWidth = Math.max(0, toPx(getContext(), width)); // Ensure positive stroke width
        invalidate();  // Redraw view with new stroke width
    }

    public void setStrokeWidth(int unit, float width) {
        strokeWidth = Math.max(0, TypedValue.applyDimension(unit, width, getContext().getResources().getDisplayMetrics()));
        invalidate();  // Redraw view with new stroke width
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    public void invalidate() {
        if (isDrawing) return;
        super.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (strokeWidth > 0) {
            isDrawing = true;
            Paint p = getPaint();
            p.setStyle(Paint.Style.FILL);

            // Draw the text normally
            super.onDraw(canvas);

            // Draw the stroke around the text
            int currentTextColor = getCurrentTextColor();
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(strokeWidth);
            setTextColor(strokeColor);
            super.onDraw(canvas);
            setTextColor(currentTextColor);
            isDrawing = false;
        } else {
            super.onDraw(canvas);
        }
    }

    private float toPx(Context context, float valueInSp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, context.getResources().getDisplayMetrics());
    }
}
