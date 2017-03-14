package pitermsthings.learnjapanese;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    CheckBox checkBox;

    public Settings() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        SharedPreferences userPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        checkBox = (CheckBox) getView().findViewById(R.id.hints_check);
        checkBox.setChecked(userPreferences.getBoolean("disabled_hints", false));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                SharedPreferences userPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putBoolean("disabled_hints", isChecked);
                editor.apply();
            }
        });
    }
}
