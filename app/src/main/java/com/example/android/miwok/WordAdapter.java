package com.example.android.miwok;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends ArrayAdapter<Word>  {
    private int backgroundColor;
    private MediaPlayer mediaPlayer;

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects, int backgroundColor) {
        super(context, 0, objects);
        this.backgroundColor = backgroundColor;
    }

    public WordAdapter(@NonNull Context context, int resource, @NonNull List<Word> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Word currentWords = getItem(position);

        LinearLayout backgroundText = (LinearLayout) listItemView.findViewById(R.id.background);
        int color = ContextCompat.getColor(getContext(),backgroundColor);
        backgroundText.setBackgroundColor(color);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.spanish_text_view);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentWords.getmSpanishkTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentWords.getmDefaultTranslation());

        ImageView iconView = (ImageView) listItemView.findViewById(R.id.image);
        if (currentWords.getmImage()!=0) {
            iconView.setImageResource(currentWords.getmImage());
        }else{
          iconView.setVisibility(View.GONE);
        }
        /*
        Log.v("NumbersActivity:", currentWords.toString());
        backgroundText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(view.getContext(), currentWords.getmSoundRescourceId());
                mediaPlayer.start();
                Log.v("NumbersActivity:", currentWords.toString());
            }
        });*/

        return listItemView;
    }
}
