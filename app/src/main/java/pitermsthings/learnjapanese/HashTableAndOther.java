package pitermsthings.learnjapanese;
/**
 * Created by Piotr Bachur on 17.06.2016.
 */
public class HashTableAndOther {

    public String[] TableCharacters;
    public char KaInHiragana;
    public char KaInKatakana;
    public int LowestValueChar;
    public String[] TableHiragana;
    public String[] TableKatakana;
    public String[] OtherLetters={"K","G","S","Z","T","D","N","H","B","P","M","Y","R","W","N"};

    public HashTableAndOther()
    {
        KaInHiragana ='か';
        KaInKatakana ='カ';
        LowestValueChar ='ぁ';
        SetUpTableRomaji();
        SetUpTableHiragana();
        SetUpKatakanaTable();
    }

    private void SetUpTableRomaji()
    {

        int SignZero ='ぁ';
        int LastSign ='ん';
        int SignZeroKatakana='ァ';
        int LastSignKatakana='ン';
        TableCharacters=new String[('ー'-LowestValueChar)+1];
        TableCharacters['ー'%LowestValueChar]="-";
        TableCharacters[SignZero%LowestValueChar]="a";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="A";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="i";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="I";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="u";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="U";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="e";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="E";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="o";
        SignZero++;
        TableCharacters[SignZero%LowestValueChar]="O";
        TableCharacters['っ'%LowestValueChar]="tu";
        TableCharacters['ゃ'%LowestValueChar]="ya";
        TableCharacters['ゅ'%LowestValueChar]="yu";
        TableCharacters['ょ'%LowestValueChar]="yo";
        TableCharacters['ゎ'%LowestValueChar]="wa";
        TableCharacters[LastSign%LowestValueChar]="N";
        TableCharacters[SignZeroKatakana%LowestValueChar]="a";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="A";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="i";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="I";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="u";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="U";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="e";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="E";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="o";
        SignZeroKatakana++;
        TableCharacters[SignZeroKatakana%LowestValueChar]="O";
        TableCharacters['ッ'%LowestValueChar]="tu";
        TableCharacters['ャ'%LowestValueChar]="ya";
        TableCharacters['ュ'%LowestValueChar]="yu";
        TableCharacters['ョ'%LowestValueChar]="yo";
        TableCharacters['ヮ'%LowestValueChar]="wa";
        TableCharacters[LastSignKatakana%'ぁ']="N";
        TableCharacters['ヴ'%LowestValueChar]="vu";
        TableCharacters['ヵ'%LowestValueChar]="ka";
        TableCharacters['ヶ'%LowestValueChar]="ke";
        TableCharacters['゛'%LowestValueChar]="\"";
        TableCharacters['゜'%LowestValueChar]="\'";
        String[] OtherLetters = {"K","G","S","Z","T","D","N","H","B","P","M","Y","R","W"};
        String[] Vowels = {"A","I","U","E","O"};
        SignZero++;
        SignZeroKatakana++;
        int Letter=0;
        int Vowel=0;
        int SwitchMulti=0;
        for(int i=SignZero%LowestValueChar;i<(LastSign%LowestValueChar);i++)
        {
            if(Letter==4 && Vowel==2)
            {
                i++;
            }
            if(Vowel>=5)
            {
                Letter++;
                if(Letter==1||Letter==3||Letter==5)
                {
                    Letter++;
                }
                if(Letter==8)
                {
                    Letter+=2;
                }
                Vowel=0;
            }
            if(Letter==11)
            {
                i++;
            }
            if(Letter==13 && Vowel==0)
            {
                i++;
            }
            TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
            if(Letter==0||Letter==2||Letter==4)
            {
                SwitchMulti++;
                i++;
                TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
                SwitchMulti=0;
            }
            if(Letter==7)
            {
                SwitchMulti++;
                i++;
                TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
                SwitchMulti++;
                i++;
                TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
                SwitchMulti=0;
            }
            if(Letter==11)
            {
                Vowel++;
            }
            if(Letter==13 && Vowel==1)
            {
                Vowel++;
            }
            Vowel++;
        }
        Letter=0;
        Vowel=0;
        SwitchMulti=0;
        for(int i=SignZeroKatakana%LowestValueChar;i<(LastSignKatakana%LowestValueChar);i++)
        {
            if(Letter==4 && Vowel==2)
            {
                i++;
            }
            if(Vowel>=5)
            {
                Letter++;
                if(Letter==1||Letter==3||Letter==5)
                {
                    Letter++;
                }
                if(Letter==8)
                {
                    Letter+=2;
                }
                Vowel=0;
            }
            if(Letter==11)
            {
                i++;
            }
            if(Letter==13 && Vowel==0)
            {
                i++;
            }
            TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
            if(Letter==0||Letter==2||Letter==4)
            {
                SwitchMulti++;
                i++;
                TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
                SwitchMulti=0;
            }
            if(Letter==7)
            {
                SwitchMulti++;
                i++;
                TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
                SwitchMulti++;
                i++;
                TableCharacters[i]=OtherLetters[Letter+SwitchMulti]+Vowels[Vowel];
                SwitchMulti=0;
            }
            if(Letter==11)
            {
                Vowel++;
            }
            if(Letter==13 && Vowel==1)
            {
                Vowel++;
            }
            Vowel++;
        }
    }

    private void SetUpTableHiragana()
    {
        TableHiragana=new String[('ん'% KaInHiragana)+2];
        TableHiragana[0]="あ";
        TableHiragana[1]="い";
        TableHiragana[2]="う";
        TableHiragana[3]="え";
        TableHiragana[4]="お";
        int CurrentLetter=-1;
        char tempCharacter=KaInHiragana;
        for(int i = 5; i<='ん'% KaInHiragana; i++)
        {
            if(i%5==0)
            {
                CurrentLetter++;
            }
            if(CurrentLetter==1||CurrentLetter==3||CurrentLetter==5)
            {
                i+=5;
                CurrentLetter++;
            }
            if(CurrentLetter==8)
            {
                i+=10;
                CurrentLetter+=2;
            }
            if(tempCharacter=='っ' || tempCharacter=='ゃ' || tempCharacter=='ゅ' || tempCharacter=='ょ' || tempCharacter=='ゎ')
            {
                tempCharacter++;
            }
            TableHiragana[i]=""+tempCharacter;
            if(CurrentLetter==0||CurrentLetter==2||CurrentLetter==4)
            {
                TableHiragana[i+5]=""+(char)(tempCharacter+1);
                tempCharacter++;
            }
            if(CurrentLetter==7)
            {
                TableHiragana[i+5]=""+(char)(tempCharacter+1);
                TableHiragana[i+10]=""+(char)(tempCharacter+2);
                tempCharacter+=2;
            }
            tempCharacter++;
        }
    }

    private void SetUpKatakanaTable()
    {
        TableKatakana=new String[('ン'% KaInKatakana)+2];
        TableKatakana[0]="ア";
        TableKatakana[1]="イ";
        TableKatakana[2]="ウ";
        TableKatakana[3]="エ";
        TableKatakana[4]="オ";
        int CurrentLetter=-1;
        char tempCharacter=KaInKatakana;
        for(int i = 5; i<='ン'% KaInKatakana; i++)
        {
            if(i%5==0)
            {
                CurrentLetter++;
            }
            if(CurrentLetter==1||CurrentLetter==3||CurrentLetter==5)
            {
                i+=5;
                CurrentLetter++;
            }
            if(CurrentLetter==8)
            {
                i+=10;
                CurrentLetter+=2;
            }
            if(tempCharacter=='ッ' || tempCharacter=='ャ' || tempCharacter=='ュ' || tempCharacter=='ョ' || tempCharacter=='ヮ')
            {
                tempCharacter++;
            }
            TableKatakana[i]=""+tempCharacter;
            if(CurrentLetter==0||CurrentLetter==2||CurrentLetter==4)
            {
                TableKatakana[i+5]=""+(char)(tempCharacter+1);
                tempCharacter++;
            }
            if(CurrentLetter==7)
            {
                TableKatakana[i+5]=""+(char)(tempCharacter+1);
                TableKatakana[i+10]=""+(char)(tempCharacter+2);
                tempCharacter+=2;
            }
            tempCharacter++;
        }
    }
}
