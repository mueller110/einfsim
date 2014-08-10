import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;


public class PatientDeathEvent extends Event<PatientEntity>{

	EmergencyRoomModel model;
	
	public PatientDeathEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {		
		model.deaths++;
		model.highPriorityPatientQueue.remove(patient);
	}
}
