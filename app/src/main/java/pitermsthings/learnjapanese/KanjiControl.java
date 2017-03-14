package pitermsthings.learnjapanese;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piterm on 22.06.2016.
 */

public class KanjiControl extends MainNavDrawer {

    public KanjiControl()
    {
        RestoreFromStorage();
    }

    ArrayList<List<String>[]> KanjiTable =  new ArrayList<>();
    List<String> CurrentCharacterKun;
    List<String> CurrentCharacterOn;
    List<String> CurrentCharacterMeaning;
    int NumberOfElements=0;

    public void CreateNewKanji(List<String> UserInputKanji, List<String> UserInputKun, List<String> UserInputOn, List<String> UserInputMeaning)
    {
        List<String>[] DataToInput=new List[4];
        DataToInput[0]=UserInputKanji;
        DataToInput[1]=UserInputKun;
        DataToInput[2]=UserInputOn;
        DataToInput[3]=UserInputMeaning;
        KanjiTable.add(DataToInput);
        NumberOfElements++;
        SaveToStorage();
    }

    public void SaveToStorage()
    {

    }
    public void RestoreFromStorage()
    {

    }
}
