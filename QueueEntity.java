import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;

public class QueueEntity extends Entity{
	
	public Queue queue;
	public QueueEntity(Model owner, String name, boolean showInTrace,Queue queue) {
		super(owner, name, showInTrace);
		this.queue=queue;
	}
	
}
