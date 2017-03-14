package pitermsthings.learnjapanese;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.fragment_main, container, false);

        Button[] ButtonsToSet=new Button[7];
        ButtonsToSet[0]=(Button) view.findViewById(R.id.HiraganaTest);
        ButtonsToSet[1]=(Button) view.findViewById(R.id.HiraganaTable);
        ButtonsToSet[2]=(Button) view.findViewById(R.id.KatakanaTest);
        ButtonsToSet[3]=(Button) view.findViewById(R.id.KatakanaTable);
        ButtonsToSet[4]=(Button) view.findViewById(R.id.KanjiTest);
        ButtonsToSet[5]=(Button) view.findViewById(R.id.KanjiTable);
        ButtonsToSet[6]=(Button) view.findViewById(R.id.More);

        for(int i=0;i<7;i++)
        {
            ButtonsToSet[i].setOnClickListener(buttonListener);
        }

        return view;
    }

    View.OnClickListener buttonListener= new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            int IdOfCaller=v.getId();

            android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
            int NumberOfItemMenu=0;

            switch(IdOfCaller)
            {
                case(R.id.HiraganaTest):
                {
                    NumberOfItemMenu=0;
                }break;
                case(R.id.HiraganaTable):
                {
                    NumberOfItemMenu=1;
                }break;
                case(R.id.KatakanaTest):
                {
                    NumberOfItemMenu=2;
                }break;
                case(R.id.KatakanaTable):
                {
                    NumberOfItemMenu=3;
                }break;
                case(R.id.KanjiTest):
                {
                    NumberOfItemMenu=4;
                }break;
                case(R.id.KanjiTable):
                {
                    NumberOfItemMenu=5;
                }break;
                case(R.id.More):
                {
                    NumberOfItemMenu=6;
                }break;
            }
            FragmentExchange fragmentExchange = new FragmentExchange();
            fragmentExchange.ExchangeFragments(NumberOfItemMenu, true, navigationView, fragmentTransaction, null);
        }
    };

}
