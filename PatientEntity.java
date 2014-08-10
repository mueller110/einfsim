import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;

/**
 * The patient entity
 */
@SuppressWarnings("deprecation")
public class PatientEntity extends Entity {
	// its priority
	private int priority;
	// its arrival time
	public SimTime arrivalTime;
	public SimTime start;
	public SimTime departureTime;
	public SimTime waitingTime;
	public SimTime end;
	public boolean treatementInterrupted;
	public SimTime rest;
	public SimTime treatmentStart;
	public SimTime treatmentDuration;
	boolean isZero = true;
	TreatmentTermination treatmentTermination;
	PatientDeathEvent deathEvent;

	/**
	 * constructor
	 * 
	 * @param owner
	 * @param name
	 * @param showInTrace
	 * @param priority
	 */
	public PatientEntity(Model owner, String name, boolean showInTrace,
			int priority) {
		super(owner, name, showInTrace);
		this.priority = priority;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * set the priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the time spent in the emergency room
	 */
	public SimTime getStay() {
		if (departureTime == null)
			return null;
		return SimTime.diff(departureTime, arrivalTime);
	}

}
