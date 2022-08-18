import java.awt.*; 
import java.util.*; 
import java.awt.event.*;  
import java.awt.Component;

public class CalculatorApp extends Frame  
{  

    public boolean setClear=true;  
    double number, memoryValue;  
    char op;  

    String digbuttontxt[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "." };  
    String operatorButtonText[] = {"÷","√x", "X", "^", "-", "+", "=","%"};  

    String specialbuttonText[] = {"A/C","DEL" };  

    Mydigbutton digbutton[]=new Mydigbutton[digbuttontxt.length];  
    MyOperatorButton operatorbutton[]=new MyOperatorButton[operatorButtonText.length];  

    MySpecialButton specialbutton[]=new MySpecialButton[specialbuttonText.length];  

    Label displayLabel=new Label("START CALCULATING!",Label.RIGHT);  
    Label memoryLabel=new Label(" ",Label.RIGHT);  

    final int framewidth=675,frameheight=675;  
    final int height=100, width=75, h_space=10,v_space=10;  
    final int TOPX=30, TOPY=50;  

    CalculatorApp(String frameText) 
    {  

        int tempX=TOPX, y=TOPY;  
        displayLabel.setBounds(100,y,350,height);//Boundary of the label
        displayLabel.setBackground(Color.BLACK);  
        displayLabel.setForeground(Color.WHITE); 
        displayLabel.setFont(new Font("ARIAL", Font.BOLD, 25));
        add(displayLabel);  

        memoryLabel.setBounds(TOPX,  TOPY+height+ v_space,width, height);  

        tempX=TOPX+1*(width+h_space); y=TOPY+1*(height+v_space);  
        for(int i=0;i<specialbutton.length;i++)  
        {  
            specialbutton[i]=new MySpecialButton(tempX,y,width*2,height,specialbuttonText[i], this);  
            specialbutton[i].setForeground(Color.BLACK);
            specialbutton[i].setBackground(Color.RED);
            specialbutton[i].setFont(new Font("ARIAL",Font.BOLD,25));
            tempX=tempX+2*width+h_space;  
        }  

        int digitX=TOPX+width+h_space;  
        int digitY=TOPY+2*(height+v_space);  
        tempX=digitX;  y=digitY;  
        for(int i=0;i<digbutton.length;i++)  
        {  
            digbutton[i]=new Mydigbutton(tempX,y,width,height,digbuttontxt[i], this);//Boundary of the digit box  
            digbutton[i].setForeground(Color.BLACK); 
            digbutton[i].setBackground(Color.BLUE);
            digbutton[i].setFont(new Font("ARIAL",Font.BOLD,25));
            tempX+=width+h_space;  
            if((i+1)%3==0)
            {tempX=digitX; y+=height+v_space;}  
        }  

        int opsX=digitX+2*(width+h_space)+h_space;  
        int opsY=digitY;  
        tempX=opsX;  y=opsY;  
        for(int i=0;i<operatorbutton.length;i++)  
        {  
            tempX+=width+h_space;  
            operatorbutton[i]=new MyOperatorButton(tempX,y,width,height,operatorButtonText[i], this);  
            operatorbutton[i].setForeground(Color.RED);  
            operatorbutton[i].setBackground(Color.BLACK);
            operatorbutton[i].setFont(new Font("ARIAL",Font.BOLD,25));
            if((i+1)%2==0)
            {tempX=opsX; y+=height+v_space;}  
        }  

        addWindowListener(new WindowAdapter()  
            {  
                public void windowClosing(WindowEvent ev)  
                {System.exit(0);}  
            });  

        setLayout(null);  
        setSize(framewidth,frameheight);  
        setVisible(true);  
    }  

    static String getformattext(double temp)  
    {  
        String resText=""+temp;  
        if(resText.lastIndexOf(".0")>0)  
            resText=resText.substring(0,resText.length()-2);  
        return resText;  
    }  

    public static void main()  
    {  

        new CalculatorApp("Pocket Calculator");  
    }  
}  

class Mydigbutton extends Button implements ActionListener  
{  
    CalculatorApp cl;  

    Mydigbutton(int x,int y, int width,int height,String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x,y,width,height);  
        this.cl=clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    static boolean isInString(String s, char ch)  
    {  
        for(int i=0; i<s.length();i++)
            if(s.charAt(i)==ch)
                return true;  
        return false;  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String tempText=((Mydigbutton)ev.getSource()).getLabel();  

        if(tempText.equals("."))  
        {  
            if(cl.setClear)   
            {cl.displayLabel.setText("0.");cl.setClear=false;}  
            else if(!isInString(cl.displayLabel.getText(),'.'))  
                cl.displayLabel.setText(cl.displayLabel.getText()+".");  
            return;  
        }  

        int index=0;  
        try{  
            index=Integer.parseInt(tempText);  
        }catch(NumberFormatException e){return;}  

        if (index==0 && cl.displayLabel.getText().equals("0")) return;  

        if(cl.setClear)  
        {cl.displayLabel.setText(""+index);cl.setClear=false;}  
        else  
            cl.displayLabel.setText(cl.displayLabel.getText()+index);  
    }
}

class MyOperatorButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  

    MyOperatorButton(int x,int y, int width,int height,String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x,y,width,height);  
        this.cl=clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String opText=((MyOperatorButton)ev.getSource()).getLabel();  

        cl.setClear=true;  
        double temp=Double.parseDouble(cl.displayLabel.getText());  

        if(opText.equals("√x"))  
        {  
            try  
            {   double tempd=Math.sqrt(temp);  
                cl.displayLabel.setText(CalculatorApp.getformattext(tempd));}  
            catch(ArithmeticException excp)  
            {cl.displayLabel.setText("Divide by 0.");}  
            return;  
        } 

        if(!opText.equals("="))  
        {  
            cl.number=temp;  
            cl.op=opText.charAt(0);  
            return;  
        } 
        
        switch(cl.op)  
        {  
            case '+':  
                temp+=cl.number;break;  
            case '-':  
                temp=cl.number-temp;break;  
            case 'X':  
                temp*=cl.number;break;  
            case '^':  
                try{
                    temp=Math.pow(cl.number,temp);}  
                catch(ArithmeticException excp)  
                {cl.displayLabel.setText("Divide by 0."); return;}  
                break;  
            case '÷':  
                try{temp=cl.number/temp;}  
                catch(ArithmeticException excp)  
                {cl.displayLabel.setText("Divide by 0."); return;}  
                break; 
            case '%':
                try{
                    temp=(cl.number/100)*temp;}  
                catch(ArithmeticException excp)  
                {cl.displayLabel.setText("Divide by 0."); return;}  
                break; 


        }  

        cl.displayLabel.setText(CalculatorApp.getformattext(temp));  

    }
}  

  
class MySpecialButton extends Button implements ActionListener  
{  
    CalculatorApp cl;  

    MySpecialButton(int x,int y, int width,int height,String cap, CalculatorApp clc)  
    {  
        super(cap);  
        setBounds(x,y,width,height);  
        this.cl=clc;  
        this.cl.add(this);  
        addActionListener(this);  
    }  

    static String backSpace(String s)  
    {  
        String Res="";  
        for(int i=0; i<s.length()-1; i++) 
            Res+=s.charAt(i);  
        return Res;  
    }  

    public void actionPerformed(ActionEvent ev)  
    {  
        String operatortext=((MySpecialButton)ev.getSource()).getLabel();  
        //backspace
        if(operatortext.equals("DEL"))  
        {  
            String tempText=backSpace(cl.displayLabel.getText());  
            if(tempText.equals(""))   
                cl.displayLabel.setText("0");  
            else   
                cl.displayLabel.setText(tempText);  
            return;  
        }  
        //RESET
        if(operatortext.equals("A/C"))   
        {  
            cl.number=0.0; cl.op=' '; cl.memoryValue=0.0;  
            cl.memoryLabel.setText(" ");  
        }  

        cl.displayLabel.setText("0");cl.setClear=true;  
    } 
}