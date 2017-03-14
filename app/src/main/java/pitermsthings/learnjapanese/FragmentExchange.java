package pitermsthings.learnjapanese;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

/**
 * Created by Piterm on 29.06.2016.
 */
public class FragmentExchange extends MainNavDrawer
{
    public void ExchangeFragments(int FragmentToLoad, boolean Button, NavigationView navigationView, android.support.v4.app.FragmentTransaction fragmentTransaction, Bundle Argments)
    {
        Bundle bundle;
        if(Argments==null)
            bundle=new Bundle();
        else
            bundle=Argments;

        Fragment fragment;
        switch(FragmentToLoad) {
            case(-2): {
                fragment = new MainFragment();
            }break;
            case(-1): {
                fragment = new Settings();
                fragmentTransaction.addToBackStack("Settings");
            }break;
            case(0): {
                bundle.putInt("Hiragana", 0);

                fragmentTransaction.addToBackStack("CreateTestH");
                fragment = new CreateTest();
                fragment.setArguments(bundle);
            }break;
            case(1): {
                bundle.putBoolean("Hiragana", true);

                fragmentTransaction.addToBackStack("DrawTableH");
                fragment = new DrawTableSmall();
                fragment.setArguments(bundle);
            }break;
            case(2): {
                bundle.putInt("Hiragana", 1);

                fragmentTransaction.addToBackStack("CreateTestK");
                fragment = new CreateTest();
                fragment.setArguments(bundle);
            }break;
            case(3): {
                bundle.putBoolean("Hiragana", false);

                fragmentTransaction.addToBackStack("DrawTableK");
                fragment = new DrawTableSmall();
                fragment.setArguments(bundle);
            }break;
            case(4): {
                fragmentTransaction.addToBackStack("KanjiTest");
                fragment = new kanjiTest();
            }break;
            case(5): {
               // fragmentTransaction.addToBackStack("asd");
               // fragment = new DrawTableSmall();
                fragment = new MainFragment();
            }break;
            case(6): {
               // fragmentTransaction.addToBackStack("asd");
               // fragment = new DrawTableSmall();
                fragment = new MainFragment();
            }break;
            default:
            {
                fragment = new MainFragment();
            }break;
        }

        if(Button)
            navigationView.getMenu().getItem(FragmentToLoad).setChecked(true);

        fragmentTransaction.replace(R.id.FragmanetContainer, fragment);
        fragmentTransaction.commit();
    }
}
