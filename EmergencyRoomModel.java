import desmoj.core.dist.RealDistExponential;
import desmoj.core.dist.RealDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
public class EmergencyRoomModel extends Model {
	private int numberOfDoctors = 2;
	private static int simulationTime = 28800;
	private static int arrivalTime = 40;
	public static int underFive = 0;
	
	public static SimTime warmUp=new SimTime(50.0);
	public EmergencyRoomModel(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	private RealDistExponential patientArrivalTime;

	public double getPatientArrivalTime() {
		return patientArrivalTime.sample();
	}

	private RealDistUniform treatmentTime[] = new RealDistUniform[3];

	public double getTreatmentTime(int cprio) {
		return treatmentTime[cprio - 1].sample();
	}
	
	public static Queue<PatientEntity> highPriorityPatientQueue;
	public static Queue<PatientEntity> lowPriorityPatientQueue;
	public static Queue<PatientEntity> lastCheckPatientQueue;
	public static Queue<PatientEntity> allPatientsQueue;
	public static Queue<DoctorEntity> freeDoctorQueue;
	public static Queue<DoctorEntity> busyDoctorQueue;
	public static Queue<PatientEntity> inTreatmentQueue;
	
	public String description() {
		return "TODO: Emergency Department Description";
	}

	public void doInitialSchedules() {
		NewPatientEvent firstPatient = new NewPatientEvent(this,
				"Creation of Patient", true);
		firstPatient.schedule(new SimTime(getPatientArrivalTime()));
	}

	public void init() {
		// new patient every 40 mins (average)
		patientArrivalTime = new RealDistExponential(this,
				"arrival time interval", arrivalTime, true, true);

		patientArrivalTime.setNonNegative(true);

		// es muss gelten: bedienZeitPrio1<bedienZeitPrio3 und
		// bedienZeitPrio2_1< bedienZeitPrio2_3
		treatmentTime[0] = new RealDistUniform(this,
				"treatment time interval (Priority 1)", 10, 30, true, true); // bedienZeitPrio
																				// 1
		treatmentTime[1] = new RealDistUniform(this,
				"treatment time interval (Priority 2_1)", 10, 20, true, true); // bedienZeitPrio
																				// 2_1
		treatmentTime[2] = new RealDistUniform(this,
				"treatment time inverval (Priority 3)", 50, 120, true, true); // bedienZeitPrio
																				// 3

		highPriorityPatientQueue = new Queue<PatientEntity>(this,
				"High-Priority Queue", true, true);
		lowPriorityPatientQueue = new Queue<PatientEntity>(this,
				"Low-Priority Queue", true, true);

		allPatientsQueue = new Queue<PatientEntity>(this, "Statistic", false, false);

		inTreatmentQueue = new Queue<PatientEntity>(this, "Patient with Dr.", true, true);
		
		// for prio 2 patients
		lastCheckPatientQueue = new Queue<PatientEntity>(this,
				"Last-Check queue", true, true);

		freeDoctorQueue = new Queue<DoctorEntity>(this, "free doctors", true,
				true);

		for (int i = 0; i < numberOfDoctors; i++) {
			freeDoctorQueue.insert(new DoctorEntity(this, "doctor", true));
		}

		busyDoctorQueue = new Queue<DoctorEntity>(this, "busy doctors", true,
				true);
	}

	public static void main(java.lang.String[] args) {

		Experiment emergencyExperiment = new Experiment("Emergency-Room");

		EmergencyRoomModel model = new EmergencyRoomModel(null,
				"Emergency-Room Model", true, true);
		model.connectToExperiment(emergencyExperiment);
		emergencyExperiment.tracePeriod(new SimTime(0.0), new SimTime(
				simulationTime));
		emergencyExperiment.debugPeriod(new SimTime(0.0), new SimTime(
				simulationTime));
		
		ResetEvent reset=new ResetEvent(model,"Queue Reset",true);		
		reset.schedule(new QueueEntity(model,"Queue",true,highPriorityPatientQueue),warmUp);
		reset=new ResetEvent(model,"Queue Reset",true);
		reset.schedule(new QueueEntity(model,"Queue",true,lowPriorityPatientQueue),warmUp);
		reset=new ResetEvent(model,"Queue Reset",true);
		reset.schedule(new QueueEntity(model,"Queue",true,lastCheckPatientQueue),warmUp);
		reset=new ResetEvent(model,"Queue Reset",true);
		reset.schedule(new QueueEntity(model,"Queue",true,allPatientsQueue),warmUp);
		reset=new ResetEvent(model,"Queue Reset",true);
		reset.schedule(new QueueEntity(model,"Queue",true,busyDoctorQueue),warmUp);
		reset=new ResetEvent(model,"Queue Reset",true);
		reset.schedule(new QueueEntity(model,"Queue",true,freeDoctorQueue),warmUp);
		reset=new ResetEvent(model,"Queue Reset",true);
		reset.schedule(new QueueEntity(model,"Queue",true,inTreatmentQueue),warmUp);
		
		emergencyExperiment.stop(new SimTime(simulationTime));
		emergencyExperiment.start();
		emergencyExperiment.report();
		emergencyExperiment.finish();

		
		// TODO rm output
		System.out.println("\nunder five minutes: " + underFive);
		System.out
				.println("avg waiting patients, rest: "
						+ (lastCheckPatientQueue.averageLength() + lowPriorityPatientQueue
								.averageLength()));
		System.out.println("max wating patients, rest: "
				+ (lastCheckPatientQueue.maxLength() + lowPriorityPatientQueue
						.maxLength()));
		System.out
				.println("Zeros: "
						+ (highPriorityPatientQueue.zeroWaits()
								+ lastCheckPatientQueue.zeroWaits() + lowPriorityPatientQueue
									.zeroWaits()));
		System.out.println(allPatientsQueue.size());
		SimTime[] simTimeArr = new SimTime[allPatientsQueue.size()];
		int count=0;
		for (int i = 0; i < simTimeArr.length; i++) {
			PatientEntity patient=allPatientsQueue.first();	
			if (SimTime.isLarger(patient.arrivalTime, warmUp)){
				//problem: there are patients with no departure time at end of simulation
				// approach: just set the simulation end time as departure time but: problem we have to talk about^^
				SimTime tmp=patient.getStay();
				if (tmp==null){
					tmp=SimTime.diff(new SimTime(simulationTime),patient.arrivalTime);
				}			
				simTimeArr[count] = tmp;
				count++;
			}
			allPatientsQueue.removeFirst();
		}
	
		SimTime temp;	
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < count - i - 1; j++) {
				if (SimTime.isLarger(simTimeArr[j], simTimeArr[j + 1])) {
					temp = simTimeArr[j];
					simTimeArr[j] = simTimeArr[j + 1];
					simTimeArr[j + 1] = temp;
				}
			}
		}
		
		int n = (int) (count * 0.9);
		double quantile;
		if (n==count-1){
			quantile=  0.5 * SimTime.add(simTimeArr[n], simTimeArr[n-1]).getTimeValue();
		}else{
			quantile=  0.5 * SimTime.add(simTimeArr[n], simTimeArr[n+1]).getTimeValue();	
		}
		 
		System.out.println("Quantile: " + quantile);
		
	}
}
