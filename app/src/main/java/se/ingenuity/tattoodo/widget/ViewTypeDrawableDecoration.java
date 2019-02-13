package se.ingenuity.tattoodo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public abstract class ViewTypeDrawableDecoration extends RecyclerView.ItemDecoration {
    @NonNull
    private final SparseArray<Drawable> drawables;

    @NonNull
    private final Builder builder;

    ViewTypeDrawableDecoration(@NonNull Builder b) {
        drawables = new SparseArray<>();
        builder = b;
    }

    @CallSuper
    @Override
    public void getItemOffsets(@NotNull Rect outRect,
                               @NotNull View view,
                               @NotNull RecyclerView parent,
                               @NotNull RecyclerView.State state) {
        ensureDrawables(parent.getContext());
    }

    @CallSuper
    @Override
    public void onDraw(@NotNull Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        ensureDrawables(parent.getContext());
    }

    @NonNull
    final SparseArray<Drawable> getDrawables() {
        return drawables;
    }

    protected void ensureExtras(@NonNull Context context) {
    }

    private void ensureDrawables(@NonNull Context context) {
        if (drawables.size() == 0) {
            final int[] attributes = new int[1];
            for (int i = 0; i < builder.drawableAttributes.size(); i++) {
                final int viewType = builder.drawableAttributes.keyAt(i);
                attributes[0] = builder.drawableAttributes.valueAt(i);

                final TypedArray a = context.obtainStyledAttributes(attributes);
                drawables.put(viewType, a.getDrawable(0));
                a.recycle();
            }

            ensureExtras(context);
        }
    }

    public abstract static class Builder<T> {
        @NonNull
        final SparseIntArray drawableAttributes;

        @NonNull
        final SparseArray<T> extras;

        Builder() {
            drawableAttributes = new SparseIntArray();
            extras = new SparseArray<>();
        }

        @NonNull
        public Builder addAttribute(@AttrRes int attribute, @NonNull int... viewTypes) {
            for (final int viewType : viewTypes) {
                removeViewType(viewType);
                drawableAttributes.put(viewType, attribute);
            }
            return this;
        }

        @NonNull
        Builder addExtra(@NonNull T extra, @NonNull int... viewTypes) {
            for (final int viewType : viewTypes) {
                extras.put(viewType, extra);
            }
            return this;
        }

        private void removeViewType(int viewType) {
            drawableAttributes.delete(viewType);
            extras.delete(viewType);
        }

        @NonNull
        public abstract ViewTypeDrawableDecoration build();
    }
}
