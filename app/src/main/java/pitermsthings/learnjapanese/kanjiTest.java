package pitermsthings.learnjapanese;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class kanjiTest extends Fragment {

    TextView AddNewKanji;
    TwoDirectionQueue<EditText> KunYomiList =  new TwoDirectionQueue<>();
    TwoDirectionQueue<EditText> OnYomiList =  new TwoDirectionQueue<>();
    TwoDirectionQueue<EditText> MeaningList =  new TwoDirectionQueue<>();
    KanjiControl KanjiStorage = new KanjiControl();

    public kanjiTest() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int Width = size.x;

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.test_layout, container, false);

        TextView textView = new TextView(container.getContext());
        textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.buttonbackground));
        textView.setText("+");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 15*Width/100);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        AddNewKanji= textView;

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(20*Width/100, 20*Width/100);
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.Reverse);
        layoutParams.leftMargin = 5*Width/100;

        relativeLayout.addView(textView,layoutParams);

        return relativeLayout;
    }

    private void AddOnClickListener(int Type, final TwoDirectionQueue<EditText> List, final RelativeLayout dialogBox)
    {
        final int IdOfTop;
        int IdOfLabel;
        final TextView Label;
        final RelativeLayout.LayoutParams layoutParamsText;
        String Hint;
        int IdOfNew;

        switch(Type)
        {
            case(0):
            {
                IdOfTop = R.id.UserKanji;
                IdOfLabel = R.id.TextKunYomi;
                Label = (TextView) dialogBox.findViewById(IdOfLabel);
                layoutParamsText  = (RelativeLayout.LayoutParams) Label.getLayoutParams();
                Hint = "Kun-Yomi";
            } break;
            case(1):
            {
                IdOfTop = R.id.UserKunYomi;
                IdOfLabel = R.id.TextOnYomi;
                Label = (TextView) dialogBox.findViewById(IdOfLabel);
                layoutParamsText  = (RelativeLayout.LayoutParams) Label.getLayoutParams();
                Hint = "On-Yomi";
            } break;
            case(2):
            {
                IdOfTop = R.id.UserOnYomi;
                IdOfLabel = R.id.TextMeaning;
                Label = (TextView) dialogBox.findViewById(IdOfLabel);
                layoutParamsText  = (RelativeLayout.LayoutParams) Label.getLayoutParams();
                Hint = "Meaning";
            } break;
            default:
            {
                IdOfTop = R.id.UserKanji;
                IdOfLabel = R.id.TextKunYomi;
                Label = (TextView) dialogBox.findViewById(IdOfLabel);
                layoutParamsText  = (RelativeLayout.LayoutParams) Label.getLayoutParams();
                Hint = "Kun-Yomi";
            } break;
        }

        final EditText NewEditText = new EditText(dialogBox.getContext());
        NewEditText.setHint(Hint);
        NewEditText.setId(View.generateViewId());
        NewEditText.setGravity(Gravity.CENTER);
        NewEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        IdOfNew = NewEditText.getId();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, IdOfTop);
        layoutParams.addRule(RelativeLayout.END_OF, IdOfLabel);

        EditText OneBelow = List.returnValueOfFirst();
        RelativeLayout.LayoutParams layoutParamsBelow = (RelativeLayout.LayoutParams) OneBelow.getLayoutParams();
        layoutParamsBelow.addRule(RelativeLayout.BELOW, IdOfNew);
        OneBelow.setLayoutParams(layoutParamsBelow);

        List.addToList(NewEditText);

        layoutParamsText.addRule(RelativeLayout.ALIGN_BASELINE, IdOfNew);
        Label.setLayoutParams(layoutParamsText);

        dialogBox.addView(NewEditText,layoutParams);

        final Button deleteView = new Button(dialogBox.getContext(), null ,R.attr.buttonStyleSmall);
        deleteView.setText("-");
        deleteView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialogBox.removeView(NewEditText);
                dialogBox.removeView(deleteView);
                EditText OneBelow = List.returnValueOfNext(NewEditText);
                RelativeLayout.LayoutParams layoutParamsBelow = (RelativeLayout.LayoutParams) OneBelow.getLayoutParams();
                if (List.returnSecond() == OneBelow)
                {
                    layoutParamsText.addRule(RelativeLayout.ALIGN_BASELINE, OneBelow.getId());
                    Label.setLayoutParams(layoutParamsText);
                    layoutParamsBelow.addRule(RelativeLayout.BELOW, IdOfTop);
                }
                else
                {
                    layoutParamsBelow.addRule(RelativeLayout.BELOW, List.returnValueOfOneBefore(NewEditText).getId());
                }
                OneBelow.setLayoutParams(layoutParamsBelow);
                List.removeElement(NewEditText);
            }
        });
        RelativeLayout.LayoutParams layoutParamsDelete = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsDelete.addRule(RelativeLayout.END_OF, NewEditText.getId());
        layoutParamsDelete.addRule(RelativeLayout.ALIGN_TOP, NewEditText.getId());
        dialogBox.addView(deleteView, layoutParamsDelete);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        AddNewKanji.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final RelativeLayout dialogLayout = (RelativeLayout) getLayoutInflater(null).inflate(R.layout.dialogkanji, null);
                final EditText UserKanji = (EditText) dialogLayout.findViewById(R.id.UserKanji);

                final AlertDialog.Builder AddKanji = new AlertDialog.Builder(getActivity());
                AddKanji.setView(dialogLayout);

                KunYomiList.addToList((EditText) dialogLayout.findViewById(R.id.UserKunYomi));
                OnYomiList.addToList((EditText) dialogLayout.findViewById(R.id.UserOnYomi));
                MeaningList.addToList((EditText) dialogLayout.findViewById(R.id.UserMeaning));

                Button buttonAddKunYomi = (Button) dialogLayout.findViewById(R.id.AddKunYomi);
                buttonAddKunYomi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        AddOnClickListener(0,KunYomiList, dialogLayout);
                    }
                });

                Button buttonAddOnYomi = (Button) dialogLayout.findViewById(R.id.AddOnYomi);
                buttonAddOnYomi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        AddOnClickListener(1,OnYomiList, dialogLayout);
                    }
                });

                Button buttonAddMeaning = (Button) dialogLayout.findViewById(R.id.AddMeaning);
                buttonAddMeaning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        AddOnClickListener(2,MeaningList, dialogLayout);
                    }
                });

                AddKanji.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        KunYomiList.clearExepctLast();
                        OnYomiList.clearExepctLast();
                        MeaningList.clearExepctLast();
                    }
                });

                AddKanji.setPositiveButton("Finish", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                AddKanji.create();
                final AlertDialog dialog = AddKanji.show();

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

                button.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        boolean OK=true;
                        String UserKanjiString = UserKanji.getText().toString();
                        if(UserKanjiString.isEmpty())
                            OK=false;
                        else
                        {
                            if((UserKanjiString.charAt(0) < 0x4e00 || UserKanjiString.charAt(0) > 0x9faf) && (UserKanjiString.charAt(0) < 0x3400 || UserKanjiString.charAt(0) > 0x4dbf))
                                OK=false;
                            boolean AllEmpty = true;
                            int Limit = KunYomiList.size();
                            for(int i=0;i<Limit;i++)
                            {
                                if (!KunYomiList.returnValueOfIndex(i).getText().toString().isEmpty())
                                {
                                    AllEmpty = false;
                                    i=Limit;
                                }
                            }
                            if(AllEmpty)
                                OK=false;
                            else
                            {
                                boolean AllEmptyOnYomi = true;
                                int LimitOn = OnYomiList.size();
                                for(int i=0;i<LimitOn;i++)
                                {
                                    if (!OnYomiList.returnValueOfIndex(i).getText().toString().isEmpty())
                                    {
                                        AllEmptyOnYomi = false;
                                        i=LimitOn;
                                    }
                                }
                                if(AllEmptyOnYomi)
                                    OK=false;
                                else
                                {
                                    boolean AllEmptyMeaning = true;
                                    int LimitMeaning = MeaningList.size();
                                    for(int i=0;i<LimitMeaning;i++)
                                    {
                                        if (!MeaningList.returnValueOfIndex(i).getText().toString().isEmpty())
                                        {
                                            AllEmptyMeaning = false;
                                            i=LimitMeaning;
                                        }
                                    }
                                    if(AllEmptyMeaning)
                                        OK=false;
                                }
                            }
                        }

                        if(OK)
                        {
                            int LimitKun = KunYomiList.size()-1;
                            int LimitOn = OnYomiList.size()-1;
                            int LimitMeaning = MeaningList.size()-1;
                            String ValueOfField;

                            List<String> KunYomi = new LinkedList<String>();
                            List<String> OnYomi = new LinkedList<String>();
                            List<String> Meaning = new LinkedList<String>();
                            List<String> Kanji = new LinkedList<String>();

                            Kanji.add(UserKanji.getText().toString());

                            for (int i = 0; i < LimitKun; i++)
                            {
                                ValueOfField = KunYomiList.returnValueOfFirst().getText().toString();
                                if(!ValueOfField.isEmpty())
                                    KunYomi.add(ValueOfField);
                                KunYomiList.removeFirst();
                            }
                            ValueOfField = KunYomiList.returnValueOfFirst().getText().toString();
                            if(!ValueOfField.isEmpty())
                                KunYomi.add(ValueOfField);

                            for (int i = 0; i < LimitOn; i++)
                            {
                                ValueOfField = OnYomiList.returnValueOfFirst().getText().toString();
                                if(!ValueOfField.isEmpty())
                                    OnYomi.add(ValueOfField);
                                OnYomiList.removeFirst();
                            }
                            ValueOfField = OnYomiList.returnValueOfFirst().getText().toString();
                            if(!ValueOfField.isEmpty())
                                OnYomi.add(ValueOfField);

                            for (int i = 0; i < LimitMeaning; i++)
                            {
                                ValueOfField = MeaningList.returnValueOfFirst().getText().toString();
                                if(!ValueOfField.isEmpty())
                                    Meaning.add(ValueOfField);
                                MeaningList.removeFirst();
                            }
                            ValueOfField = MeaningList.returnValueOfFirst().getText().toString();
                            if(!ValueOfField.isEmpty())
                                Meaning.add(ValueOfField);

                            KanjiStorage.CreateNewKanji(Kanji,KunYomi,OnYomi,Meaning);

                            dialog.dismiss();
                        }
                        else
                        {
                            AlertDialog.Builder NotEnoughData = new AlertDialog.Builder(getContext());
                            NotEnoughData.setNeutralButton("OK", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {

                                }
                            });
                            NotEnoughData.setMessage("Not enough information provided you need to provide at least one Kanji,Kun-Yomi reading,On-Yomi reading and Meaning");
                            NotEnoughData.show();
                        }
                    }
                });
            }
        });
    }
}
