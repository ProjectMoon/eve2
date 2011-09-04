package eve.eji;

@EJIType("test")
public class Test {
	public Test() {}
	
	public Test __create() {
		return new Test();
	}
	
	public int getX() {
		return 5;
	}
}
