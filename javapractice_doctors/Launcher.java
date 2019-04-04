package javapractice_doctors;

public class Launcher {
	
	public static void main(String[] args) {
		Doctor doctorInstance = new Doctor();
		FamilyDoctor familyDoctorInstance = new FamilyDoctor();
		Surgeon surgeonInstance = new Surgeon();
//		INITIALIZING
		doctorInstance.worksAtHospital=false;
		familyDoctorInstance.worksAtHospital=true;
		surgeonInstance.worksAtHospital=false;
//		printAttributes
		System.out.print("doctor doctorInstance works At Hospital: " + doctorInstance.worksAtHospital +"\n" );
		System.out.print("doctor familyDoctorInstance works At Hospital: " + familyDoctorInstance.worksAtHospital +"\n");
		System.out.print("doctor surgeonInstance works At Hospital: " + surgeonInstance.worksAtHospital +"\n");
//		methodTest
		doctorInstance.treatPatient();
		familyDoctorInstance.treatPatient();
		familyDoctorInstance.giveAdvice();
		surgeonInstance.treatPatient();
		surgeonInstance.makeIncision();	
		// Doctor s1=new Surgeon(); //Understanding Object.equals() method
		// Doctor f1=new FamilyDoctor();
		// Doctor s2=new Surgeon();
		// Surgeon s1Clone= (Surgeon)s1;
		// Surgeon s2Clone= (Surgeon)s2;
		// System.out.println(s1.getClass());
		// System.out.println(f1.getClass());
		// System.out.println("s1 is equal to f1?: "+s1.equals(f1));
		// System.out.println("s1 is equal to s2?: "+s1.equals(s2));
		// System.out.println("s1 is equal to s1?: "+s1.equals(s1));
		// System.out.println("s1 is equal to s1Clone?: "+s1.equals(s1Clone));
		// System.out.println("s1 is equal to s2Clone?: "+s1.equals(s2Clone));
	}	
}