import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
//required for exception
import java.io.IOException;

import java.util.ArrayList;

abstract class Cake{
   String name;
   String[] topping;
   String[] toppingOrder;
   double priceSmall;
   double priceMedium;
   double priceBig;
   double totalPrice;
   int size;
   int quantity;
   
   Cake(String name){this.name=name;}
   
   abstract void setCake(String[] t, double ps, double pm, double pb);
     
   
   void cakeOrder(String[] toppingOrder, int quantity, int size){
      this.toppingOrder = toppingOrder;
      this.quantity = quantity;
      this.size = size;
   }
   
   public double getSizePrice(){
      double sprice=0.0;
      if(size==1) sprice = priceSmall;
      else if(size==2) sprice = priceMedium;
      else if(size==3) sprice = priceBig;
      
      return sprice;
   }
   
   public double getTotalPrice(){
      totalPrice = getSizePrice();
      totalPrice *= quantity;
      totalPrice += (toppingOrder.length*10);
      return totalPrice;  
   }
   
   public int getQuantity(){
      return quantity;  
   }
   
   public String getSize(){
      String size_str="";
      if(size==1) size_str="Small";
      else if (size==2) size_str="Medium";
      else if (size==3) size_str="Large";
      return size_str;
   }
   
   //return print order to GUI call
   public String printOrder_GUI(){
      String txt = "\n\nCake Order detail:\n";
      txt += "-------------------------------------------\n";
      txt += "Topping Order: \n";
      for(int i=0; i<toppingOrder.length; i++){
         txt += "(" + (i+1) + ") " + toppingOrder[i] + " \n";
      } 
      txt += "\nSize        : " + getSize() + "\n";
      txt += "Quantity : " + getQuantity() + "\n";
      txt += "-------------------------------------------\n";
      txt += "Total Price: RM" + getTotalPrice() + "\n"; 
      txt += "-------------------------------------------\n"; 
      return txt;
   }      
}

class BlackForest extends Cake{
 
   BlackForest(String name){
      super(name);
   }
   
   void setCake(String[] topping, double priceSmall, double priceMedium, double priceBig){
      this.topping = topping;
      this.priceSmall = priceSmall;
      this.priceMedium = priceMedium;
      this.priceBig = priceBig;
   }
}



class FormPanel extends JPanel implements ActionListener{
   JButton btnOrder;
   JButton btnAdd;
   JTextArea jt1;
   JTextArea jt2;
   JComboBox jcb;
   JLabel jcmenu;
   JLabel jtoppings;
   static BlackForest cake;
   static ArrayList<String> order;
   
   
   String filePath = "BlackForest.txt";

   public FormPanel() {
      //construct preComponents
      String[] jcbItems = {"[Select]"};
   
      //construct components
      btnOrder = new JButton ("Order");
      btnAdd = new JButton ("Add Topping");
      jt1 = new JTextArea (5, 5);
      jt2 = new JTextArea (5, 5);
      JScrollPane jt1_sp = new JScrollPane(jt1);
      JScrollPane jt2_sp = new JScrollPane(jt2);
      jcb = new JComboBox (jcbItems);
      jtoppings = new JLabel ("Topping Selection");
      jcmenu = new JLabel ("Cake Menu");
      
      btnAdd.addActionListener(this);
      btnOrder.addActionListener(this);
   
      //adjust size and set layout
      setPreferredSize (new Dimension (472, 559));
      setLayout (null);
   
      //add components
      add (btnAdd);
      add (btnOrder);
      add (jt1_sp);
      add (jt2_sp);
      add (jcb);
      add (jtoppings);
      add (jcmenu);
   
      //set component bounds (only needed by Absolute Positioning)
      btnAdd.setBounds (325, 210, 125, 30);
      btnOrder.setBounds(325, 250, 125, 30);
      jt2_sp.setBounds (100, 25, 265, 175);
      jt1_sp.setBounds (25, 285, 425, 255);
      jcb.setBounds (180, 210, 125, 30);
      jtoppings.setBounds (45, 210, 125, 30);
      jcmenu.setBounds (195, 0, 100, 25);
   }
   
   
   public void actionPerformed(ActionEvent ae){
      String command = ae.getActionCommand(); 
      
      if(command.equals("Add Topping")){
         order.add(jcb.getSelectedItem().toString());      
         jt1.append(jcb.getSelectedItem().toString() + "\n");   
      }
      else if(command.equals("Order")){
         //convert Arraylist to String array
         // declaration and initialise String Array 
         String str_order[] = new String[order.size()]; 
      
         // ArrayList to Array Conversion 
         for (int j = 0; j < order.size(); j++) { 
            // Assign each value to String array 
            str_order[j] = order.get(j); 
         }
        
         //Sumbit order, quantity and size
         //to do: create GUI for quantity and size selection
         //will save order in BlackForest.text   
         cake.cakeOrder(str_order, 1, 2);
         String txt = cake.printOrder_GUI();
         jt1.append(txt);
         writeInput();
      }    
   }
   
   public void showMenu(){
   
      String[] topping = {"Chocolate", "Cherries", "Whipped Cream"};
      String text = cake.name + " cake with available toppings:\n";
      for(int i=0; i<topping.length; i++){
         text += "\n" + (i+1) + ")  " + topping[i] + "\n";
      } 
      text += "\nPrice:\n";
      text += "[1] Small : RM45.0 \n";
      text += "[2] Medium: RM65.0 \n";
      text += "[3] Big   : RM80.0 \n";
      jt2.append(text);
   }

       
   public void updateCB(String[] topping){
      for(int i=0; i<topping.length; i++){
         jcb.addItem(topping[i]);
      }      
   }
   
   //write to file
   public void writeInput(){
      File file = new File(filePath);
      FileWriter fr = null;
      BufferedWriter br = null;
      PrintWriter pr = null;
      
      String input = "Topping: ";
      String str_order[] = new String[order.size()];
      for(int i=0; i < order.size(); i++){
         str_order[i] = order.get(i);
         input += str_order[i] + ", ";
      }
      input += "Size: " + cake.getSize();
      input += ", Quantity: " + (String.valueOf(cake.quantity));
      input += ", Total Price: RM" + (String.valueOf(cake.getTotalPrice()));
      
      
      //exception implementation
      try {
      	// to append to file, you need to initialize FileWriter using below constructor
         fr = new FileWriter(file, true);
         br = new BufferedWriter(fr);
         pr = new PrintWriter(br);
         pr.println(input);
      } catch (IOException e) {			
         jt1.setText(e.toString());
      } finally {
         try {
            pr.close();
            br.close();
            fr.close();
         } catch (IOException e) {
            jt1.setText(e.toString());
         }
      }
   }

}



public class CakeApp_Lab6_3_2{
   public static void main (String[] args) {
      FormPanel bcake = new FormPanel();
      bcake.cake = new BlackForest("Black Forest");
      bcake.order = new ArrayList<String>();
      
      
      JFrame frame = new JFrame ("My Cake Menu");
      String[] topping = {"Chocolate", "Cherries", "Whipped Cream"};
      bcake.cake.setCake(topping, 45.00, 65.00, 80.00); 
      bcake.showMenu();
      
      bcake.updateCB(topping);
      
      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add (bcake);
      frame.pack();
      frame.setVisible (true);
   }
}

