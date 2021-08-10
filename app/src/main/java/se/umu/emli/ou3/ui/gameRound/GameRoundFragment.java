package se.umu.emli.ou3.ui.gameRound;

import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import se.umu.emli.ou3.R;
import se.umu.emli.ou3.Song;

/**
 * View class.
 *
 * Handles UI and operating system interactions for the user to be playing a round of the game.
 *
 * That includes starting the round with a countdown from 3, giving the user some time to
 * settle in. After initial countdown a round is started, 3 minute timer is started counting down visually
 * to the user showing how much time of the round is left.
 *
 * Then a random song is shown in the UI. A result is set depending on how the user
 * moved the device. Tilt up for point on the song and tilt down for skipping/passing the song.
 * UI is updated depending on point or pass, including textview showing point or pass,
 * backgroundcolor and a sound is played. After UI update a new random song is shown and it starts
 * over. Goes on for 3 minutes.
 *
 * When time is up, the fragment returns collected points and how many songs in total was shown
 * in the round.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameRoundFragment extends Fragment implements SensorEventListener {

    private GameRoundViewModel gameRoundViewmodel;
    private View root;

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView textViewtimer;
    private TextView songLyrics;
    private TextView songTitle;
    private TextView songArtist;

    private static final String TIME_FORMAT = "%02d:%02d";
    boolean positionIsReset;
    boolean roundIsStarted = false;

    CountDownTimer startingTimer;

    private Handler handler = new Handler();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        gameRoundViewmodel =
                new ViewModelProvider(this).get(GameRoundViewModel.class);

        root = inflater.inflate(R.layout.fragment_game_round, container, false);

        setUpViewItems();
        setUpAccelerometerSensor();
        startTheStartingCountdown();

        return root;
    }

    /**
     * Starts a countdown of 3 before starting the gameRound. Displaying "3,2,1,KÖR!" to the user.
     * When countdown is finished a round is started.
     */
    private void startTheStartingCountdown() {
        startingTimer = new CountDownTimer(5000, 1000) {
            int countDownInt = 3;
            @Override
            public void onTick(long millisUntilFinished) {
                if(countDownInt == 0){
                    songLyrics.setText("KÖR!");
                }
                else{
                    songLyrics.setText(Integer.toString(countDownInt));
                    countDownInt--;
                }
            }

            @Override
            public void onFinish() {
                startTheRound();
            }
        }.start();
    }

    /**
     * Cancels the starting timer that counts the user down from 3 before starting the game.
     */
    public void cancelStartingTimer() {
        if(startingTimer!=null)
            startingTimer.cancel();
    }
    /**
     * Starting a round of the game. Sets a boolean saying that the round is started (to activate
     * sensors), starts the timer and sets a random song to the UI.
     */
    private void startTheRound() {
        roundIsStarted = true;
        startTimer();
        setSongView(gameRoundViewmodel.getNextRandomSong());
    }

    /**
     * Starts the timer for 3 minutes and sets an observer on the time left value, updating
     * the timer accordingly.
     */
    private void startTimer() {
        gameRoundViewmodel.startCountDown();
        gameRoundViewmodel.getTimerLiveData().observe(requireActivity(),this::updateTimer);
    }

    /**
     * Updates the UI timer to show user how much time is left on their turn. When its 1 second
     * or less left on the timer, the accelerometer-sensor is unregistered so that to
     * not collect any more points this round. When timer hits 0 seconds left, the game round
     * is ended.
     * @param timeLeft, how much time is left.
     */
    private void updateTimer(Long timeLeft){
        textViewtimer.setText(String.format(Locale.GERMAN,TIME_FORMAT,
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft) -
                        TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(timeLeft))));
        if(timeLeft <= 1000){
            sensorManager.unregisterListener(this);
        }
        if(timeLeft == 0){
            endRound();
        }
    }

    /**
     * Sets the songview to a new song.
     * @param song, the song to be displayed.
     */
    private void setSongView(Song song) {
        songArtist.setText(song.getArtist());
        songTitle.setText(song.getTitle());
        songLyrics.setText(song.getLyrics());
    }

    /**
     * Sets up the viewitems. In this case textviews for showing how much time is left,
     * songs title, artist and lyrics. Also sets the textviews of title and artist to blank
     * for creating a clean slate for starting countdowntimer to be shown.
     */
    private void setUpViewItems() {
        textViewtimer = root.findViewById(R.id.timer);
        songTitle = root.findViewById(R.id.songTitle);
        songArtist = root.findViewById(R.id.songArtist);
        songLyrics = root.findViewById(R.id.songLyric);

        songTitle.setText("");
        songArtist.setText("");
    }

    /**
     * End the round. Cancels the timer, puts the results (total points and total nr of songs
     * this round) in a bundle that is sent back to activity that started GameRoundFragment.
     */
    private void endRound() {
        gameRoundViewmodel.cancelTimer();
        Bundle result = new Bundle();
        result.putInt("Points",gameRoundViewmodel.getPointsThisRound());
        result.putInt("TotalSongs",gameRoundViewmodel.getTotalOfSongsThisRound());
        getParentFragmentManager().setFragmentResult("gameResults", result);
    }

    /**
     * Setting up the accelerometer motion sensor for monitoring the devices motion. Also sets
     * a boolean for showing that the device position is not reset (it resets when device
     * is held against forehead) so that no motion is wrongly detected as the user
     * moves the phone up against it's forehead.
     */
    private void setUpAccelerometerSensor() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        positionIsReset = false;
    }

    /**
     * When the device moves in any direction the method is triggered.
     * Method is set to take action when device is held horizontal (against forehead) and
     * is tilted up or down, to collect points or pass on the current song.
     *
     * A boolean is used to make sure points are not collected or songs are not passed several
     * times for one movement.
     * @param event, the event that is listened for.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[2];

        if(positionIsReset && roundIsStarted){
            // User tilts the phone downwards and passes the song, a new song is displayed.
            if (x < -7) {
                positionIsReset = false;
                updateUI(R.raw.pass, "Pass!", R.color.red_for_pass);
                gameRoundViewmodel.noPoint();
                nextSong();

            }
            //User tilts the phone upwards, gets a point and a new song is displayed.
            else if (x > 7) {
                positionIsReset = false;
                updateUI(R.raw.point, "Poäng!", R.color.green_for_points);
                gameRoundViewmodel.addPoint();
                nextSong();
            }
        }
        // The boolean is reset when user holds the phone against forehead again.
        else if( -7< x && x < 7) {
            positionIsReset = true;
        }
    }

    /**
     * Updated the UI for either pass or point, playing a sound, showing a text and
     * coloring the background.
     *
     * @param p, the ID of the soundeffect to be played.
     * @param s, the string to be displayed showing pass or point.
     * @param p2, the color of which the background is to be set.
     */
    private void updateUI(int p, String s, int p2) {
        playSoundEffect(p);
        blankOutOldSong(s);
        setBackground(p2);
    }

    /**
     * Sets the background to color p.
     * @param p, the color to be set.
     */
    private void setBackground(int p) {
        root.setBackgroundColor(ResourcesCompat.getColor(getResources(), p, null));
    }

    /**
     * Plays a soundeffect p.
     * @param p, the soundeffect to be played.
     */
    private void playSoundEffect(int p) {
       MediaPlayer.create(requireContext(), p).start();
    }

    /**
     * Blanks out the old song and shows pass or point text instead.
     * @param passOrPoint, the text to be set instead of old song.
     */
    private void blankOutOldSong(String passOrPoint) {
        songLyrics.setText(passOrPoint);
        songTitle.setText("");
        songArtist.setText("");
    }

    /**
     * Sets a delay of 1 second before setting up a new song to be displayed. Giving the user
     * a chance to see the pass or point text displayed in the UI.
     */
    private void nextSong() {
        handler.postDelayed(this::setUpNewSongRound, 1000);
    }

    /**
     * Sets up a new songround by setting songview to a new random song and changing the
     * backgroundcolor back to original.
     */
    private void setUpNewSongRound() {
        setSongView(gameRoundViewmodel.getNextRandomSong());
        setBackground(R.color.primary_green);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * Register the tilt listener.
     */
    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Unregisters the tilt listener when activity because listener is CPU and energy consuming.
     */
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelStartingTimer();
        gameRoundViewmodel.cancelTimer();
    }

    /*
    @Override
    public void onDestroy() {
        super.onDestroy();
        gameRoundViewmodel.cancelTimer();
    }*/
}