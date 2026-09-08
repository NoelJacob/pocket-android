package com.pocket.ui.view.themed;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


import org.jetbrains.annotations.Nullable;

/**
 * A themed version of {@link RelativeLayout} for Views which have not yet been fully ported over to pocket-ui.
 * <p>
 * New Views should generally use {@link ThemedConstraintLayout} rather than RelativeLayout.
 */
public class ThemedRelativeLayout extends RelativeLayout {


    public ThemedRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ThemedRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public ThemedRelativeLayout(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] state = super.onCreateDrawableState(extraSpace + 1);
        mergeDrawableStates(state, AppThemeUtil.getState(this));
        return state;
    }








    @Override public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }
}