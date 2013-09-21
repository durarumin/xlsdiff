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
		
		//�v���p�e�B�t�@�C���������œn��
		String filepath = args[0];
		//�v���p�e�B�t�@�C���ǂݍ���
		Properties config = new Properties();
		try{
			InputStream is = new FileInputStream(new File(filepath));
			config.load(is);
			
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//file�����
		String old_filename = config.getProperty("file_path") + config.getProperty("old_filename");
		String new_filename = config.getProperty("file_path") + config.getProperty("new_filename");
		
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
		 * �t�@�C���̔�r
		 */
		int i = 0;
		int j = 0;
		int dflag = 0; //���ۂ�flag
		ArrayList<String> array  = new ArrayList<String>();
		
		if(o_read.getY() == n_read.getY() && o_read.getX() == n_read.getX()){
			//���̒��ɓ��������̓f�[�^�̑����͕ς��Ȃ��̂ŌÂ����̍s���A�񐔂��g��
			for(i=0; i<=o_read.getY(); i++){
				for(j=0; j<=o_read.getX(); j++){
					if(o_read.getMap().get(i+":"+j).equals(n_read.getMap().get(i+":"+j))){
						//correct is no process
					}else{
						array.add("row"+i+":"+"column"+j);
						dflag = 1;
						System.out.println("old:"+o_read.getMap().get(i+":"+j));
						System.out.println("new:"+n_read.getMap().get(i+":"+j));
					}
				}
			}
		}else{
			System.out.println("diffing numnber of data");
			dflag = 1;
		}
		
		/***
		 * diff�v�f���o��
		 */
		if(dflag == 0){
			System.out.println("OK");
			System.exit(dflag);
		}else{
			System.out.println("diffing!!");
			Iterator<String> it = array.iterator();
			while(it.hasNext()){
				System.out.println(it.next());
			}
			System.exit(dflag);
		}
	}
}
