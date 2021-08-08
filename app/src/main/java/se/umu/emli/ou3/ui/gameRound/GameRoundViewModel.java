package se.umu.emli.ou3.ui.gameRound;

import android.app.Application;
import android.os.CountDownTimer;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import se.umu.emli.ou3.RandomSongGenerator;
import se.umu.emli.ou3.Song;
import se.umu.emli.ou3.ui.gameResult.GameResultFragment;

/**
 * ViewModel class.
 *
 * For holding and preparing data for the userinterface {@link GameRoundFragment}.
 * Including getting random songs, and holding the points and total of songs for the gameround.
 * Also holding the gametimer of 3 minutes giving updates via livedata to the fragment.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class GameRoundViewModel extends AndroidViewModel {

    private RandomSongGenerator randomSongGenerator;
    private MutableLiveData<Long> timerLiveData;
    private CountDownTimer countDownTimer;
    private int pointsThisRound;
    private int totalOfSongsThisRound;
    private Application application;

    public GameRoundViewModel(@NonNull @NotNull Application application) {
        super(application);
        randomSongGenerator = new RandomSongGenerator(application);
        timerLiveData = new MutableLiveData<>();
        pointsThisRound= 0;
        totalOfSongsThisRound=0;
        this.application= application;
    }

    public int getPointsThisRound(){
        return pointsThisRound;
    }

    public int getTotalOfSongsThisRound(){
        return totalOfSongsThisRound;
    }

    /**
     * Gets a new random song via the RandomSongGenerator.
     * @return a new random song.
     */
    public Song getNextRandomSong(){
        return randomSongGenerator.getRandomSong();
    }

    /**
     * Adds a point to the round. Also adds to the total of songs this round.
     */
    public void addPoint(){
        pointsThisRound++;
        totalOfSongsThisRound++;
    }

    /**
     * Only adds to the total of songs this round.
     */
    public void noPoint(){
        totalOfSongsThisRound++;
    }

    /**
     * Starts a countdown of 3 minutes and updates the UI every second, showing the user
     * how much time is left on their round. When timer is up the gameround finishes.
     * TODO: Sätt tillbaka till 3 min aka 180200 milliseconds.
     *
     */
    public void startCountDown() {
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLiveData.setValue(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerLiveData.setValue((long) 0);
            }
        }.start();
    }

    /**
     * Cancels the timer.
     */
    public void cancelTimer() {
        if(countDownTimer!=null)
            countDownTimer.cancel();
    }

    /**
     * Updating the livedata for the timer.
     * @return a long of how much time is left on timer. 
     */
    public MutableLiveData<Long> getTimerLiveData(){
        return timerLiveData;
    }
}