package com.example.gisro.roomplannerisaac.Classes.ClientModels;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gisro.roomplannerisaac.R;
import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.PopupView;
import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.data.IPopup;
import com.framgia.library.calendardayview.decoration.CdvDecorationDefault;

/**
 * Created by BePulverized on 24-1-2018.
 */

public class DayViewDecoration extends CdvDecorationDefault {

    public DayViewDecoration(Context context) {
        super(context);
    }

    @Override
    public EventView getEventView(final IEvent event, Rect eventBound, int hourHeight,
                                  int seperateHeight) {
        final EventView eventView =
                super.getEventView(event, eventBound, hourHeight, seperateHeight);

        // hide event name
        TextView textEventName = (TextView) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_name);
        textEventName.setTypeface(null, Typeface.BOLD);
        textEventName.setTextSize(11f);
        // hide event header
        TextView textHeader1 = (TextView) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_header_text1);
        TextView textHeader2 = (TextView) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_header_text2);


        return eventView;
    }

    @Override
    public PopupView getPopupView(final IPopup popup, Rect eventBound, int hourHeight,
                                  int seperateH) {
        PopupView popupView = super.getPopupView(popup, eventBound, hourHeight, seperateH);
        // popupView.show();
        CardView cardView = (CardView) popupView.findViewById(com.framgia.library.calendardayview.R.id.cardview);
        TextView textQuote = (TextView) popupView.findViewById(com.framgia.library.calendardayview.R.id.quote);
        TextView textTitle = (TextView) popupView.findViewById(com.framgia.library.calendardayview.R.id.title);
        ImageView imvEnd = (ImageView) popupView.findViewById(com.framgia.library.calendardayview.R.id.imv_end);

        // do something with views

        return popupView;
    }
}
