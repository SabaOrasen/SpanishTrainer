package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FamilyActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    public MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
            Log.v("NumbersActivity:","Memory is released");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        final ArrayList<Word> words = getNumberList();

        WordAdapter wordAdapter =
                new WordAdapter(this, R.layout.list_item, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = words.get(i);
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getmSoundRescourceId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(onCompletionListener);

            }


        });

    }

    private ArrayList<Word> getNumberList(){
        ArrayList<String> defaultTranslation = new ArrayList(Arrays.asList("one","two","three","four","five","six","seven","eight","nine","ten"));
        ArrayList<String> spanishWord = new ArrayList(Arrays.asList("uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez"));
        ArrayList<Integer> imageNumbers = new ArrayList(Arrays.asList(R.drawable.number_one,R.drawable.number_two,R.drawable.number_three,R.drawable.number_four,R.drawable.number_five,R.drawable.number_six
                ,R.drawable.number_seven,R.drawable.number_eight,R.drawable.number_nine,R.drawable.number_ten));
        ArrayList<Integer> soundNumbers = new ArrayList(Arrays.asList(R.raw.number_one,R.raw.number_two,R.raw.number_three,R.raw.number_four,R.raw.number_five,R.raw.number_six
                ,R.raw.number_seven,R.raw.number_eight,R.raw.number_nine,R.raw.number_ten));


        ArrayList<Word> words = new ArrayList<Word>();
        if (defaultTranslation.size()==spanishWord.size()){
            for (int i = 0; i < defaultTranslation.size();i++){
                words.add(new Word(spanishWord.get(i), defaultTranslation.get(i), imageNumbers.get(i), soundNumbers.get(i)));
            }}
        return words;
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
        }
    }
}