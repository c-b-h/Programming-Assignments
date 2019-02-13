package se.ingenuity.tattoodo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.annotation.AnyRes
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ViewTypeDividerDecoration internal constructor(private val builder: Builder) :
    ViewTypeDrawableDecoration(builder) {
    private val buffer: Rect = Rect()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        drawVertical(c, parent)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val viewHolder = parent.getChildViewHolder(view)
        val viewType = viewHolder.itemViewType
        val divider = drawables.get(viewType)
        if (divider != null) {
            val extra = builder.extras.get(viewType)
            if (extra.placement == Placement.BOTTOM) {
                outRect.set(0, 0, 0, divider.bounds.height())
            } else {
                outRect.set(0, divider.bounds.height(), 0, 0)
            }
        }
    }

    override fun ensureExtras(context: Context) {
        for (i in 0 until builder.extras.size()) {
            val extra = builder.extras.valueAt(i)
            val viewType = builder.extras.keyAt(i)

            val divider = drawables[viewType]

            divider?.apply {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                if (extra.customHeight != null) {
                    bounds.bottom = context.resources.getDimensionPixelSize(extra.customHeight)
                }
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = 0
            right = parent.width
        }

        val childCount = parent.childCount
        val drawables = drawables
        val bounds = this.buffer
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val viewHolder = parent.getChildViewHolder(child)
            val viewType = viewHolder.itemViewType

            val divider = drawables.get(viewType) ?: continue

            val height = divider.bounds.height()
            parent.getDecoratedBoundsWithMargins(child, bounds)

            val top: Int
            val bottom: Int
            val extra = builder.extras.get(viewType)
            if (extra.placement == Placement.BOTTOM) {
                bottom = bounds.bottom + Math.round(child.translationY)
                top = bottom - height
            } else {
                top = bounds.top + Math.round(child.translationY)
                bottom = top + height
            }

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    enum class Placement {
        TOP,
        BOTTOM
    }

    class Builder : ViewTypeDrawableDecoration.Builder<Builder.DividerExtra>() {
        override fun addAttribute(
            @AttrRes attributeRes: Int,
            vararg viewTypes: Int
        ): ViewTypeDrawableDecoration.Builder<*> {
            return addAttributeResource(attributeRes = attributeRes, viewTypes = *viewTypes)
        }

        fun addAttributeResource(
            @AttrRes attributeRes: Int,
            @DimenRes customHeight: Int? = null,
            placement: Placement = Placement.BOTTOM,
            vararg viewTypes: Int
        ): Builder {
            return add(attributeRes, customHeight, placement, *viewTypes)
        }

        private fun add(
            @AnyRes anyRes: Int,
            @DimenRes customHeight: Int? = null,
            placement: Placement = Placement.BOTTOM,
            vararg viewTypes: Int
        ): Builder {
            super.addAttribute(anyRes, *viewTypes)
            addExtra(DividerExtra(placement, customHeight), *viewTypes)

            return this
        }

        override fun build(): ViewTypeDividerDecoration {
            return ViewTypeDividerDecoration(this)
        }

        class DividerExtra(
            val placement: Placement,
            @param:DimenRes @field:DimenRes val customHeight: Int? = null
        )
    }
}
