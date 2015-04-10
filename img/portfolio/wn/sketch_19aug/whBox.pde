/*
  Class to create WareHouse

*/
import processing.core.PApplet;
import processing.core.PVector;
import shapes3d.utils.*;
import shapes3d.animation.*;
import shapes3d.*;

import processing.opengl.*;

//Table t;



class whBox{
  
  protected float w, h, d;
  int catNum;
  
  PImage tex1, tex2, tex3, tex4,  tex5,  tex6;
  
  private PApplet pa;
  
  PVector loc = new PVector(0,0,0);
  
 
  
  int boxCount = 1;
  
  PrintWriter output;
   

Table  t = new Table();
Table tab;


  Table readTable;
  
  int[] id = new int[150];
  String[] cat = new String[150];
  
  
  void whBox(){
   
    

  }
  
  public PVector getBoxVal(int row, int col){
   readTable = loadTable("new.csv", "header, csv");
  PVector cone = new PVector(0,0);
 // println("TEST");
  for (TableRow row2 : readTable.rows()) {
    if(row == row2.getInt("Row")){
      if(col == row2.getInt("Column")){
        cone.x = row2.getFloat("X");
        cone.y = row2.getFloat("Z");}
    }
  }
  return cone;
}

  
 void textureBox(float size, PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6) {
   
   
   w = h = d = size;
   stroke(50);
    strokeWeight(1.0);
    strokeJoin(ROUND);
  beginShape();
  
  texture(tex1);

    
  // +Z "front" face
  vertex(-1*w, -1*h,  1*d, 0, 0);
  vertex( 1*w, -1*h,  1*d, 1, 0);
  vertex( 1*w,  1*h,  1*d, 1, 1);
  vertex(-1*w,  1*h,  1*d, 0, 1);
  endShape();
  
  beginShape();
  texture(tex3);

  // -Z "back" face
  vertex( 1*w, -1*h, -1*d, 0, 0);
  vertex(-1*w, -1*h, -1*d, 1, 0);
  vertex(-1*w,  1*h, -1*d, 1, 1);
  vertex( 1*w,  1*h, -1*d, 0, 1);
  endShape();

  beginShape();
  texture(tex5);
  // +Y "bottom" face
  vertex(-1*w,  1*h,  1*d, 0, 0);
  vertex( 1*w,  1*h,  1*d, 1, 0);
  vertex( 1*w,  1*h, -1*d, 1, 1);
  vertex(-1*w,  1*h, -1*d, 0, 1);
  endShape();

  beginShape();  
  texture(tex6);
  // -Y "top" face
  vertex(-1*w, -1*h, -1*d, 0, 0);
  vertex( 1*w, -1*h, -1*d, 1, 0);
  vertex( 1*w, -1*h,  1*d, 1, 1);
  vertex(-1*w, -1*h,  1*d, 0, 1);
  endShape();
  
  beginShape();
  texture(tex2);
  // +X "right" face
  vertex( 1*w, -1*h,  1*d, 0, 0);
  vertex( 1*w, -1*h, -1*d, 1, 0);
  vertex( 1*w,  1*h, -1*d, 1, 1);
  vertex( 1*w,  1*h,  1*d, 0, 1);
  endShape();

  beginShape();
  texture(tex4);
  // -X "left" face
  vertex(-1*w, -1*h, -1*d, 0, 0);
  vertex(-1*w, -1*h,  1*d, 1, 0);
  vertex(-1*w,  1*h,  1*d, 1, 1);
  vertex(-1*w,  1*h, -1*d, 0, 1);

  endShape();
  
} 
  void twoBox(float s1, float s2,  PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6){
    beginShape();
    textureBox(s1, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    beginShape();
    translate((s1+s2),(s1-s2),0);
    textureBox(s2, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    
  }
  
  void threeBox(float s1, float s2, float s3, PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6, int num){
    
    
    beginShape();
    textureBox(s1, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    beginShape();
    translate((s1+s2),(s1-s2),0);
    textureBox(s2, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    
    beginShape();
    translate(-s3, -(2*s3), 0);
    textureBox(s2, tex1, tex2, tex3, tex4,  tex5,  tex6);
    
    endShape();
  }
    

void whBox(int row, int col){
//  output = createWriter("boxPosition.txt");
   
    t.addColumn("id");
    t.addColumn("Row");
    t.addColumn("Column");
    t.addColumn("Category");
    t.addColumn("X");
    t.addColumn("Y");
    t.addColumn("Z");
    
  
  for(int i =0; i < row; i++){
    for(int j=0; j<col; j++){
      
      pushMatrix();
      translate(i*80, -11, -j+(j*80));
      
      b[i][j] = new whBox();
      b[i][j].threeBox(size ,size, size ,text1, text2, text3, text4, text5,text5, boxCount);
      
     
      float x=modelX(0,0,0);
      float y = modelY(0,0,0);
      float z = modelZ(0,0,0);

//     // loc[i][j] = new PVector(x,y,z);
//      if((i<10)||(j<10)){
//      output.println("Box  row: " + i + " column:   " + j + "   position: X = " + x + "  Y=  " + y + "   Z=  "+z);
//      }
      TableRow r = t.addRow();
      r.setInt("id", boxCount);
      r.setInt("Row", i+1);
      r.setInt("Column", j+1);
      
      if(boxCount<=5){
        r.setString("Category", "Automotive");
      }else if(boxCount>5 && boxCount<=10){
        r.setString("Category", "Baby & Toddlers");
      }else if(boxCount>10 && boxCount<=15){
        r.setString("Category", "Books");
      }else if(boxCount>15 && boxCount<=20){
        r.setString("Category", "Cards / Insurance");
      }else if(boxCount>20 && boxCount<=25){
        r.setString("Category", "Clothing");
      }else if(boxCount>25 && boxCount<=30){
        r.setString("Category", "DVDs");
      }else if(boxCount>30 && boxCount<=35){
        r.setString("Category", "Electronics");
      }else if(boxCount>35 && boxCount<=40){
        r.setString("Category", "Everyday Needs");
      }else if(boxCount>40 && boxCount<=45){
        r.setString("Category", "Food & Beverages");
      }else if(boxCount>45 && boxCount<=50){
        r.setString("Category", "Furnitures");
      }else if(boxCount>50 && boxCount<=55){
        r.setString("Category", "Gaming");
      }else if(boxCount>55 && boxCount<=60){
        r.setString("Category", "Gardening");
      }else if(boxCount>60 && boxCount<=65){
        r.setString("Category", "Hardware");
      }else if(boxCount>65 && boxCount<=70){
        r.setString("Category", "Health & Beauty");
      }else if(boxCount>70 && boxCount<=75){
        r.setString("Category", "Homewares");
      }else if(boxCount>75 && boxCount<=80){
        r.setString("Category", "Jwellery");
      }else if(boxCount>80 && boxCount<=85){
        r.setString("Category", "Shoes");
      }else if(boxCount>85 && boxCount<=90){
        r.setString("Category", "Stationary");
      }else if(boxCount>90 && boxCount<=95){
        r.setString("Category", "Sports & Outdoors");
      }else if(boxCount>95 && boxCount<=100){
        r.setString("Category", "Toys");
      }
      r.setFloat("X", x);
      r.setFloat("Y", y);
      r.setFloat("Z",z);
      
      popMatrix();
      boxCount += 1;
      
      
    }
  }

  saveTable(t, "data/new.csv");

}


void setBoxValue(){
   
  readTable = loadTable("new.csv", "header, csv");
    int a=0;
   println(readTable.getRowCount() + " total Boxes in table");
   
   for (TableRow row : readTable.rows()) {
    
   id[a] = row.getInt("id");
    
    cat[a] = row.getString("Category");
    
   // println(cat[a]);
    a += 1;
    
    
  }
  
}

void catBox(float s3, int boxCount){
  textSize(10);
  if(boxCount<=5 ){
        text("Automotive", -s3, -30, 0); 
      }else if(boxCount>5 && boxCount<=10){
        text("Baby & Toddlers", -s3, -30, 0);
        
      }else if(boxCount>10 && boxCount<=15){
       text("Books", -s3, -30, 0); 
        
      }else if(boxCount>15 && boxCount<=20){
       text("Cards / Insurance", -s3, -30, 0); 
       
      }else if(boxCount>20 && boxCount<=25){
        text("Clothing", -s3, -30, 0); 
        
      }else if(boxCount>25 && boxCount<=30){
        text("DVDs", -s3, -30, 0); 
        
      }else if(boxCount>30 && boxCount<=35){
        text("Electronics", -s3, -30, 0); 
       
      }else if(boxCount>35 && boxCount<=40){
        text("Everyday Needs", -s3, -30, 0); 
        
      }else if(boxCount>40 && boxCount<=45){
        text("Food & Beverages", -s3, -30, 0); 
       
      }else if(boxCount>45 && boxCount<=50){
        text("Furnitures", -s3, -30, 0); 
        
      }else if(boxCount>50 && boxCount<=55){
        text("Gaming", -s3, -30, 0); 
       
      }else if(boxCount>55 && boxCount<=60){
        text("Gardening", -s3, -30, 0); 
       
      }else if(boxCount>60 && boxCount<=65){
       text("Hardware", -s3, -30, 0); 
       
      }else if(boxCount>65 && boxCount<=70){
        text("Health & Beauty", -s3, -30, 0); 
    
      }else if(boxCount>70 && boxCount<=75){
        text("Homewares", -s3, -30, 0); 
        
      }else if(boxCount>75 && boxCount<=80){
        text("Jwellery", -s3, -30, 0); 
       
      }else if(boxCount>80 && boxCount<=85){
        text("Shoes", -s3, -30, 0); 
        
      }else if(boxCount>85 && boxCount<=90){
        text("Stationary", -s3, -30, 0); 
        
      }else if(boxCount>90 && boxCount<=95){
        text("Sports & Outdoors", -s3, -30, 0); 
      } else if(boxCount>95 && boxCount<=100){
        text("Toys", -s3, -30, 0); 
        
      }
  
 
}
    
    

void threeBoxNew(float s1, float s2, float s3, PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6, int num){
    
    
    beginShape();
    textureBox(s1, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    beginShape();
    translate((s1+s2),(s1-s2),0);
    textureBox(s2, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    
    beginShape();
    translate(-s3, -(2*s3), 0);
    textureBox(s2, tex1, tex2, tex3, tex4,  tex5,  tex6);
    
    endShape();
    
//    if(num <=100){
     
//    }else{
//      num = 1;}
   if(num<100){
     catBox(s3,num);}
     else{
       num = 1;
       catBox(s3, num);
     }
    
  }



void createWarehouse(int row, int col){
 
 int g=1;
  
  for(int i =0; i < row; i++){
    for(int j=0; j<col; j++){
      pushMatrix();
      translate(i*80, -11, -j+(j*80));
      
      b[i][j] = new whBox();
      b[i][j].threeBoxNew(size,size, size,text1, text2, text3, text4, text5,text5, g);
  
    
      
      popMatrix();
      
      g += 1;
    }
  }
  
}
}
  

