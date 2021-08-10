package se.umu.emli.ou3.ui.rules;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.umu.emli.ou3.R;

/**
 * View class.
 *
 * Handles UI for showing the user the rules of the game.
 *
 * @author Emmy Lindgren, emli.
 * @version 1.0
 */
public class RulesFragment extends Fragment {

    private RulesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rules_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}