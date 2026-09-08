package com.pocket.ui.view.themed;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;


import org.jetbrains.annotations.Nullable;

public class ThemedConstraintLayout extends ConstraintLayout {

	public ThemedConstraintLayout(Context context) {
		this(context, null);
	}

	public ThemedConstraintLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ThemedConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
