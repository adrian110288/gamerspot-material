package com.adrianlesniak.gamerspot.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.utilities.Utils;

/**
 * Created by Adrian on 04-Jan-15.
 */
public class FeedListSeparator extends RecyclerView.ItemDecoration {

    private Context mContext;

    public FeedListSeparator(Context context) {
        mContext = context;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        Paint paint = new Paint();
        paint.setColor(mContext.getResources().getColor(R.color.feed_item_separator));
        c.drawPaint(paint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = Utils.convertDpToPx(mContext, 0.5f);
    }
}
