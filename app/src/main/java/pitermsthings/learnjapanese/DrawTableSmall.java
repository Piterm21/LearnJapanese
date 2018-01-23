package pitermsthings.learnjapanese;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawTableSmall extends Fragment {

    HashTableAndOther hashTableAndOther=new HashTableAndOther();
    boolean Hiragana;

    public DrawTableSmall() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        Bundle bundle = getArguments();
        Hiragana = bundle.getBoolean("hiragana");
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int Width = size.x;

        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.table_layout, container, false);
        ScrollView scrollView = (ScrollView) relativeLayout.getChildAt(0);
        final RelativeLayout FinalView = new RelativeLayout(scrollView.getContext());
        scrollView.addView(FinalView);

        int Letter = 0;

        for(int i = 0; i < 17 ; i++) {
            for (int j = 0; j < 6; j++) {
                TextView textView = new TextView(container.getContext());
                textView.setId(View.generateViewId());
                textView.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams layoutParams;
                layoutParams = new RelativeLayout.LayoutParams(17*Width/100, 17*Width/100);
                layoutParams.topMargin = (i-1)*(17*Width/100)+(10*Width/100);

                if (i == 0) {
                    switch (j) {
                        case (0): {
                            layoutParams = new RelativeLayout.LayoutParams(10 * Width / 100, 10 * Width / 100);
                        } break;

                        case (1): {
                            layoutParams = new RelativeLayout.LayoutParams(17 * Width / 100, 10 * Width / 100);
                            textView.setText("A");
                        } break;

                        case (2): {
                            layoutParams = new RelativeLayout.LayoutParams(17 * Width / 100, 10 * Width / 100);
                            textView.setText("I");
                        }break;

                        case (3): {
                            layoutParams = new RelativeLayout.LayoutParams(17 * Width / 100, 10 * Width / 100);
                            textView.setText("U");
                        }break;

                        case (4): {
                            layoutParams = new RelativeLayout.LayoutParams(17 * Width / 100, 10 * Width / 100);
                            textView.setText("E");
                        } break;

                        case (5): {
                            layoutParams = new RelativeLayout.LayoutParams(17 * Width / 100, 10 * Width / 100);
                            textView.setText("O");
                        } break;
                    }
                }

                if (j < 2) {
                    layoutParams.leftMargin =10*Width/100*j;
                } else {
                    layoutParams.leftMargin=(10* Width/100)+(17*Width/100*(j-1));
                }

                if (i != 0) {
                    if (j == 0) {
                        layoutParams = new RelativeLayout.LayoutParams(10*Width/100,17*Width/100);
                        layoutParams.topMargin=(i-1)*(17*Width/100)+(10*Width/100);

                        if (i != 1) {
                            textView.setText(hashTableAndOther.OtherLetters[i - 2]);
                        }
                    } else {
                        textView.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.backgroundletters));

                        if(Hiragana) {
                            textView.setText(hashTableAndOther.TableHiragana[Letter]);
                        } else {
                            textView.setText(hashTableAndOther.TableKatakana[Letter]);
                        }

                        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.aidsyellow));
                        textView.setClickable(true);
                        textView.setOnClickListener(TextViewClickListener);
                        Letter++;
                    }
                }

                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
                FinalView.addView(textView, layoutParams);

                if (i == 16 && j == 1) {
                    j = 6;
                }

                if (i == 15 && j == 2) {
                    j++;
                }

                if (i == 13 && (j == 1 || j == 3)) {
                    j++;
                }
            }
        }
        return relativeLayout;
    }

    View.OnClickListener TextViewClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            View dialogLayout = getLayoutInflater(null).inflate(R.layout.dialog_layout, null);

            AlertDialog.Builder Enlarge = new AlertDialog.Builder(getActivity());
            Enlarge.setView(dialogLayout);
            TextView character = (TextView) dialogLayout.findViewById(R.id.Character);
            TextView undercharacter = (TextView) dialogLayout.findViewById(R.id.UnderCharacter);
            TextView clickedCharacter = (TextView) v;
            character.setTextSize(TypedValue.COMPLEX_UNIT_SP,100);
            character.setText(clickedCharacter.getText().toString());
            String translated = hashTableAndOther.TableCharacters[clickedCharacter.getText().toString().charAt(0)-hashTableAndOther.LowestValueChar];

            switch (translated) {
                case ("SI"): {
                    undercharacter.setText("SI (SHI)");
                } break;

                case ("TI"): {
                    undercharacter.setText("TI (CHI)");
                }break;

                case ("TU"): {
                    undercharacter.setText("TU (TSU)");
                } break;

                case ("HU"): {
                    undercharacter.setText("HU (FU)");
                } break;

                case ("WO"): {
                    undercharacter.setText("WO (O)");
                } break;

                case ("ZI"): {
                    undercharacter.setText("ZI (JI)");
                } break;

                case ("DI"): {
                    undercharacter.setText("DI (JI)");
                } break;

                case ("DU"):
                {
                    undercharacter.setText("DU (DZU)");
                } break;

                default: {
                    undercharacter.setText(""+translated);
                } break;
            }
            Enlarge.show();
        }
    };
}
