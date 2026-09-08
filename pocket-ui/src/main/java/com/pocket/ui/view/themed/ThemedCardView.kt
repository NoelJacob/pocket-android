package com.pocket.ui.view.themed

import android.content.Context
import androidx.cardview.widget.CardView
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet

open class ThemedCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr)
{
    private var cardBackgroundColorStateList: ColorStateList? = null

    init {
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 1)
        mergeDrawableStates(state, AppThemeUtil.getState(this))
        return state
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        if (cardBackgroundColorStateList != null) {
            setCardBackgroundColor(cardBackgroundColorStateList!!.getColorForState(drawableState,
                Color.TRANSPARENT))
        }
    }

    override fun setCardBackgroundColor(color: ColorStateList?) {
        super.setCardBackgroundColor(color)
        cardBackgroundColorStateList = color
    }
}