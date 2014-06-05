import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;


public class PatientDeathEvent extends Event<PatientEntity>{

	public PatientDeathEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		
	}

}
