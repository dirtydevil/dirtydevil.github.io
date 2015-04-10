import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.awt.MouseInfo; 
import java.awt.Point; 
import shapes3d.utils.*; 
import shapes3d.animation.*; 
import shapes3d.*; 
import processing.opengl.*; 
import processing.core.PApplet; 
import processing.core.PVector; 
import shapes3d.utils.*; 
import shapes3d.animation.*; 
import shapes3d.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_19aug extends PApplet {










PImage text1, text2, text3, text4, text5, text6, bg ; //Images

Terrain terrain; // terrain object of class Terrain is initialized
Box skybox; // skybox object of class Box is initialized
Cone c;  //Marker
//whBox b[10][10];
whBox b[][] = new whBox[11][11];

whBox wh;


float terrainSize = 4000;   //size of real world is given
float horizon = 4000;       //visiblity limit for drwawing wrap around

TerrainCam cam;
int camHoversAt = 30 ;

float camSpeed;
long time;

float size = 10;
float size1 = 20;

PVector left_Dir = new PVector(10,0,0);
PVector right_Dir = new PVector(-10,0,0);
PVector front_Dir = new PVector(0,0,-10);
PVector back_Dir = new PVector(0,0,10);
PVector pos;

int boxCount = 0;

float res = size;

PrintWriter output, newO;





final int MainScreen  = 0; // Menue at the beginning
final int Start        = 1; // Enter the Warehouse to search for the Box.

int state = MainScreen;

Table tab;

int rowNum, colNum;

public void setup(){
   size(1000,600, P3D);
   cursor(CROSS);
   
  smooth(8);
   
    bg = loadImage("MS.jpg");
  
   
   text1 = loadImage("cardboard side.png");   //front of box
   text2 = loadImage("cardboard.png");        //Left of box   
   text3 = loadImage("cardboard_left.png");   //Back of the box
   text4 = loadImage("cardboard_top.png");    //Right of the box
   text5 = loadImage("top.png");              //Top of the box
  
  //Floor details
  terrain = new Terrain(this, 60, terrainSize, horizon);
  terrain.usePerlinNoiseMap(0, 0, 0.15f, 0.15f);
  terrain.setTexture("bg1.jpg", 4);
  terrain.tag = "Ground";
  terrain.tagNo = -1;
  terrain.drawMode(S3D.TEXTURE);
  
  
  //Sky details
  skybox = new Box(this,1200,300,1200);
  skybox.setTexture("whwalls.png", Box.FRONT);
  skybox.setTexture("whwalls.png", Box.BACK);
  skybox.setTexture("whwalls.png", Box.LEFT);
  skybox.setTexture("whwalls.png", Box.RIGHT);
  skybox.setTexture("whceiling.jpg", Box.TOP);
  skybox.visible(false, Box.BOTTOM);
  skybox.drawMode(S3D.TEXTURE);
  skybox.tag = "Skybox";
  skybox.tagNo = -1;
  
  camSpeed = 10;
  cam = new TerrainCam(this);
  cam.adjustToTerrain(terrain, Terrain.WRAP, camHoversAt);
  
  //PVector pos1 = new PVector(27, -15, -90);
  PVector pos1 = new PVector(27, -31, -90);
  
  cam.eye(pos1);
  cam.camera();
  cam.speed(camSpeed);
  cam.forward.set(cam.lookDir());


  


  // Tell the terrain what camera to use
  terrain.cam = cam;

  time = millis();
//   b = new whBox();
   
  wh = new whBox();
  wh.whBox(10,10);
 //  wh.location(3,6);
  
}

public void draw(){
  
  background(0);  

   switch(state){
    case MainScreen:

      mainScreen();
      break;
     case Start:
       startWareHouse();
       break;
  }
  
  
}

public void mainScreen(){
  background(bg);
  smooth();
  if(onButton(85, 165, 175, 55) == true){ 
    state = Start;
   rowNum=1;
   colNum =3 ;
   
  }  else if(onButton(85, 250, 175, 55) == true){  
     state = Start;
     rowNum = 1;
     colNum = 7;
     
  }
   else if(onButton(85, 325, 175, 55) == true){  
     state = Start;
     rowNum = 2;
     colNum = 3;
     
  }
   else if(onButton(85, 405, 175, 55) == true){  
     state = Start;
     rowNum = 2;
     colNum = 7;
     
      }
   else if(onButton(85, 485, 175, 55) == true){  
     state = Start;
     rowNum = 3;
     colNum = 3;
     
   }
   else if(onButton(295, 165, 175, 55) == true){  
     state = Start;
     rowNum = 3;
     colNum = 7;  
     
   }
  else if(onButton(295, 250, 175, 55) == true){  
     state = Start;
     rowNum = 4;
     colNum = 3;
     
   }
   else if(onButton(295, 325, 175, 55) == true){  
     state = Start;
     rowNum = 4;
     colNum = 7;
     
    }
   else if(onButton(295, 405, 175, 55) == true){  
     state = Start;
     rowNum = 5;
     colNum = 3;
     
   }
   else if(onButton(295, 465, 175, 55) == true){  
     state = Start;
     rowNum = 5;
     colNum = 7;
     
    }
      else if(onButton(505, 165, 175, 55) == true){  
     state = Start;
     rowNum = 6;
     colNum = 3;
     
   }
  else if(onButton(505, 250, 175, 55) == true){  
     state = Start;
     rowNum = 6;
     colNum = 7;
     
    }
   else if(onButton(505, 325, 175, 55) == true){  
     state = Start;
     rowNum = 7;
     colNum = 3;
     
    }
   else if(onButton(505, 405, 175, 55) == true){  
     state = Start;
     rowNum = 7;
     colNum = 7;
     
    }
   else if(onButton(505, 465, 175, 55) == true){  
     state = Start;
     rowNum = 8;
     colNum = 3;
     
    }
      else if(onButton(715, 165, 175, 55) == true){  
     state = Start;
     rowNum = 8;
     colNum = 7;
     
  }
  else if(onButton(715, 250, 175, 55) == true){  
     state = Start;
     rowNum = 9;
     colNum = 3; 
     
    }
   else if(onButton(715, 325, 175, 55) == true){  
     state = Start;
     rowNum = 7;
     colNum = 4;
     
   }
   else if(onButton(715, 405, 175, 55) == true){  
     state = Start;
     rowNum = 10;
     colNum = 4;  
     
    }
   else if(onButton(715, 465, 175, 55) == true){  
     state = Start;
     rowNum = 10;
     colNum = 8;
   } 
      
}


  
  public boolean onButton(int x, int y, int w, int h){
  if(mouseX > x && mouseX < x+w && mouseY > y && mouseY < y+h){
    if(mousePressed == true){
      return true;
    }
    else{
       return false;
     }}
   else{
       return false;
  }
}
  
  
  public void startWareHouse(){
  long t = millis() - time;
  time = millis();
  
  // Calculate amount of movement based on velocity and time
 // cam.move(t/1000.0f);
 // cam.move(0);
  PVector location = wh.getBoxVal(rowNum, colNum);
   
  // PVector b1 = new PVector(500,100,500);
  c = new Cone(this,10);
  c.setSize(5,5,-20);
  c.moveTo(getRandomPosOnTerrain(terrain, terrainSize, 50.0f, location));

  c.tagNo = 100;
  c.fill(color(random(128,255), random(128,255), random(128,255)));
  c.drawMode(S3D.SOLID);
  terrain.addShape(c);
  
  
  // Adjust the cameras position so we are over the terrain
  // at the given height.
  cam.adjustToTerrain(terrain, Terrain.WRAP, camHoversAt);
  // Set the camera view before drawing
  cam.camera();
  
  pos = new PVector();
 
   //Draw some boxes
  noStroke();
  

  
  wh.createWarehouse(10,10);
  

 
  
  if(mousePressed){
    float achange = (mouseX - pmouseX) * PI / width;
    // Keep view and move directions the same
    cam.rotateViewBy(achange);
    cam.turnBy(achange);
  }
   
  terrain.draw();
  // Get rid of directional lights so skybox is evenly lit.
  skybox.moveTo(cam.eye().x, 0, cam.eye().z);
  skybox.draw();
  
  

  //If a key is pressed, turn on the move boolean
if (keyPressed) {
  if(key == 'w'||key =='W'){
  update(t/100.0f);
  pos = cam.eye();

  
  }
  if(key=='s'||key=='S'){
     update(-t/100.0f);
     pos = cam.eye();

  }

  if(key=='q'||key=='Q'){
 
}
}  
}


  

public PVector getRandomPosOnTerrain(Terrain t, float tsize, float height, PVector ob){
  PVector p = new PVector(ob.x, 0, ob.y);
  p.y = t.getHeight(p.x, p.z) - height-10;
  return p;
 // println(p);
}

public void update(float t){
  cam.move(t);
  cam.adjustToTerrain(terrain,Terrain.WRAP,camHoversAt);
}
/*
  Class to create WareHouse

*/








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
  
  
  public void whBox(){
   
    

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

  
 public void textureBox(float size, PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6) {
   
   
   w = h = d = size;
   stroke(50);
    strokeWeight(1.0f);
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
  public void twoBox(float s1, float s2,  PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6){
    beginShape();
    textureBox(s1, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    beginShape();
    translate((s1+s2),(s1-s2),0);
    textureBox(s2, tex1, tex2, tex3, tex4,  tex5,  tex6);
    endShape();
    
  }
  
  public void threeBox(float s1, float s2, float s3, PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6, int num){
    
    
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
    

public void whBox(int row, int col){
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


public void setBoxValue(){
   
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

public void catBox(float s3, int boxCount){
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
    
    

public void threeBoxNew(float s1, float s2, float s3, PImage tex1, PImage tex2, PImage tex3, PImage tex4, PImage tex5, PImage tex6, int num){
    
    
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



public void createWarehouse(int row, int col){
 
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
  

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "sketch_19aug" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
