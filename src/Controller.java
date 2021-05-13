import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller 
{
    @FXML
    Label mainScreen;

    @FXML
    Label subScreen;
    
    @FXML
    private void keyPress(KeyEvent event)
    {
        KeyCode input = event.getCode();
        
        if(input == KeyCode.NUMPAD1 || input == KeyCode.DIGIT1) mainScreen.setText(mainScreen.getText()+"1");
        else if(input == KeyCode.NUMPAD2 || input == KeyCode.DIGIT2) mainScreen.setText(mainScreen.getText()+"2");
        else if(input == KeyCode.NUMPAD3 || input == KeyCode.DIGIT3) mainScreen.setText(mainScreen.getText()+"3");
        else if(input == KeyCode.NUMPAD4 || input == KeyCode.DIGIT4) mainScreen.setText(mainScreen.getText()+"4");
        else if(input == KeyCode.NUMPAD5 || input == KeyCode.DIGIT5) mainScreen.setText(mainScreen.getText()+"5");
        else if(input == KeyCode.NUMPAD6 || input == KeyCode.DIGIT6) mainScreen.setText(mainScreen.getText()+"6");
        else if(input == KeyCode.NUMPAD7 || input == KeyCode.DIGIT7) mainScreen.setText(mainScreen.getText()+"7");
        else if(input == KeyCode.NUMPAD8 || input == KeyCode.DIGIT8) mainScreen.setText(mainScreen.getText()+"8");
        else if(input == KeyCode.NUMPAD9 || input == KeyCode.DIGIT9) mainScreen.setText(mainScreen.getText()+"9");
        else if(input == KeyCode.NUMPAD0 || input == KeyCode.DIGIT0) mainScreen.setText(mainScreen.getText()+"0");
        else if(input == KeyCode.DECIMAL) mainScreen.setText(mainScreen.getText()+".");

        else if(input == KeyCode.ADD) mainScreen.setText(mainScreen.getText()+"+");
        else if(input == KeyCode.SUBTRACT) mainScreen.setText(mainScreen.getText()+"-");
        else if(input == KeyCode.DIVIDE) mainScreen.setText(mainScreen.getText()+"/");
        else if(input == KeyCode.MULTIPLY) mainScreen.setText(mainScreen.getText()+"*");

        else if(input == KeyCode.A) 
        {
            mainScreen.setText("");
            subScreen.setText("");
        }
        else if(input == KeyCode.C)
        {
            mainScreen.setText(CEfunction(mainScreen.getText()));
        }
        else if(input == KeyCode.BACK_SPACE)
        {
            if(mainScreen.getText().length() > 0)
                mainScreen.setText(mainScreen.getText().substring(0,mainScreen.getText().length()-1));
        }
    }
    


    public void allClear(ActionEvent e) throws Exception
    {
        mainScreen.setText("");
        subScreen.setText("");
    }

    public void clearNum(ActionEvent e) throws Exception
    {
        mainScreen.setText(CEfunction(mainScreen.getText()));
    }

    public void backspace(ActionEvent e) throws Exception
    {
        if(mainScreen.getText().length() > 0)
            mainScreen.setText(mainScreen.getText().substring(0,mainScreen.getText().length()-1));
    }

    public void equals(ActionEvent event)
    {
        subScreen.setText(mainScreen.getText());
        try 
        {
            Double ans = calculate();

            BigDecimal bd = BigDecimal.valueOf(ans);
            bd = bd.setScale(1, RoundingMode.HALF_UP);

            mainScreen.setText(Double.toString(bd.doubleValue()));
        } 
        catch (Exception e) 
        {
            mainScreen.setText("ERROR");
        }
    }

    public void num1(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"1"); }
    public void num2(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"2"); }
    public void num3(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"3"); }
    public void num4(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"4"); }
    public void num5(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"5"); }
    public void num6(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"6"); }
    public void num7(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"7"); }
    public void num8(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"8"); }
    public void num9(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"9"); }
    public void num0(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"0"); }
    public void dot(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"."); }
    public void plus(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"+"); }
    public void minus(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"-"); }
    public void multi(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"*"); }
    public void div(ActionEvent e) throws Exception { mainScreen.setText(mainScreen.getText()+"/"); }


    public String CEfunction(String text)
    {
        int maxIndex = text.lastIndexOf("+");

        if(text.lastIndexOf("-") > maxIndex) maxIndex=text.lastIndexOf("-");
        if(text.lastIndexOf("*") > maxIndex) maxIndex=text.lastIndexOf("*");
        if(text.lastIndexOf("/") > maxIndex) maxIndex=text.lastIndexOf("/");

        if(maxIndex == -1) return "";

        return text.substring(0,maxIndex+1);
    }

    public double calculate() throws Exception
    {
        String equation = mainScreen.getText();

        String[] numbers = equation.split("\\+|-|\\*|/");
        String[] operators = equation.split("[0-9]|\\.|\\s");

        List<String> list = new ArrayList<String>();

        list.add(" ");
        for(int i=0 ; i<operators.length ; i++)
            if(operators[i]!=null && operators[i].length()>0) list.add(operators[i]);

        operators = list.toArray(new String[list.size()]);

        Stack<Double> num = new Stack<>();
        Stack<String> op = new Stack<String>();

        Double result = 0.0;

        for(int i=0 ; i<operators.length || i<numbers.length; i++)
        {
            if(i == 0)
            {
                num.push(Double.parseDouble(numbers[0]));
                continue;
            }

            if(operators[i].equals("+") || operators[i].equals("-"))
            {
                if(i < numbers.length) num.push(Double.parseDouble(numbers[i]));
                if(i < operators.length) op.push(operators[i]);
            }

            if(operators[i].equals("*") || operators[i].equals("/"))
            {
                double tmp = num.pop();

                if(operators[i].equals("*")) num.push(preciseMulti(tmp,Double.parseDouble(numbers[i])));
                if(operators[i].equals("/")) num.push(preciseDiv(tmp, Double.parseDouble(numbers[i])));
            }
        }

        result = num.elementAt(0);
        for(int i=0 ; i<op.size() ; i++)
        {
            if(op.elementAt(i).equals("+")) result = preciseAdd(result, num.get(i+1));
            if(op.elementAt(i).equals("-")) result = preciseSub(result, num.get(i+1));
        }

        return result;
    }

    private double preciseAdd(double a, double b)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(a)); 
        BigDecimal b2 = new BigDecimal(Double.toString(b)); 
        return b1.add(b2).doubleValue(); 
    }
    private double preciseSub(double a, double b)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(a)); 
        BigDecimal b2 = new BigDecimal(Double.toString(b)); 
        return b1.subtract(b2).doubleValue(); 
    }
    private double preciseMulti(double a, double b)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(a)); 
        BigDecimal b2 = new BigDecimal(Double.toString(b)); 
        return b1.multiply(b2).doubleValue(); 
    }
    private double preciseDiv(double a, double b)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(a)); 
        BigDecimal b2 = new BigDecimal(Double.toString(b)); 
        return b1.divide(b2, 7, RoundingMode.HALF_UP).doubleValue(); 
    }
    
}
