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
	// start time
	public SimTime start;
	// its departure time
	public SimTime departureTime;
	// total wating time of the patient
	public SimTime waitingTime;
	// departure time
	public SimTime end;
	// if the treatement was interrupted
	public boolean treatementInterrupted;
	// if interrupted, rest of the treatment time
	public SimTime rest;
	// treatment start time
	public SimTime treatmentStart;
	// the treatment duration
	public SimTime treatmentDuration;
	// no waiting time
	boolean isZero = true;
	// its treatment termination event
	TreatmentTermination treatmentTermination;
	// its death event
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
