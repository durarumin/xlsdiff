package xlsdiff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author naoto
 *
 */
public class XlsDiff {
	
	public static void main(String[] args){
		
		if(args.length != 2){
			System.out.println("args <file1> <file2>");
			System.exit(1);
		}
		
		//引数でファイルを指定
		String old_filename = args[0];
		String new_filename = args[1];
		
		System.out.println("old_file:"+old_filename);
		System.out.println("new_file:"+new_filename);
		
		Read o_read = new Read(old_filename);
		try {
			o_read.setData();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			return;
		} catch (IOException e2) {
			e2.printStackTrace();
			return;
		}

		Read n_read = new Read(new_filename);
		try {
			n_read.setData();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			return;
		} catch (IOException e2) {
			e2.printStackTrace();
			return;
		}

		/***
		 * ファイルの比較
		 */
		int i = 0;
		int j = 0;
		int dflag = 0; //成否のflag
		ArrayList<String> array  = new ArrayList<String>();
		
		if(o_read.getY() == n_read.getY() && o_read.getX() == n_read.getX()){
			//この中に入った時はデータの総数は変わらないので古い方の行数、列数を使う
			for(i=0; i<=o_read.getY(); i++){
				for(j=0; j<=o_read.getX(); j++){
					if(o_read.getMap().get(i+":"+j).equals(n_read.getMap().get(i+":"+j))){
						//correct is no process
					}else{
						array.add("row"+i+":"+"column"+j);
						dflag = 1;
						System.out.println("row"+i+":"+"column"+j);
						System.out.println("old:"+o_read.getMap().get(i+":"+j));
						System.out.println("new:"+n_read.getMap().get(i+":"+j));
						System.out.println("=========");
					}
				}
			}
		}else{
			System.out.println("diffing numnber of data");
			dflag = 1;
		}
		
		/***
		 * diff要素を出力
		 */
		if(dflag == 0){
			System.out.println("OK");
			System.exit(dflag);
		}else{
			System.out.println("NG!!");
//			Iterator<String> it = array.iterator();
//			while(it.hasNext()){
//				System.out.println(it.next());
//			}
			System.exit(dflag);
		}
	}
}
