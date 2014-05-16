import java.util.Random;

import desmoj.core.simulator.*;

public class NewPatientEvent extends ExternalEvent {

	private EmergencyRoomModel model;
	static Random rand = new Random();
	static int count;
	public NewPatientEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}


	@Override
	public void eventRoutine() {
		int prio;
		count++;
		if (rand.nextDouble() <= 0.2){
			prio = 3;
			
		}
		else{
			prio = 1;
		}
		
		PatientEntity patient = new PatientEntity(model, "Patient", true, prio);
		PatientArrivalEvent arrival = new PatientArrivalEvent(model,
				"Arrival of Patient", true);
		arrival.schedule(patient, new SimTime(0.0)); //instant arrival
		NewPatientEvent nextPatient = new NewPatientEvent(model,
				"Creation of Patient", true);
		nextPatient.schedule(new SimTime(model.getPatientArrivalTime()));

	}

}
