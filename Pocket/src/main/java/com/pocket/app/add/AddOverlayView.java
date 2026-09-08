package com.pocket.app.add;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ideashower.readitlater.R;
import com.ideashower.readitlater.databinding.ViewAddOverlayBinding;


import com.pocket.ui.view.button.ButtonBoxDrawable;
import com.pocket.ui.view.button.IconButton;
import com.pocket.ui.view.progress.FullscreenProgressView;

import org.jetbrains.annotations.Nullable;

public class AddOverlayView extends FrameLayout {

    private final Binder binder = new Binder();

    private final View saved;
    private final IconButton addTags;
    private final FullscreenProgressView loadingDialog;


    public AddOverlayView(Context context) {
        super(context);
        ViewAddOverlayBinding views =
                ViewAddOverlayBinding.inflate(LayoutInflater.from(context), this);

        final View overlay = views.overlayRoot;
        overlay.setBackground(
                new ButtonBoxDrawable(context, com.pocket.ui.R.color.pkt_bg, R.color.add_overlay_free_stroke));
        overlay.setClickable(true);

        saved = views.saved;
        addTags = views.tag;
        loadingDialog = views.loading;

        views.saveIcon.setChecked(true);
        final TextView label = views.saveLabel;
        label.setText(R.string.add_overlay_free_title);
        label.setTextColor(ContextCompat.getColorStateList(getContext(), com.pocket.ui.R.color.pkt_themed_grey_1));

        addTags.setSideMarginStart();
        addTags.setSideMarginEnd();

        bind().clear();
        
    }

    
    
    
    
    
    
    public Binder bind() {
        return binder;
    }

    public class Binder {

        public Binder clear() {
            onSavedClick(null);
            onTagClick(null);
            hideLoading();
            return this;
        }


        public Binder onSavedClick(OnClickListener listener) {
            saved.setOnClickListener(view -> {
                if (listener != null) listener.onClick(view);
            });
            return this;
        }

        public Binder onTagClick(OnClickListener listener) {
            addTags.setOnClickListener(view -> {
                if (listener != null) listener.onClick(view);
            });
            return this;
        }

        public Binder showLoading(CharSequence message) {
            loadingDialog.bind().clear()
                    .progressCircle(true)
                    .message(message)
                    .visible(true);
            return this;
        }

        public Binder hideLoading() {
            loadingDialog.bind()
                    .visible(false);
            return this;
        }
    }
}
