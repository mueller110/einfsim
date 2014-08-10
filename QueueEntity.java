import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;

/**
 * the queues
 */
public class QueueEntity extends Entity{
	
	public Queue queue;
	/**
	 * constructor
	 * @param owner
	 * @param name
	 * @param showInTrace
	 * @param queue
	 */
	public QueueEntity(Model owner, String name, boolean showInTrace,Queue queue) {
		super(owner, name, showInTrace);
		this.queue=queue;
	}
	
}
