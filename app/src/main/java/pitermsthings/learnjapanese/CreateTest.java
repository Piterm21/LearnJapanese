package pitermsthings.learnjapanese;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTest extends Fragment {

    TextView MainTextView;
    EditText UserInput;
    Button Reverse;
    TextView hintsTextView;

    boolean[] NotValidIndexes;
    int Skipped;
    int Right;
    int Mistakes;
    long TimeStart;
    int CurrentCharacterMistakes;
    boolean disabledHints;
    int Hiragana;
    boolean TextMode;
    int LastObject=0;
    int FirstObject=0;
    String StringOnScreenSign;

    HashTableAndOther hashTableAndOther = new HashTableAndOther();

    public CreateTest() {
    }

    private void ReformatButtons(boolean Portrait)
    {
        if(!TextMode)
        {
            WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            int Width = size.x;
            float Dimensions = 25;
            int TextSize = 16;
            int NumberOfElements = 8;
            if (!Portrait)
            {
                Dimensions = 12.5f;
                TextSize = 9;

                TextView textView;
                RelativeLayout.LayoutParams layoutParams;
                for (int i = FirstObject; i <= LastObject; i++)
                {
                    textView = (TextView) getView().findViewById(i);
                    layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                    if (i - FirstObject == NumberOfElements / 2)
                    {
                        layoutParams.leftMargin = (int) ((Dimensions * Width / 100) * NumberOfElements / 2);
                    }
                    if (i - FirstObject >= NumberOfElements / 2)
                    {
                        layoutParams.addRule(RelativeLayout.BELOW, R.id.Character);
                    }
                    layoutParams.height = (int) (Dimensions * Width / 100);
                    layoutParams.width = (int) (Dimensions * Width / 100);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize * Width / 100);
                }

            } else
            {
                TextView textView;
                RelativeLayout.LayoutParams layoutParams;
                for (int i = FirstObject; i <= LastObject; i++)
                {
                    textView = (TextView) getView().findViewById(i);
                    layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                    if (i - FirstObject == NumberOfElements / 2)
                    {
                        layoutParams.leftMargin = 0;
                    }
                    if (i - FirstObject >= NumberOfElements / 2)
                    {
                        layoutParams.addRule(RelativeLayout.BELOW, FirstObject);
                    }
                    layoutParams.height = (int) (Dimensions * Width / 100);
                    layoutParams.width = (int) (Dimensions * Width / 100);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize * Width / 100);
                }
            }
        }
    }

    private void CreateButtons(RelativeLayout relativeLayout, ViewGroup container)
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int Orientation = getResources().getConfiguration().orientation;
        int Rows= 2;
        float Height = 25;
        int TextSize = 16;
        int NumberOfElements = 8;
        if(Orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Rows = 1;
            Height = 12.5f;
            TextSize = 9;
        }

        int Width = size.x;
        int LastCreatedObject=0;

        for(int j=0;j<Rows;j++)
        {
            for(int i=0;i<NumberOfElements/Rows;i++)
            {
                TextView textView = new TextView(container.getContext());
                textView.setText(""+j+i);
                textView.setGravity(Gravity.CENTER);
                textView.setId(View.generateViewId());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize*(Width)/100);
                textView.setTextColor(Color.BLACK);
                textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.backgroundletters));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView Chosen = (TextView) v;
                        String DisplayedText = MainTextView.getText().toString();
                        if(DisplayedText.length()>2)
                        {
                            char []TableChar = DisplayedText.toCharArray();
                            DisplayedText=""+TableChar[0]+TableChar[1];
                        }
                        if(hashTableAndOther.TableCharacters[Chosen.getText().toString().charAt(0)-hashTableAndOther.LowestValueChar].equals(DisplayedText))
                        {
                            Right++;

                            if((Right+Skipped)<73)
                            {
                                if(CurrentCharacterMistakes>0)
                                    ResetBackgrounds();
                                CurrentCharacterMistakes = 0;
                                StringOnScreenSign = GetNewCharacter();
                                String TextToSet = hashTableAndOther.TableCharacters[StringOnScreenSign.charAt(0)-hashTableAndOther.LowestValueChar];
                                TextToSet = PhoneticWriting(TextToSet);
                                MainTextView.setText(TextToSet);
                                FillOptions();
                            }
                            else
                            {
                                CreateAndShowMessage();
                            }
                        }
                        else
                        {
                            CurrentCharacterMistakes++;
                            Mistakes++;
                            Chosen.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.wrong));
                        }
                    }
                });


                RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams((int)(Height*(Width)/100), (int)(Height*(Width)/100));
                if(j==0)
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.Character);
                else
                    layoutParams.addRule(RelativeLayout.BELOW, FirstObject);
                if(i>0)
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, LastCreatedObject);

                LastCreatedObject = textView.getId();
                if((j*4 + i +1) == 1)
                    FirstObject=LastCreatedObject;
                if((j*4 + i +1) == NumberOfElements)
                    LastObject=LastCreatedObject;

                relativeLayout.addView(textView, layoutParams);
            }
        }
    }

    public void CreateAndShowMessage()
    {
        long TimeEnd=System.currentTimeMillis();
        long TimeElapsed = TimeEnd - TimeStart;
        AlertDialog.Builder AllDone = new AlertDialog.Builder(getActivity());
        AllDone.setNeutralButton("Try again?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResetValues();
                StringOnScreenSign = GetNewCharacter();
                if(TextMode)
                    MainTextView.setText(StringOnScreenSign);
                else
                {
                    String TextToSet = hashTableAndOther.TableCharacters[StringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
                    TextToSet = PhoneticWriting(TextToSet);
                    MainTextView.setText(TextToSet);
                    if(CurrentCharacterMistakes>0)
                        ResetBackgrounds();
                    CurrentCharacterMistakes = 0;
                    FillOptions();
                }
            }
        });
        String TimeToShow;
        if(TimeElapsed>=60*1000)
            TimeToShow=TimeElapsed/1000/60+"min "+ TimeElapsed/1000%60+"s";
        else
            TimeToShow=TimeElapsed/1000%60+"s";
        AllDone.setMessage("All done\nTime elapsed: "+TimeToShow+"\nSkipped: " + Skipped + "\nMistakes: " + Mistakes).create();
        AllDone.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            ReformatButtons(false);
        }
        else
        {
            ReformatButtons(true);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle Arguments = getArguments();
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.test_layout, container, false);
        RelativeLayout.LayoutParams layoutParams;

        Hiragana = Arguments.getInt("Hiragana");
        TextMode = Arguments.getBoolean("Text", true);
        int UserInputID=0;

        if(TextMode)
        {
            TextView textView = new TextView(container.getContext());
            textView.setText("");
            textView.setId(View.generateViewId());
            hintsTextView = textView;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.Character);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            relativeLayout.addView(textView, layoutParams);

            EditText editText = new EditText(container.getContext());
            editText.setText("");
            editText.setHint("Answer here");
            editText.setGravity(Gravity.CENTER_HORIZONTAL);
            editText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
            editText.setId(View.generateViewId());
            UserInputID = editText.getId();
            UserInput = editText;
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        UpdateWhenDone(actionId);
                        return true;
                    }
            });
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, hintsTextView.getId());
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            relativeLayout.addView(editText, layoutParams);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else
        {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
            CreateButtons(relativeLayout, container);
        }

        Button button = new Button(container.getContext());
        button.setText("Skip");
        button.setId(View.generateViewId());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Skipped++;
                if((Right+Skipped)<73)
                {
                    StringOnScreenSign = GetNewCharacter();
                    if(TextMode)
                    {
                        MainTextView.setText(StringOnScreenSign);
                    }
                    else
                    {
                        if(CurrentCharacterMistakes>0)
                            ResetBackgrounds();
                        CurrentCharacterMistakes = 0;
                        String TextToSet = hashTableAndOther.TableCharacters[StringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
                        TextToSet = PhoneticWriting(TextToSet);
                        MainTextView.setText(TextToSet);
                        FillOptions();
                    }
                }
                else
                {
                    CreateAndShowMessage();
                }

            }
        });
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if(TextMode)
            layoutParams.addRule(RelativeLayout.BELOW, UserInputID);
        else
            layoutParams.addRule(RelativeLayout.BELOW, LastObject);

        relativeLayout.addView(button, layoutParams);

        return relativeLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        MainTextView = (TextView) getView().findViewById(R.id.Character);
        if(!TextMode) {
            MainTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) MainTextView.getLayoutParams();
            layoutParams.setMargins(0,20,0,20);
        }
        Reverse = (Button) getView().findViewById(R.id.Reverse);
        Reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentExchange fragmentExchange = new FragmentExchange();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                Bundle bundleArgs = new Bundle();
                if (TextMode)
                    bundleArgs.putBoolean("Text", false);
                else
                    bundleArgs.putBoolean("Text", true);
                int FragmentIndex;
                if (Hiragana == 0)
                    FragmentIndex = 0;
                else if (Hiragana == 1)
                    FragmentIndex = 2;
                else
                    FragmentIndex = 4;
                fragmentExchange.ExchangeFragments(FragmentIndex, false, navigationView, fragmentTransaction, bundleArgs);
            }
        }
        );

        NotValidIndexes=new boolean[73];
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        disabledHints = sharedPreferences.getBoolean("disabled_hints", false);

        CurrentCharacterMistakes=0;

        TimeStart =System.currentTimeMillis();

        ResetValues();
        StringOnScreenSign = GetNewCharacter();
        if(TextMode)
            MainTextView.setText(StringOnScreenSign);
        else {
            String TextToSet = hashTableAndOther.TableCharacters[StringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
            TextToSet = PhoneticWriting(TextToSet);
            MainTextView.setText(TextToSet);
            FillOptions();
        }
    }

    public void ResetBackgrounds()
    {
        for(int i=FirstObject;i<=LastObject;i++)
        {
            TextView textView = (TextView) getView().findViewById(i);
            textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.backgroundletters));
        }
    }

    public String PhoneticWriting(String TextToSet)
    {
        switch (TextToSet)
        {
            case("SI"): {
                TextToSet += "(SHI)";
            }break;
            case("TI"): {
                TextToSet += "(CHI)";
            }break;
            case("TU"): {
                TextToSet += "(TSU)";
            }break;
            case("HU"): {
                TextToSet += "(FU)";
            }break;
            case("DU"): {
                TextToSet += "(DZU)";
            }break;
            case("WO"): {
                TextToSet += "(O)";
            }break;
            case("ZI"): {
                TextToSet += "(JI)";
            }break;
            case("DI"): {
                TextToSet += "(JI)";
            }break;
        }
        return TextToSet;
    }

    public void FillOptions()
    {
        Random random = new Random();
        TextView Answer;

        int IndexOfAnswer =random.nextInt(8)+FirstObject;
        String TextToSet;
        String []Used= new String[8];

        Answer = (TextView) getView().findViewById(IndexOfAnswer);
        TextToSet = StringOnScreenSign;
        Answer.setText(TextToSet);
        for(int i=0;i<8;i++)
            Used[i]="";
        Used[IndexOfAnswer-FirstObject]=StringOnScreenSign;
        for(int i=FirstObject;i<=LastObject;)
        {
            if(i!=IndexOfAnswer)
            {
                TextView textView = (TextView) getView().findViewById(i);
                int IndexOfText;
                int NumberOfFine=0;
                while(NumberOfFine<8)
                {
                    IndexOfText = random.nextInt(73);
                    NumberOfFine = 0;

                    if (Hiragana == 0)
                        TextToSet = hashTableAndOther.TableHiragana[IndexOfText];
                    else
                        TextToSet = hashTableAndOther.TableKatakana[IndexOfText];
                    for (int j = 0; j < 8; j++)
                    {
                        if (!Used[j].equals(TextToSet))
                            NumberOfFine++;
                        else
                            j = 8;
                    }
                }
                Used[i-FirstObject]=TextToSet;
                textView.setText(TextToSet);
                i++;
            }
            else
            {
                i++;
            }
        }
    }

    public String GetNewCharacter()
    {
        Random RandomValue = new Random();
        int Number = RandomValue.nextInt(73);
        int i=RandomValue.nextInt(73);
        if(NotValidIndexes[Number])
            Number=(Number+i)%73;
        while (NotValidIndexes[Number]) {
            Number = (Number + 1) % 73;
        }
        NotValidIndexes[Number] = true;

        String Result;
        if(Hiragana==0)
            Result = hashTableAndOther.TableHiragana[Number];
        else
            Result = hashTableAndOther.TableKatakana[Number];
        return Result;
    }

    public void ResetValues()
    {
        Right=0;
        Skipped=0;
        Mistakes=0;
        TimeStart=System.currentTimeMillis();
        for(int i=0;i<73;i++)
        {
            NotValidIndexes[i]=false;
        }
    }

    public void UpdateWhenDone(int actionId)
    {
        if (actionId == EditorInfo.IME_ACTION_DONE)
        {
            String UserInputValue = UserInput.getText().toString();
            String CharacterOnScreenInRomaji = hashTableAndOther.TableCharacters[MainTextView.getText().toString().charAt(0)-hashTableAndOther.LowestValueChar];

            switch (UserInputValue) {
                case ("shi"): {
                    UserInputValue = "si";
                }break;
                case ("chi"): {
                    UserInputValue = "ti";
                }break;
                case ("tsu"): {
                    UserInputValue = "tu";
                }break;
                case ("fu"): {
                    UserInputValue = "hu";
                }break;
                case ("dzu"):{
                    UserInputValue = "du";
                }break;
                case("o"):{
                    if(CharacterOnScreenInRomaji.equals("WO")){
                        UserInputValue = "wo";
                    }
                }break;
                case("ji"):{
                    if(CharacterOnScreenInRomaji.equals("ZI"))
                        UserInputValue = "zi";
                    else
                        if(CharacterOnScreenInRomaji.equals("DI"))
                            UserInputValue = "di";
                }break;
            }

            if(CharacterOnScreenInRomaji.equals(UserInputValue.toUpperCase()))
            {
                Right++;

                if((Right+Skipped)<73)
                {
                    hintsTextView.setText("");
                    CurrentCharacterMistakes=0;
                    MainTextView.setText(GetNewCharacter());
                }
                else
                {
                    CurrentCharacterMistakes=0;
                    CreateAndShowMessage();
                }
            }
            else
            {
                CurrentCharacterMistakes++;
                if(!disabledHints)
                {
                    Random random = new Random();
                    int randomInt= random.nextInt(2);
                    int randomInt2 = random.nextInt(2);
                    if(CurrentCharacterMistakes==3)
                    {
                        if (CharacterOnScreenInRomaji.length() == 2)
                        {
                            String TextToSet = "";
                            if (randomInt == 0)
                                TextToSet += CharacterOnScreenInRomaji.charAt(0) + "-";
                            else
                                TextToSet += "-" + CharacterOnScreenInRomaji.charAt(1);
                            hintsTextView.setText(TextToSet);
                        }
                        else
                            hintsTextView.setText("-");

                        switch (CharacterOnScreenInRomaji) {
                            case ("SI"): {
                                if(randomInt+randomInt2==2)
                                    hintsTextView.setText(hintsTextView.getText()+"(--I)");
                                else
                                if(randomInt+randomInt2==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(-H-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(S--)");
                            }break;
                            case ("TI"): {
                                if(randomInt+randomInt2==2)
                                    hintsTextView.setText(hintsTextView.getText()+"(--I)");
                                else
                                if(randomInt+randomInt2==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(-H-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(C--)");
                            }break;
                            case ("TU"): {
                                if(randomInt+randomInt2==2)
                                    hintsTextView.setText(hintsTextView.getText()+"(--U)");
                                else
                                if(randomInt+randomInt2==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(-S-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(T--)");
                            }break;
                            case ("HU"): {
                                if(randomInt==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(F-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(-U)");
                            }break;
                            case ("DU"):{
                                if(randomInt+randomInt2==2)
                                    hintsTextView.setText(hintsTextView.getText()+"(--U)");
                                else
                                if(randomInt+randomInt2==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(-Z-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(D--)");
                            }break;
                            case("WO"):{
                                hintsTextView.setText(hintsTextView.getText()+"(-)");
                            }break;
                            case("ZI"):{
                                if(randomInt==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(J-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(-I)");
                            }break;
                            case("DI"):{
                                if(randomInt==1)
                                    hintsTextView.setText(hintsTextView.getText()+"(J-)");
                                else
                                    hintsTextView.setText(hintsTextView.getText()+"(-I)");
                            }break;
                        }
                    }
                }
                Mistakes++;
            }
        }
            UserInput.setText("");
    }
}

