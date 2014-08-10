import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;

/**
 * The patient death event represents a death of a patient (priority 3)
 */
public class PatientDeathEvent extends Event<PatientEntity>{

	EmergencyRoomModel model;
	
	/**
	 * constructor
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public PatientDeathEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		// the patient died ---> remove the patient of its queue
		model.deaths++;
		model.highPriorityPatientQueue.remove(patient);
	}
}
