/*
 * @(#) Roberto.java
 * 
 * Tern Tangible Programming Language
 * Copyright (c) 2011 Michael S. Horn
 * 
 *           Michael S. Horn (michael.horn@tufts.edu)
 *           Northwestern University
 *           2120 Campus Drive
 *           Evanston, IL 60613
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 2) as
 * published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package tidal.tern;

import tidal.tern.rt.Robot;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.Log;


/**
 * Roberto implementation of Robot
 */
public class Roberto implements Robot {
   
   public static final String TAG = "Roberto";

   /** Frame rate constant (milliseconds) */   
   private static final int DURATION = 200;
   
   
   /** Reference to the View object */
   protected ProgramView view;
   
   /** Current frame index to be drawn */
   private int frame = 0;
   
   /** Frame count for current animation pose */
   private int fcount = 0;
      
   /** Name of current animation */   
   private String pose = null;
   
   public static boolean tsensor = false;
   
   public static boolean isPlaying = false;
   
   private String text = null;
   
   private long last_tick = 0;
   
   
   
   public Roberto(ProgramView view) {
      this.view = view;
   }
   
   
   /**
    * These functions are inherited from the Robot interface but not
    * needed for Roberto.
    */
   public boolean isConnected() { return true; }
   public void setAddress(String address) {  }
   public void openConnection() { }
   public void closeConnection() { }
   public void allStop() {
      frame = fcount;
   }
   
   
   private void drawFrame(Canvas canvas) {
      if (pose != null)  {
      
         // Determine the resource id and load the drawable
         Resources res = view.getResources();
         String name = pose + "0" + (int)Math.min(frame, fcount);
         Log.i(TAG, name);
         int id = res.getIdentifier(name, "drawable", "tidal.tern");
         Drawable current = res.getDrawable(id);
         
         // Draw the frame
         if (current != null) {
            int w = view.getWidth();
            int h = view.getHeight();
            int dw = current.getIntrinsicWidth()/2;
            int dh = current.getIntrinsicHeight()/2;
            int dx = w/2 - dw/2;
            int dy = h/2 - dh/2;
            current.setBounds(dx, dy, dx + dw, dy + dh);
            current.draw(canvas);
         }
      }
   }
   
   
   public void draw(Canvas canvas) {
	   
	   if (isPlaying) { 
		   long elapsed = (System.currentTimeMillis() - last_tick);
		   
	       if (elapsed >= DURATION) {
	    	   if (frame <= fcount) {
	    	      last_tick = System.currentTimeMillis();
	    		   frame++;
	    	   } else {
	    		   isPlaying = false;
	    	   }
	       }
	      
	      drawFrame(canvas);
	      
		  if (frame <= fcount)
			  view.repaint();
	   }
	   
	  
	  if (this.text != null) {
		  int w = view.getWidth();
	      
	      Paint font = new Paint(Paint.ANTI_ALIAS_FLAG);
	      font.setColor(Color.BLACK);
	      font.setStyle(Style.FILL);
	      font.setTextSize(25);
	      font.setTextAlign(Paint.Align.CENTER);
	      canvas.drawText(this.text, w/2, 27, font);
	      
	      this.text = null;
		  
	  }
	  
	  
	  
	  
	  /**if (view.interpFinished && view.missedSticker) {
		  
		// clear background 
	      canvas.drawRGB(210, 210, 210);
		  int w = view.getWidth();
	      int h = view.getHeight();
	      
	    	 Paint font = new Paint(Paint.ANTI_ALIAS_FLAG);
	         font.setColor(Color.BLACK);
	         font.setStyle(Style.FILL);
	         font.setTextSize(25);
	         font.setTextAlign(Paint.Align.CENTER);
	         canvas.drawText("Couldn't complete,", w/2, 27, font);
	         
	         //if (view.stickerName != null)
	        	// canvas.drawText("Make sure \"" +view.stickerName+ "\" sticker is aligned", w/2, 67, font);
	         
	        // else
	        	 canvas.drawText("Make sure stickers are aligned", w/2, 67, font);
	        	 
	         canvas.drawText("and not faded, and then try again", w/2, 107, font);
	     }//*/
	  
   }

   
   private void changePicture(String pose, int frame_count) {
      this.pose = pose;
      this.fcount = frame_count;
      this.frame = 1;
      this.last_tick = 0;
      isPlaying = true;
      this.text = null;
      view.repaint();
   }
   
   
   public int doJump(int [] args) {
	  ProgramView.sounds.play(ProgramView.jump_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("jump", 5);
      return 0;
   }
   
   
   public int doRun(int [] args) {
	  ProgramView.sounds.play(ProgramView.run_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("run", 1);
      return 0;
   }
   
   
   public int doWalk(int [] args) {
	  ProgramView.sounds.play(ProgramView.walk_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("walk", 5);
      return 0;
   }
   
   
   public int doWiggle(int [] args) {
	  ProgramView.sounds.play(ProgramView.wiggle_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("wiggle", 6);
      return 0;
   }
   
   
   public int doSleep(int [] args) {
	  ProgramView.sounds.play(ProgramView.sleep_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("sleep", 1);
      return 0;
   }
   
   
   public int doYawn(int [] args) {
	  ProgramView.sounds.play(ProgramView.yawn_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("yawn", 1);
      return 0;
   }
   
   
   public int doStand(int [] args) {
	  ProgramView.sounds.play(ProgramView.stand_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("stand", 1);
      return 0;
   }
   
   
   public int doSpin(int [] args) {
	  ProgramView.sounds.play(ProgramView.spin_sound, 1.0f, 1.0f, 0, 0, 1.0f);
      changePicture("spin", 6);
      return 0;
   }
   
   
   
   public int doEnd(int [] args) {
	   changePicture("end", 1);
	   return 0;
   }
   
   
   public int doDance(int [] args) {
      return 0;
   }
   
   public int doWait(int [] args) {
	   int p = args[0];
	   if (p == 1)
		   this.text = "WAIT FOR TAP...";
	   else if (p == 1000)
		   this.text = "WAIT FOREVER...";
	   else
		   this.text = "WAIT FOR " + p + " SECONDS..";
	   
	   view.repaint();
	   return 0;
   }
   
   public int getTouchSensor(int [] args) {    
	   int result = tsensor ? 1 : 0;      
	   tsensor = false; 
	   return result;   
   }

}