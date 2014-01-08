
package lotte.fw.common.validate;

import java.awt.Color;

public class HtmlEncoder {

   // membervariables

   /** List with the HTML translation of all the characters. */
   private static final String[] htmlCode = new String[256];

   static {
      for (int i = 0; i < 10; i++) {
         htmlCode[i] = "&#00" + i + ";";
      }

      for (int i = 10; i < 32; i++) {
         htmlCode[i] = "&#0" + i + ";";
      }

      //make all spaces visible
      htmlCode[32] = "&nbsp;";


      for (int i = 33; i < 128; i++) {
         htmlCode[i] = String.valueOf((char) i);
      }

      // Special characters
      htmlCode['\t'] = "\t";
      htmlCode['\n'] = "<br />\n";
      htmlCode['\"'] = "&quot;"; // double quote
      htmlCode['&'] = "&amp;"; // ampersand
      htmlCode['<'] = "&lt;"; // lower than
      htmlCode['>'] = "&gt;"; // greater than

      for (int i = 128; i < 256; i++) {
         htmlCode[i] = "&#" + i + ";";
      }
   }


   // constructors

   /**
    * This class will never be constructed.
    * <P>
    * HtmlEncoder only contains static methods.
    */

   private HtmlEncoder() {
   }

   // methods

   /**
    * Converts a <CODE>String</CODE> to the HTML-format of this <CODE>String</CODE>.
    *
    * @param string The <CODE>String</CODE> to convert
    * @return a <CODE>String</CODE>
    */

   public static String encode(String string) {
      int n = string.length();
      char character;
      StringBuffer buffer = new StringBuffer();
      // loop over all the characters of the String.
      for (int i = 0; i < n; i++) {
         character = string.charAt(i);
         // the Htmlcode of these characters are added to a StringBuffer one by one
         if (character < 256) {
            buffer.append(htmlCode[character]);
         } else {
            // Improvement posted by Joachim Eyrich
            buffer.append("&#").append((int) character).append(";");
         }
      }
      return buffer.toString().trim();
   }

   /**
    * Converts a <CODE>Color</CODE> into a HTML representation of this <CODE>Color</CODE>.
    *
    * @param color the <CODE>Color</CODE> that has to be converted.
    * @return the HTML representation of this <COLOR>Color</COLOR>
    */

   public static String encode(Color color) {
      StringBuffer buffer = new StringBuffer("#");
      if (color.getRed() < 16) {
         buffer.append('0');
      }
      buffer.append(Integer.toString(color.getRed(), 16));
      if (color.getGreen() < 16) {
         buffer.append('0');
      }
      buffer.append(Integer.toString(color.getGreen(), 16));
      if (color.getBlue() < 16) {
         buffer.append('0');
      }
      buffer.append(Integer.toString(color.getBlue(), 16));
      return buffer.toString();
   }


}