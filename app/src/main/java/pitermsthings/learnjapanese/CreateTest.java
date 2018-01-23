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
    TextView mainTextView;
    EditText userInput;
    Button reverse;
    TextView hintsTextView;
    boolean[] notValidIndexes;
    int skipped;
    int right;
    int mistakes;
    long timeStart;
    int currentCharacterMistakes;
    boolean disabledHints;
    boolean katakana;
    boolean textMode;
    int lastObject = 0;
    int firstObject = 0;
    String stringOnScreenSign;
    HashTableAndOther hashTableAndOther = new HashTableAndOther();

    public CreateTest() {
    }

    private void ReformatButtons(boolean portrait) {
        if (!textMode) {
            WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            float dimensions = 25;
            int textSize = 16;
            int numberOfElements = 8;
            TextView textView;
            RelativeLayout.LayoutParams layoutParams;

            if (!portrait) {
                dimensions = 12.5f;
                textSize = 9;
            }

            for (int i = firstObject; i <= lastObject; i++) {
                textView = (TextView) getView().findViewById(i);
                layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();

                if (i - firstObject >= numberOfElements / 2) {
                    if (!portrait) {
                        layoutParams.addRule(RelativeLayout.BELOW, R.id.Character);

                        if (i - firstObject == numberOfElements / 2) {
                            layoutParams.leftMargin = (int) ((dimensions * width / 100) * numberOfElements / 2);
                        }
                    } else {
                        layoutParams.addRule(RelativeLayout.BELOW, firstObject);

                        if (i - firstObject == numberOfElements / 2) {
                            layoutParams.leftMargin = 0;
                        }
                    }
                }

                layoutParams.height = (int) (dimensions * width / 100);
                layoutParams.width = (int) (dimensions * width / 100);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * width / 100);
            }
        }
    }

    private void CreateButtons(RelativeLayout relativeLayout, ViewGroup container) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int orientation = getResources().getConfiguration().orientation;
        int rows = 2;
        float height = 25;
        int textSize = 16;
        int numberOfElements = 8;
        int width = size.x;
        int lastCreatedObject = 0;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rows = 1;
            height = 12.5f;
            textSize = 9;
        }

        for (int j=0; j < rows; j++) {
            for (int i=0;i<numberOfElements/rows;i++) {
                TextView textView = new TextView(container.getContext());
                textView.setText(""+j+i);
                textView.setGravity(Gravity.CENTER);
                textView.setId(View.generateViewId());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize*(width)/100);
                textView.setTextColor(Color.BLACK);
                textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.backgroundletters));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView chosen = (TextView) v;
                        String displayedText = mainTextView.getText().toString();
                        if (displayedText.length()>2) {
                            char []tableChar = displayedText.toCharArray();
                            displayedText=""+tableChar[0]+tableChar[1];
                        }

                        if (hashTableAndOther.TableCharacters[chosen.getText().toString().charAt(0)-hashTableAndOther.LowestValueChar].equals(displayedText)) {
                            right++;

                            if ((right + skipped) < 73) {
                                if (currentCharacterMistakes > 0) {
                                    ResetBackgrounds();
                                }

                                currentCharacterMistakes = 0;
                                stringOnScreenSign = GetNewCharacter();
                                String textToSet = hashTableAndOther.TableCharacters[stringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
                                textToSet = PhoneticWriting(textToSet);
                                mainTextView.setText(textToSet);
                                FillOptions();
                            } else {
                                CreateAndShowMessage();
                            }
                        } else {
                            currentCharacterMistakes++;
                            mistakes++;
                            chosen.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.wrong));
                        }
                    }
                });

                RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams((int)(height*(width)/100), (int)(height*(width)/100));
                if (j == 0) {
                    layoutParams.addRule(RelativeLayout.BELOW, R.id.Character);
                } else {
                    layoutParams.addRule(RelativeLayout.BELOW, firstObject);
                }

                if (i > 0) {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, lastCreatedObject);
                }

                lastCreatedObject = textView.getId();

                if ((j*4 + i +1) == 1) {
                    firstObject = lastCreatedObject;
                }

                if ((j*4 + i +1) == numberOfElements) {
                    lastObject = lastCreatedObject;
                }

                relativeLayout.addView(textView, layoutParams);
            }
        }
    }

    public void CreateAndShowMessage()
    {
        long timeEnd = System.currentTimeMillis();
        long timeElapsed = timeEnd - timeStart;
        AlertDialog.Builder allDone = new AlertDialog.Builder(getActivity());
        allDone.setNeutralButton("Try again?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResetValues();
                stringOnScreenSign = GetNewCharacter();

                if (textMode) {
                    mainTextView.setText(stringOnScreenSign);
                } else {
                    String textToSet = hashTableAndOther.TableCharacters[stringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
                    textToSet = PhoneticWriting(textToSet);
                    mainTextView.setText(textToSet);

                    if (currentCharacterMistakes > 0) {
                        ResetBackgrounds();
                    }

                    currentCharacterMistakes = 0;
                    FillOptions();
                }
            }
        });
        String timeToShow;

        if (timeElapsed >= 60*1000) {
            timeToShow = timeElapsed / 1000 / 60 + "min " + timeElapsed / 1000 % 60 + "s";
        } else {
            timeToShow = timeElapsed / 1000 % 60 + "s";
        }

        allDone.setMessage("All done\nTime elapsed: "+timeToShow+"\nskipped: " + skipped + "\nmistakes: " + mistakes).create();
        allDone.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ReformatButtons(false);
        } else {
            ReformatButtons(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.test_layout, container, false);
        RelativeLayout.LayoutParams layoutParams;
        katakana = arguments.getBoolean("katakana");
        textMode = arguments.getBoolean("Text", true);
        int userInputID = 0;

        if (textMode) {
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
            userInputID = editText.getId();
            userInput = editText;

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
        } else {
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
                skipped++;

                if ((right + skipped) < 73) {
                    stringOnScreenSign = GetNewCharacter();
                    if (textMode) {
                        mainTextView.setText(stringOnScreenSign);
                    } else {
                        if (currentCharacterMistakes >0) {
                            ResetBackgrounds();
                        }

                        currentCharacterMistakes = 0;
                        String TextToSet = hashTableAndOther.TableCharacters[stringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
                        TextToSet = PhoneticWriting(TextToSet);
                        mainTextView.setText(TextToSet);
                        FillOptions();
                    }
                } else {
                    CreateAndShowMessage();
                }

            }
        });

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        if (textMode) {
            layoutParams.addRule(RelativeLayout.BELOW, userInputID);
        } else {
            layoutParams.addRule(RelativeLayout.BELOW, lastObject);
        }

        relativeLayout.addView(button, layoutParams);

        return relativeLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        mainTextView = (TextView) getView().findViewById(R.id.Character);

        if (!textMode) {
            mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mainTextView.getLayoutParams();
            layoutParams.setMargins(0,20,0,20);
        }

        reverse = (Button) getView().findViewById(R.id.Reverse);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                FragmentExchange fragmentExchange = new FragmentExchange();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                Bundle bundleArgs = new Bundle();

                if (textMode) {
                    bundleArgs.putBoolean("Text", false);
                } else {
                    bundleArgs.putBoolean("Text", true);
                }

                int FragmentIndex;

                if (!katakana) {
                    FragmentIndex = 0;
                } else {
                    FragmentIndex = 2;
                }

                fragmentExchange.ExchangeFragments(FragmentIndex, false, navigationView, fragmentTransaction, bundleArgs);
            }
        }
        );

        notValidIndexes = new boolean[73];
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        disabledHints = sharedPreferences.getBoolean("disabled_hints", false);
        currentCharacterMistakes = 0;
        timeStart = System.currentTimeMillis();
        ResetValues();
        stringOnScreenSign = GetNewCharacter();

        if(textMode) {
            mainTextView.setText(stringOnScreenSign);
        } else {
            String textToSet = hashTableAndOther.TableCharacters[stringOnScreenSign.charAt(0) - hashTableAndOther.LowestValueChar];
            textToSet = PhoneticWriting(textToSet);
            mainTextView.setText(textToSet);
            FillOptions();
        }
    }

    public void ResetBackgrounds() {
        for (int i = firstObject; i<= lastObject; i++) {
            TextView textView = (TextView) getView().findViewById(i);
            textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.backgroundletters));
        }
    }

    public String PhoneticWriting(String TextToSet) {
        switch (TextToSet) {
            case("SI"): {
                TextToSet += "(SHI)";
            } break;

            case("TI"): {
                TextToSet += "(CHI)";
            } break;

            case("TU"): {
                TextToSet += "(TSU)";
            } break;

            case("HU"): {
                TextToSet += "(FU)";
            } break;

            case("DU"): {
                TextToSet += "(DZU)";
            } break;

            case("WO"): {
                TextToSet += "(O)";
            } break;

            case("ZI"): {
                TextToSet += "(JI)";
            } break;

            case("DI"): {
                TextToSet += "(JI)";
            } break;
        }

        return TextToSet;
    }

    public void FillOptions() {
        Random random = new Random();
        TextView Answer;

        int IndexOfAnswer = random.nextInt(8) + firstObject;
        String TextToSet;
        String []Used = new String[8];

        Answer = (TextView) getView().findViewById(IndexOfAnswer);
        TextToSet = stringOnScreenSign;
        Answer.setText(TextToSet);

        for (int i = 0; i < 8; i++) {
            Used[i] = "";
        }

        Used[IndexOfAnswer - firstObject] = stringOnScreenSign;

        for (int i = firstObject; i <= lastObject; i++) {
            if (i != IndexOfAnswer) {
                TextView textView = (TextView) getView().findViewById(i);
                int IndexOfText;
                int NumberOfFine = 0;
                while(NumberOfFine < 8) {
                    IndexOfText = random.nextInt(73);
                    NumberOfFine = 0;

                    if (!katakana) {
                        TextToSet = hashTableAndOther.TableHiragana[IndexOfText];
                    } else {
                        TextToSet = hashTableAndOther.TableKatakana[IndexOfText];
                    }

                    for (int j = 0; j < 8; j++) {
                        if (!Used[j].equals(TextToSet)) {
                            NumberOfFine++;
                        } else {
                            break;
                        }
                    }
                }

                Used[i - firstObject] = TextToSet;
                textView.setText(TextToSet);
            }
        }
    }

    public String GetNewCharacter() {
        Random RandomValue = new Random();
        int Number = RandomValue.nextInt(73);
        int i = RandomValue.nextInt(73);
        String Result;

        while (notValidIndexes[Number]) {
            Number = (Number + 1) % 73;
        }

        notValidIndexes[Number] = true;

        if(!katakana) {
            Result = hashTableAndOther.TableHiragana[Number];
        } else {
            Result = hashTableAndOther.TableKatakana[Number];
        }

        return Result;
    }

    public void ResetValues() {
        right = 0;
        skipped = 0;
        mistakes = 0;
        timeStart = System.currentTimeMillis();

        for(int i = 0; i < 73; i++) {
            notValidIndexes[i] = false;
        }
    }

    public StringBuilder extendHint(String fullAdditional, int indexToSet, StringBuilder builderToChange) {
        StringBuilder phoneticRepresentation = new StringBuilder("");

        for (int i = 0; i < fullAdditional.length(); i++) {
            phoneticRepresentation.append('-');
        }

        phoneticRepresentation.setCharAt(indexToSet, fullAdditional.charAt(indexToSet));
        builderToChange.append("(");
        builderToChange.append(phoneticRepresentation);
        builderToChange.append(")");

        return builderToChange;
    }

    public void UpdateWhenDone(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String UserInputValue = userInput.getText().toString();
            String CharacterOnScreenInRomaji = hashTableAndOther.TableCharacters[mainTextView.getText().toString().charAt(0) - hashTableAndOther.LowestValueChar];

            switch (UserInputValue) {
                case ("shi"): {
                    UserInputValue = "si";
                } break;

                case ("chi"): {
                    UserInputValue = "ti";
                } break;

                case ("tsu"): {
                    UserInputValue = "tu";
                } break;

                case ("fu"): {
                    UserInputValue = "hu";
                } break;

                case ("dzu"):{
                    UserInputValue = "du";
                } break;

                case("o"):{
                    if (CharacterOnScreenInRomaji.equals("WO")) {
                        UserInputValue = "wo";
                    }
                } break;

                case("ji"):{
                    if (CharacterOnScreenInRomaji.equals("ZI")) {
                        UserInputValue = "zi";
                    } else {
                        if (CharacterOnScreenInRomaji.equals("DI")) {
                            UserInputValue = "di";
                        }
                    }
                } break;
            }

            if (CharacterOnScreenInRomaji.equals(UserInputValue.toUpperCase())) {
                right++;

                if ((right + skipped)<73) {
                    hintsTextView.setText("");
                    currentCharacterMistakes = 0;
                    mainTextView.setText(GetNewCharacter());
                } else {
                    currentCharacterMistakes = 0;
                    CreateAndShowMessage();
                }
            } else {
                currentCharacterMistakes++;

                if (!disabledHints) {
                    Random random = new Random();
                    int randomInt = random.nextInt(2);

                    if (currentCharacterMistakes == 3) {
                        StringBuilder TextToSet;

                        if (CharacterOnScreenInRomaji.length() == 2) {
                            TextToSet = new StringBuilder("--");
                            TextToSet.setCharAt(randomInt, CharacterOnScreenInRomaji.charAt(randomInt));
                        } else {
                            TextToSet = new StringBuilder("-");
                        }

                        switch (CharacterOnScreenInRomaji) {
                            case ("SI"): {
                                TextToSet = extendHint("SHI", randomInt + random.nextInt(2), TextToSet);
                            } break;

                            case ("TI"): {
                                TextToSet = extendHint("CHI", randomInt + random.nextInt(2), TextToSet);
                            } break;

                            case ("TU"): {
                                TextToSet = extendHint("TSU", randomInt + random.nextInt(2), TextToSet);
                            } break;

                            case ("HU"): {
                                TextToSet = extendHint("FU", randomInt, TextToSet);
                            } break;

                            case ("DU"): {
                                TextToSet = extendHint("DZU", randomInt + random.nextInt(2), TextToSet);
                            } break;

                            case ("WO"): {
                                TextToSet.append("(-)");
                            } break;

                            case ("ZI"):
                            case ("DI"): {
                                TextToSet = extendHint("JI", randomInt, TextToSet);
                            }break;
                        }

                        hintsTextView.setText(TextToSet.toString());
                    }
                }
                mistakes++;
            }
        }
            userInput.setText("");
    }
}

