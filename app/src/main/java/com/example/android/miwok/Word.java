package com.example.android.miwok;

public class Word {
    private String mSpanishTranslation;
    private String mDefaultTranslation;
    private int mImageResourceId;
    private int mSoundRescourceId;

    public Word(String mSpanishTranslation, String mDefaultTranslation){
        this.mSpanishTranslation =mSpanishTranslation;
        this.mDefaultTranslation = mDefaultTranslation;
    }

    public Word(String mSpanishTranslation, String mDefaultTranslation, int mImageResourceId, int mSoundRescourceId){
        this.mSpanishTranslation =mSpanishTranslation;
        this.mDefaultTranslation = mDefaultTranslation;
        this.mImageResourceId = mImageResourceId;
        this.mSoundRescourceId=mSoundRescourceId;}

    public int getmImage() { return mImageResourceId; }

    public String getmSpanishkTranslation() {
        return mSpanishTranslation;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }

    public int getmSoundRescourceId() { return mSoundRescourceId; }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mSpanishTranslation + '\'' +
                ", mAudioResourceId=" + mSoundRescourceId +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
