import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
public class PatientEntity extends Entity {
	private int priority;
	public SimTime arrivalTime;
	public SimTime start;
	public SimTime departureTime;
	public SimTime waitingTime;
	public SimTime end;
	public boolean treatementInterrupted;
	public SimTime rest;
	public SimTime treatmentStart;
	public SimTime treatmentDuration;
	boolean isZero=true;
	TreatmentTermination treatmentTermination;
	PatientDeathEvent deathEvent;

	public PatientEntity(Model owner, String name, boolean showInTrace,
			int priority) {
		super(owner, name, showInTrace);
		this.priority = priority;
		
		
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public SimTime getStay(){
		if (departureTime==null)
			return null;
		return SimTime.diff(departureTime, arrivalTime);
	}

}
