import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimTime;
import desmoj.extensions.visualization2d.animation.core.simulator.EntityAnimation;

public class PatientEntity extends Entity {
	private int priority;
	public SimTime start;
	public SimTime start2;
	public SimTime end;
	public SimTime end2;
	public SimTime waitingTime;
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

}
