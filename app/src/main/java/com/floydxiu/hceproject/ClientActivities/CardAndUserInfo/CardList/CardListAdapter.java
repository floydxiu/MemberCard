package com.floydxiu.hceproject.ClientActivities.CardAndUserInfo.CardList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floydxiu.hceproject.DataType.Card;
import com.floydxiu.hceproject.R;

import java.util.List;

/**
 * Created by Floyd on 2016/9/27.
 */

public class CardListAdapter extends ArrayAdapter<Card> {

    private LayoutInflater layoutInflater;

    public CardListAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card item = (Card) getItem(position);

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.listview_singlecard, null);
        }

        final ImageView imgMore = (ImageView) convertView.findViewById(R.id.imgMore);
        final LinearLayout layoutDetails = (LinearLayout) convertView.findViewById(R.id.layoutDetails);
        final LinearLayout layoutbtnDetails = (LinearLayout) convertView.findViewById(R.id.layoutbtnDetails);
        final TextView txvHeaderComName = (TextView) convertView.findViewById(R.id.txvHeaderComName);
        final TextView txvHeaderCardNum = (TextView) convertView.findViewById(R.id.txvHeaderCardNum);

        txvHeaderCardNum.setText(""+item.getCardNum());
        txvHeaderComName.setText(item.getComName());

        layoutbtnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutDetails.getVisibility() == LinearLayout.INVISIBLE){
                    layoutDetails.setVisibility(LinearLayout.VISIBLE);
                    layoutDetails.getLayoutParams().height = 300;
                    layoutDetails.requestLayout();
                    imgMore.setImageResource(R.drawable.ic_expand_less_black_24dp);
                }
                else{
                    layoutDetails.setVisibility(LinearLayout.INVISIBLE);
                    layoutDetails.getLayoutParams().height = 0;
                    layoutDetails.requestLayout();
                    imgMore.setImageResource(R.drawable.ic_expand_more_black_24dp);
                }
            }
        });

        return convertView;
    }
}