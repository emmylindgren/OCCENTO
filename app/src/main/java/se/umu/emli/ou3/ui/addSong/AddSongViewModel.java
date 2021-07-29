package se.umu.emli.ou3.ui.addSong;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddSongViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddSongViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}