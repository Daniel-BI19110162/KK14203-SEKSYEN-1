import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//required for border
import javax.swing.BorderFactory;
import javax.swing.border.Border;
//required for file IO
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
//required for exception
import java.io.IOException;

//Header panel
class HeaderPanel extends JPanel{
   private JLabel header;
   public HeaderPanel(){
      header = new JLabel("Course Evaluation Form");
      add(header);
   }
}


//Form Panel
class FormPanel extends JPanel implements ActionListener,ItemListener{
   //list all UI components for the panel
   JLabel lbl_name;
   JTextField name;
   JLabel lbl_matric;
   JTextField matric;
   JLabel lbl_code;
   JComboBox<String> code;
   JLabel lbl_rating;
   JLabel lbl_outcome;
   JButton b_submit;
   JButton b_clear;
   JCheckBox c1;
   JCheckBox c2;
   JLabel lbl_output; 
   JScrollPane jsp;
   Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
   
   //global variable  
   String output="";
   String code_selection="";
   String rb_selection="";
   String cb_selection="";
   String filePath2 ="";//test IOexception handling
   String filePath="dataList.txt"; //in the same directory
      
   public FormPanel(){   
      setLayout(new FlowLayout(FlowLayout.LEFT));     
      
      lbl_name = new JLabel("Name");
      lbl_name.setPreferredSize(new Dimension(150, 20));
      lbl_name.setBorder(border);
      
      add(lbl_name);
      name = new JTextField(20);
      add(name);
      
      lbl_matric = new JLabel("Matric No.");
      lbl_matric.setPreferredSize(new Dimension(150, 20));
      add(lbl_matric);
      matric = new JTextField(15);
      add(matric);   
      
      String[] courses={"[Select]", "KP14203 Electronic Design", "KT20363 Fundamental C#"};
      
      lbl_code = new JLabel("Course Code");
      lbl_code.setPreferredSize(new Dimension(150, 20));
      add(lbl_code);
      code = new JComboBox<String>(courses);
      add(code); 
      
      //JComboBox action listener
      code.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            //get selected item
               code_selection = (String) code.getSelectedItem();
            }
         });  
      
      lbl_rating = new JLabel("Rating");
      lbl_rating.setPreferredSize(new Dimension(150, 20));
      add(lbl_rating);
      
      //Radio buttons and action listener
      JRadioButton rb1 = new JRadioButton("1");
      rb1.addActionListener(this);
      JRadioButton rb2 = new JRadioButton("2");
      rb2.addActionListener(this);
      JRadioButton rb3 = new JRadioButton("3");
      rb3.addActionListener(this);
      JRadioButton rb4 = new JRadioButton("4");
      rb4.addActionListener(this);
      JRadioButton rb5 = new JRadioButton("5");
      rb5.addActionListener(this);
      add(rb1);
      add(rb2);
      add(rb3);
      add(rb4);
      add(rb5);
      
      //define button group
      ButtonGroup bg = new ButtonGroup();
      bg.add(rb1);
      bg.add(rb2);
      bg.add(rb3);
      bg.add(rb4);
      bg.add(rb5);
      
      lbl_outcome = new JLabel("Outcome");
      lbl_outcome.setPreferredSize(new Dimension(150, 20));
      add(lbl_outcome);
      
      //checkbox and  item listener
      c1 = new JCheckBox("Basic knowledge");
      c1.addItemListener(this);
      c2 = new JCheckBox("Advanced knowledge");
      c2.addItemListener(this);
      add(c1);
      add(c2);
      
      b_submit = new JButton("Submit");
      add(b_submit);
      b_clear = new JButton("Clear");
      add(b_clear);
      
      //handle button submit action listener
      //input validation to handle empty field
      //view the input to output label
      //and write to file
      b_submit.addActionListener(
         new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
            //call method  
             
               if(name.getText().equals("")){JOptionPane.showMessageDialog(null,"Name field cannot be empty","Alert",JOptionPane.WARNING_MESSAGE); }
               
               else if(matric.getText().equals("")){JOptionPane.showMessageDialog(null,"Matric field cannot be empty","Alert",JOptionPane.WARNING_MESSAGE);}
               
               else if(isNullOrEmpty(code_selection) || code_selection.equals("[Select]")){JOptionPane.showMessageDialog(null,"Course Code is not selected","Alert",JOptionPane.WARNING_MESSAGE);}
               
               else if(isNullOrEmpty(rb_selection)){JOptionPane.showMessageDialog(null,"Rating is not selected","Alert",JOptionPane.WARNING_MESSAGE);}
               
               else if(isNullOrEmpty(cb_selection)){JOptionPane.showMessageDialog(null,"Course Outcome is not selected","Alert",JOptionPane.WARNING_MESSAGE);}
               
               else{
                  if(printOutput()){
                     writeInput();
                     JOptionPane.showMessageDialog(null,"Data is saved successfully.");//Show message if data is saved to text
                  }
                  else{
                     JOptionPane.showMessageDialog(null,"Data saved unsuccessful","Alert",JOptionPane.WARNING_MESSAGE);}//Show message if data is not save in   
               }     
            }                
         });
      
      //handle button clear action listener
      b_clear.addActionListener(
         new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
               lbl_output.setText("Output");  
               name.setText("");
               matric.setText(""); 
               code.setSelectedIndex(0);
               bg.clearSelection();
               c1.setSelected(false);
               c2.setSelected(false);
            }  
         });
      
      lbl_output = new JLabel("Output");
      lbl_output.setBorder(border);
      lbl_output.setVerticalAlignment(JLabel.TOP);
      
      //add output label to scrollpane
      jsp = new JScrollPane(lbl_output);
      jsp.setPreferredSize(new Dimension(410,120));
      add(jsp);     
   }
   
   //handle radio button selection
   public void actionPerformed(ActionEvent ae) {
      rb_selection = ae.getActionCommand();    	   
   }
   
   //handle item listener for checkbox
   public void itemStateChanged(ItemEvent ie) {
      JCheckBox check = (JCheckBox)ie.getSource();
      cb_selection += check.getText() + " ";   
   }
   
   //method to print output to lbl_output
   public boolean printOutput(){
      output = "<html>";
      output += "Thank you for your evaluation<br><br>"; 
      output += "Name: " + name.getText() + "<br>";
      output += "Matric: " + matric.getText() + "<br>";
      output += "Course: " + code_selection + "<br>";
      output += "Rating: " + rb_selection + "<br>";
      output += "Outcome: " + cb_selection + "<br>";
      output += "</html>";          
      lbl_output.setText(output);
      jsp.getViewport().revalidate();
      return true;
   }
    
    //write to file
   public void writeInput(){
      File file = new File(filePath);// filePath2 to test IOExeption handling
      FileWriter fr = null;
      BufferedWriter br = null;
      PrintWriter pr = null;
      
      String input = name.getText() + ", " + matric.getText() + ", " + code_selection + ", " + rb_selection + ", " + cb_selection;
      
      //exception implementation
      try {
      	// to append to file, you need to initialize FileWriter using below constructor
         fr = new FileWriter(file, true);
         br = new BufferedWriter(fr);
         pr = new PrintWriter(br);
         pr.println(input);
      } catch (IOException e) {			
         JOptionPane.showMessageDialog(null,"java.io.FileNotFoundException", "Error",JOptionPane.ERROR_MESSAGE);
      } finally {
         try {
            pr.close();
            br.close();
            fr.close();
         } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"java.io.FileNotFoundException", "Error",JOptionPane.ERROR_MESSAGE);
         }
      }
   }
   
   //check if string value empty or not
   public boolean isNullOrEmpty(String str) {
      if(str != null && !str.isEmpty())
         return false;
      return true;
   }

}
   
class MenuActionListener implements ActionListener {
   FormPanel fp;
   //receive FormPanel class to this constructor
   public MenuActionListener(FormPanel p){
      fp = p;
   }
   
   public void actionPerformed(ActionEvent e) {
      BufferedReader reader;
      try {
         reader = new BufferedReader(new FileReader(fp.filePath));//filepath2 is used to test the IOException handling
         String line = reader.readLine();
         String output="<html>";
         while (line != null) {
            output += line + "<br>";
         	// read next line
            line = reader.readLine();
         }
         output += "<br>";
         fp.lbl_output.setText(output);
         reader.close();
      } catch (IOException io) {
         JOptionPane.showMessageDialog(null,"java.io.FileNotFoundException", "Error",JOptionPane.ERROR_MESSAGE);
      }
   
   }
}


//run the application using this main
public class CourseEvaluationApp {  
   public static void main(String[] 	args) {  
      JFrame f = new JFrame("Evaluation");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      //load panels
      HeaderPanel h = new 	HeaderPanel();
      //HeaderPanel h2 = new 	HeaderPanel();
      FormPanel fp = new FormPanel();
      
      JMenuBar mb = new JMenuBar(); 
      // create a menu 
      JMenu mb_name = new JMenu("Menu"); 
      
      // create menuitems 
      JMenuItem mload = new JMenuItem("Load Data"); 
      // attach listener and send FormPanel class
      mload.addActionListener(new MenuActionListener(fp));
      
      JMenuItem mexit = new JMenuItem("Exit");  
      mexit.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               int a=JOptionPane.showConfirmDialog(f,"Are you sure you want to exit?");  
               if(a==JOptionPane.YES_OPTION){  
                  System.exit(0);}}});
      // add menu items to menu 
      mb_name.add(mload); 
      mb_name.add(mexit);
     
      // add menu to menu bar 
      mb.add(mb_name); 
     
      // add menubar to frame 
      f.setJMenuBar(mb);  
               
      //add panels to frame       
      f.add(h,BorderLayout.NORTH);
      f.add(fp, BorderLayout.CENTER);
      f.setSize(460,400);
      f.setVisible(true);
   }  
}


