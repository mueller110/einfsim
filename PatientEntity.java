import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.extensions.visualization2d.animation.core.simulator.EntityAnimation;

public class PatientEntity extends Entity {
	private int priority;

	public PatientEntity(Model owner, String name, boolean showInTrace,
			int priority) {
		super(owner, name, showInTrace);
		this.priority = priority;
		// TODO Auto-generated constructor stub
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
