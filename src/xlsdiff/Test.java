package xlsdiff;

public class Test {
	public void getValue(String str, int x){
		System.out.println(str+"="+x);
	}
	
	public void printstr(String str){
		System.out.println(str);
	}
	
	public void diff(){
		String a = "a";
		String b = "a";
		
		if(a != b){
			System.out.print("testNG");
		}
		else {
			System.out.print("testOK");
		}
	}
}
