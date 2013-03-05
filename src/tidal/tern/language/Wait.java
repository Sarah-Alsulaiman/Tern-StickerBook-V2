/*
 * @(#) Song.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tidal.tern.language;

import java.io.PrintWriter;

import android.util.Log;
import tidal.tern.compiler.Statement;
import tidal.tern.compiler.CompileException;
import topcodes.TopCode;


public class Wait extends Statement {


   public Wait() {
      super();
   }
   
   
   public Wait(TopCode top) {
      super(top);
   }


   public void compile(PrintWriter out, boolean debug) throws CompileException {
      if (debug) out.println("trace " + getCompileID());
      this.setCompiled();
      
      String limit = "1000";
      
      if (hasConnection("param")) { 
		   limit = getConnection("param").getName();
		   getConnection("param").setCompiled();
	   }
	   
	   if (limit.equals("Tap Sensor") ) {
		   out.println("doWait(1)");
		   out.println("wait 500");
		  
		   	out.println("while not getTouchSensor():");
		    out.println("{");
		    out.println("   wait 100");
		    out.println("}");      
		    compileNext(out, debug);
	   }
	   
	   else { //if parameter is number or nothing (forever)
		   out.println("doWait(" + limit + ")");
		   out.println("wait 1000");
		   out.println("a = 0");
		   out.println("while a < " + limit + ":" );
		   out.println("{");
		   out.println("wait 1000");
		   out.println("a = a + 1");
		   out.println("}");   
		   compileNext(out, debug);
		   
	   }
      
      /**out.println("while not getTouchSensor():");
      out.println("{");
      out.println("   wait 100");
      out.println("}");      
      compileNext(out, debug);//*/
   }
}
