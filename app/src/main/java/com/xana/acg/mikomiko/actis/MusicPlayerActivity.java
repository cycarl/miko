package com.xana.acg.mikomiko.actis;
import android.animation.ValueAnimator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.xana.acg.Factory;
import com.xana.acg.com.app.PresenterActivity;
import com.xana.acg.com.app.ToolbarActivity;
import com.xana.acg.com.widget.PortraitView;
import com.xana.acg.fac.model.music.SearchResult;
import com.xana.acg.fac.model.music.SongUri;
import com.xana.acg.fac.presenter.play.MusicPlayerContract;
import com.xana.acg.fac.presenter.play.MusicPlayerPresenter;
import com.xana.acg.mikomiko.R;
import net.qiujuer.genius.ui.widget.SeekBar;
import java.io.IOException;
import java.util.Date;
import butterknife.BindView;
import butterknife.OnClick;

import static com.xana.acg.com.Common.URI.MUSIC;

public class MusicPlayerActivity extends PresenterActivity<MusicPlayerContract.Presenter>
    implements MusicPlayerContract.View{

    private MediaPlayer mediaPlayer;
    private Date date;
    final Object syncObj = new Object();
    private Animation anim;

//    protected MediaPlayer getMedia(){
//        if(mediaPlayer==null){
//            synchronized (this){
//                if(mediaPlayer==null)
//                    return mediaPlayer = new MediaPlayer();
//            }
//        }
//        return mediaPlayer;
//    }

    @BindView(R.id.sb_bar)
    SeekBar mBar;

    @BindView(R.id.iv_cur_time)
    TextView mCurTime;

    @BindView(R.id.iv_duration)
    TextView mDuration;

    @BindView(R.id.pv_disk)
    PortraitView mDisk;

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_tip)
    TextView mTip;

    @BindView(R.id.tv_creater)
    TextView mCreator;

    private int curTime = 0;
    private int duration = 1;

    private boolean isPlaying = true;
    private boolean isFavor = false;

    private SearchResult.Song  song;
    private String uri;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music_player;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        String json = bundle.getString("song");
        if(json==null||json.length()==0)
            return false;
        song = Factory.getGson().fromJson(json, SearchResult.Song.class);
        return song !=null;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mediaPlayer = new MediaPlayer();
        date = new Date();
    }

    @Override
    protected void initData() {
        super.initData();
        Log.e("songSrc",song.toString());

        uri = String.format(MUSIC, song.getId());
        mDisk.setSrc(song.getAl().getPicUrl());

        mTitle.setText(song.getName());
        mTip.setText(song.getAlia().size()>0? song.getAlia().get(0): "");
        StringBuilder creaters = new StringBuilder();
        for (SearchResult.Song.Ar ar : song.getAr()) {
            creaters.append(ar.getName()).append(" ");
        }
        mCreator.setText(creaters.toString());

        if (!setPlayer()) return;
        mBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                double rate = seekBar.getProgress() / 100.0;
                int duration = mediaPlayer.getDuration();
                curTime = (int) (duration * rate);
                mediaPlayer.seekTo(curTime);
            }
        });
    }


    private Thread mTaskTime = new Thread() {
        @Override
        public void run() {

            synchronized (syncObj) {
                while (curTime < duration) {
                    curTime += 1000;
                    if (curTime > duration)
                        curTime = duration;
                    double rate = (double) curTime / duration;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mCurTime.setText(DateFormat.format("mm:ss", new Date(curTime)));
                            mBar.setProgress((int) (100 * rate));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
    private boolean setPlayer() {
        try {

            mediaPlayer.setDataSource(uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    duration = mediaPlayer.getDuration();
                    prepare();
                    mediaPlayer.start();
                    anim = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setRepeatCount(ValueAnimator.INFINITE);
                    anim.setInterpolator(new LinearInterpolator());
                    anim.setDuration(10000);

                    mDisk.startAnimation(anim);
                }
            });
            return true;

        } catch (IOException e) {
            showToast("异常");
            e.printStackTrace();
        }
        return false;
    }


    private void prepare() {
        date.setTime(duration);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDuration.setText(DateFormat.format("mm:ss", date));
            }
        });
        mTaskTime.start();
    }

    @OnClick({R.id.iv_favor, R.id.iv_dload, R.id.iv_extra, R.id.iv_comment,
            R.id.iv_detail, R.id.iv_mode, R.id.iv_front, R.id.iv_play,
            R.id.iv_next, R.id.iv_list})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_favor:
                toggle(v);
                break;
            case R.id.iv_dload:
                break;

            case R.id.iv_extra:
                break;
            case R.id.iv_comment:
                break;
            case R.id.iv_detail:
                break;
            case R.id.iv_mode:
                break;
            case R.id.iv_front:
                break;
            case R.id.iv_play:
                toggle(v);
                break;
            case R.id.iv_next:
                break;

            case R.id.iv_list:
                break;
        }
    }

    private void toggle(View view) {
        ImageView v = (ImageView) view;
        switch (v.getId()) {
            case R.id.iv_play:
                if (isPlaying) {
                    mediaPlayer.pause();
                    v.setImageResource(R.drawable.ic_play_);
                } else {
                    mediaPlayer.start();
                    v.setImageResource(R.drawable.ic_pause_);
                }
                isPlaying = !isPlaying;
                break;

            case R.id.iv_favor:
                v.setImageResource(isFavor ? R.drawable.ic_favor_s : R.drawable.ic_favor);
                isFavor = !isFavor;
                break;
        }
    }

    @Override
    protected MusicPlayerContract.Presenter initPresenter() {
        return new MusicPlayerPresenter(this);
    }

    @Override
    public void onLoad(SongUri songUri) {
        // TODO
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}