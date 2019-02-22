package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class NumbersActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioFocus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        audioFocus = (AudioManager) NumbersActivity.this.getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = getNumberList();

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter wordAdapter =
                new WordAdapter(this, R.layout.list_item, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(wordAdapter);

        // Interface AdapterView hat die OnItemClickListener Methode die von ListView geerbt wird
        // Vergibt allen ListView-Items eine onClick Methode
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result = audioFocus.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                Word word = words.get(i);
                releaseMediaPlayer();
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmSoundRescourceId());
                    onAudioFocusChangeListener.onAudioFocusChange(result);
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }


        });


    }


    private ArrayList<Word> getNumberList() {
        ArrayList<String> defaultTranslation = new ArrayList(Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"));
        ArrayList<String> spanishWord = new ArrayList(Arrays.asList("uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez"));
        ArrayList<Integer> imageNumbers = new ArrayList(Arrays.asList(R.drawable.number_one, R.drawable.number_two, R.drawable.number_three, R.drawable.number_four, R.drawable.number_five, R.drawable.number_six
                , R.drawable.number_seven, R.drawable.number_eight, R.drawable.number_nine, R.drawable.number_ten));
        ArrayList<Integer> soundNumbers = new ArrayList(Arrays.asList(R.raw.number_one, R.raw.number_two, R.raw.number_three, R.raw.number_four, R.raw.number_five, R.raw.number_six
                , R.raw.number_seven, R.raw.number_eight, R.raw.number_nine, R.raw.number_ten));


        ArrayList<Word> words = new ArrayList<Word>();
        if (defaultTranslation.size() == spanishWord.size()) {
            for (int i = 0; i < defaultTranslation.size(); i++) {
                words.add(new Word(spanishWord.get(i), defaultTranslation.get(i), imageNumbers.get(i), soundNumbers.get(i)));
            }
        }
        return words;
    }

    public AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            if (i == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
                Log.v("NumbersActivity:", "Memory is released");
            } else if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            }
        }
    };

    public MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
            Log.v("NumbersActivity:", "Memory is released");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            audioFocus.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}


