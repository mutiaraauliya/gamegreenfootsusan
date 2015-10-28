/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package string_matching;

/**
 *
 * @author root
 */
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class engine1 {
    String fileInput(String path) {
        InputStream is= null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        File file = new File(path);
        
        //FileInputStream fis = null;
        //BufferedInputStream bis = null;
        //DataInputStream dis = null;
         
        
        StringBuffer sbuffer = new StringBuffer("");
        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is);
             br = new BufferedReader(isr);
             
                 while(is.available() != 0){
                 sbuffer.append(is.read());
                 }
         
                 is.close();
                 isr.close();
                 br.close();
                } 
                catch (FileNotFoundException e) {
                } 
                catch (IOException e){
                    
               // error.printStackTrace();
                
            }
        String str = sbuffer.toString();
        return str;
            
        }
        /*try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            while(dis.available() != 0) {
                sbuffer.append(dis.readLine());
                //System.out.println(dis.readLine());
            }
            fis.close();
            bis.close();
            dis.close();
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } 
        String str = sbuffer.toString();
        return str;
}
*/
     int brute (String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int j;
        for(int i = 0; i <= (n-m); i++) {
            j = 0;
            while((j<m)&&(text.charAt(i+j)==pattern.charAt(j)))
                j++;
            if(j == m)
                return i;
        }
        return -1;
    }
    
    static int kmpMatch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();
        int fail[] = computeFail(pattern);
        int i = 0;
        int j = 0;
        while(i < n) {
            if(pattern.charAt(j) == text.charAt(i)) {
                if(j == m -1)
                    return i - m + 1;
                i++;
                j++;
            }
            else if(j > 0)
                j = fail[j-1];
            else 
                i++;
        }
        return -1;
    }
    
    static int[] computeFail (String pattern) {
        int fail[] = new int[pattern.length()];
        fail[0] = 0;
        int m = pattern.length();
        int j = 0;
        int i = 1;
        while(i<m) {
            if(pattern.charAt(j) == pattern.charAt(i)) {
                fail[i] = j+1;
                i++;
                j++;
            }
            else if(j > 0)
                j = fail[j-1];
            else {
                fail[i] = 0;
                i++;
            }
        }
        return fail;
    }
    
    static int bmMatch(String text,String pattern) {
        int last[] = buildLast(pattern);
        int n = text.length();
        int m = pattern.length();
        int i = m-1;
        
        if(i > n-1) 
            return -1;
        int j = m - 1;
        do {
            if(pattern.charAt(j) == text.charAt(i))
                if(j==0) {
                    return i;
                } else {
                    i--;
                    j--;
                }
            else {
                int lo = last[text.charAt(i)];
                i = i + m - Math.min(j,1+lo);
                j = m - 1;
            }
        } while (i <= n-1);
        return -1;
    }
    
    static int[] buildLast(String pattern) {
        int last[] = new int[128];
        for(int i = 0; i < 128; i++)
            last[i] = -1;
        for(int i = 0; i < pattern.length(); i++)
            last[pattern.charAt(i)] = 1;
        return last;
    }
    
}
