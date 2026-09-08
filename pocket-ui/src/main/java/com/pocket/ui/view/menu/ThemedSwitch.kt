package com.pocket.ui.view.menu

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SwitchCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.pocket.ui.R
import com.pocket.ui.util.NestedColorStateList
import com.pocket.ui.view.themed.AppThemeUtil

/**
 * A [SwitchCompat] pre-styled for Pocket.
 */
class ThemedSwitch
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = androidx.appcompat.R.attr.switchStyle,
) : SwitchCompat(context, attrs, defStyle) {



    init {
        DrawableCompat.setTintList(thumbDrawable, NestedColorStateList.get(
            context, R.color.pkt_switch_thumb))
        DrawableCompat.setTintList(trackDrawable, NestedColorStateList.get(
            context, R.color.pkt_switch_track))
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val state = super.onCreateDrawableState(extraSpace + 1)
        mergeDrawableStates(state, AppThemeUtil.getState(this))
        return state
    }



    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
    }
}