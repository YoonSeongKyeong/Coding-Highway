package javapractice_doctors;

public class Surgeon extends Doctor {
	public void makeIncision(){
		System.out.print("makeIncision"+"\n");
	}
	public void treatPatient() {
		System.out.print("Surgeon treat patient!"+"\n");
	}
}