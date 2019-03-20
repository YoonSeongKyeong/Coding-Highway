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
	}	
}