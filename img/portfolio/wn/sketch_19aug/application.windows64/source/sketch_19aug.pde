import java.awt.MouseInfo;
import java.awt.Point;

import shapes3d.utils.*;
import shapes3d.animation.*;
import shapes3d.*;

import processing.opengl.*;

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

void setup(){
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

void draw(){
  
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

void mainScreen(){
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


  
  boolean onButton(int x, int y, int w, int h){
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
  
  
  void startWareHouse(){
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
