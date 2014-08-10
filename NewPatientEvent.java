import java.util.Random;

import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;
/**
 * The new patient event creates a new patient
 */

public class NewPatientEvent extends ExternalEvent {

	// the model
	private EmergencyRoomModel model;
	// rand is used to get the priority of the patient
	private static Random rand = new Random();
	
	/**
	 * constructor
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public NewPatientEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	/**
	 * the event occurs
	 */
	@Override
	public void eventRoutine() {
		// get the priority of the patient
		// priority 3: 20%
		// priority 1: 80%
		int prio;	
		if (rand.nextDouble() <= 0.2) {
			prio = 3;
		} else {
			prio = 1;
		}
	
		PatientEntity patient = new PatientEntity(model, "Patient", true, prio);
		PatientArrivalEvent arrival = new PatientArrivalEvent(model,
				"Arrival of Patient", true);
		arrival.schedule(patient, new SimTime(0.0)); // instant arrival

		// create a new patient
		NewPatientEvent nextPatient = new NewPatientEvent(model,
				"Creation of Patient", true);
		// set the arrival time of the new patient
		nextPatient.schedule(new SimTime(model.getPatientArrivalTime()));
		
	}
}
