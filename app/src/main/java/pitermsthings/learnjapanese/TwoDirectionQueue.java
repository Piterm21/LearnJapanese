package pitermsthings.learnjapanese;

/**
 * Created by Piterm on 04.07.2016.
 */
public class TwoDirectionQueue<Type>
{
    Element First;
    Element Last;
    int NumberOfElements=0;

    private class Element
    {
        Element()
        {
            PreDecessor=null;
            Next=null;
            Data=null;
        }
        Element PreDecessor;
        Element Next;
        Type Data;
    }

    public void addToList(Type DataToAdd)
    {
        if(NumberOfElements==0)
        {
            First=new Element();
            First.Next=null;
            First.PreDecessor=null;
            First.Data=DataToAdd;
            Last=First;
        }
        else
        {
            Element NewElement = new Element();
            NewElement.Next=First;
            NewElement.PreDecessor=null;
            NewElement.Data=DataToAdd;
            First.PreDecessor=NewElement;
            First=NewElement;
        }
        NumberOfElements++;
    }

    public void removeFirst()
    {
        if(NumberOfElements==1)
        {
            First = new Element();
            Last=First;
        }
        else
        {
            First = First.Next;
        }
    }

    public void removeLast()
    {
        if(NumberOfElements==1)
        {
            First = new Element();
            Last=First;
        }
        else
        {
            Last = Last.PreDecessor;
        }
    }

    public void removeIndex(int i)
    {
        if(i+1 <= NumberOfElements)
        {
            if(i==0)
            {
                removeFirst();
            }
            else
            if (i + 1 == NumberOfElements)
            {
                    removeLast();
            }
            else
            {
                Element ToReach = First;
                for(int j=0;j<i;j++)
                {
                    ToReach=ToReach.Next;
                }
                ToReach.PreDecessor.Next=ToReach.Next;
                ToReach.Next.PreDecessor=ToReach.PreDecessor;
            }
        }
    }

    public void removeElement(Type DataToRemove)
    {

        Element ToReach = First;
        if(ToReach.Data == DataToRemove)
            removeFirst();
        else
        if(Last.Data == DataToRemove)
            removeLast();
        else
        {
            while(ToReach!=null)
            {
                ToReach = ToReach.Next;
                if(ToReach.Data == DataToRemove)
                {
                    ToReach.PreDecessor.Next = ToReach.Next;
                    ToReach.Next.PreDecessor = ToReach.PreDecessor;
                    ToReach=null;
                    NumberOfElements--;
                }
            }
        }
    }

    public Type returnValueOfFirst()
    {
        return First.Data;
    }

    public Type returnValueOfLast()
    {
        return Last.Data;
    }

    public Type returnValueOfIndex(int i)
    {
        Type Result=null;
        if(i+1<=NumberOfElements)
        {
            if(i==0)
            {
                Result = returnValueOfFirst();
            }
            else
            {
                if(i+1==NumberOfElements)
                {
                    Result = returnValueOfLast();
                }
                else
                {
                    Element ToReach = First;
                    for(int j=0;j<i;j++)
                    {
                        ToReach= ToReach.Next;
                    }
                    Result = ToReach.Data;
                }
            }
        }
        return Result;
    }

    public int returnIndexOfElement(Type DataToCheck)
    {
        boolean Found=false;
        int Result=-1;
        Element ToCheck = First;
        while(ToCheck.Next!=null)
        {
            if(DataToCheck==ToCheck.Data)
            {
                Found=true;
                ToCheck=Last;
            }
            ToCheck = ToCheck.Next;
            Result++;
        }
        if(Found)
            return Result;
        else
            return -1;
    }

    public Type returnValueOfOneBefore(Type DataToCheck)
    {
        Type Result=null;
        Element ToCheck = First;
        if(DataToCheck==ToCheck.Data)
            return null;
        else
        {
            while (ToCheck.Next != null)
            {
                ToCheck = ToCheck.Next;
                if (DataToCheck == ToCheck.Data)
                {
                    Result = ToCheck.PreDecessor.Data;
                    ToCheck = Last;
                }
            }
        }
        return Result;
    }

    public Type returnSecond()
    {
        if(First!=null)
        {
            if (First.Next != null)
            {
                return First.Next.Data;
            } else
                return null;
        }
        else
            return null;
    }

    public Type returnValueOfNext(Type DataToCheck)
    {
        Type Result=null;
        Element ToCheck = First;
        if(DataToCheck==ToCheck.Data)
            if(First.Next!=null)
                Result = First.Next.Data;
            else
                Result = null;
        else
        {
            while (ToCheck.Next != null)
            {
                ToCheck = ToCheck.Next;
                if (DataToCheck == ToCheck.Data)
                {
                    if(ToCheck.Next!=null)
                        Result = ToCheck.Next.Data;
                    else
                        Result=null;
                    ToCheck = Last;
                }
            }
        }
        return Result;
    }

    public void clear()
    {
        First=null;
        Last=null;
    }

    public void clearExepctLast()
    {
        Last.PreDecessor = null;
        First = Last;
    }

    public int size()
    {
        return NumberOfElements;
    }
}
