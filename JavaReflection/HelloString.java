public class HelloString {
    private String str;

    public HelloString(){
        str = "";
    }

    public void print(String str){
        System.out.println("Hello" + " " + str);
    }
	
	public static void staticPrint(String str){
		System.out.println("static function: Hello" + " " + str);
	}
}
