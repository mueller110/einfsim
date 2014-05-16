import desmoj.core.dist.RealDistExponential;
import desmoj.core.dist.RealDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
public class EmergencyRoomModel extends Model {
	private int numberOfDoctors =2;
	private static int simulationTime=50000;
	public EmergencyRoomModel(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
		// TODO Auto-generated constructor stub
	}

	private RealDistExponential patientArrivalTime;

	public double getPatientArrivalTime() {
		return patientArrivalTime.sample();
	}

	private RealDistUniform treatmentTime[] = new RealDistUniform[4];

	public double getTreatmentTime(int cprio) {
		return treatmentTime[cprio - 1].sample();
	}

	protected Queue<PatientEntity> highPriorityPatientQueue;
	protected Queue<PatientEntity> lowPriorityPatientQueue;
	protected Queue<PatientEntity> lastCheckPatientQueue;
	protected Queue<DoctorEntity> freeDoctorQueue;
	protected Queue<DoctorEntity> busyDoctorQueue;

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
				"arrival time interval",20, true, true);

		// avoid negativ arrival time
		patientArrivalTime.setNonNegative(true);

		// kundenAnkunftsZeit.setSeed(1234567890);

		// es muss gelten: bedienZeitPrio1<bedienZeitPrio3 und
		// bedienZeitPrio2_1< bedienZeitPrio2_3
		treatmentTime[0] = new RealDistUniform(this, "treatment time interval (Priority 1)", 10,
				30, true, true); // bedienZeitPrio 1
		treatmentTime[1] = new RealDistUniform(this, "treatment time interval (Priority 2_1)", 20,
				10, true, true); // bedienZeitPrio 2_1
		treatmentTime[3] = new RealDistUniform(this, "treatment time interval (Priority 2_3)", 40,
				20, true, true); // bedienZeitPrio 2_3
		treatmentTime[2] = new RealDistUniform(this, "treatment time inverval (Priority 3)", 50,
				120, true, true); // bedienZeitPrio 3

		highPriorityPatientQueue = new Queue<PatientEntity>(this,
				"High-Priority Queue", true, true);
		lowPriorityPatientQueue = new Queue<PatientEntity>(this,
				"Low-Priority Queue", true, true);

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
		Experiment schalterExperiment = new Experiment("Emergency-Room");

		// neues Modell erzeugen
		// Par 1: null markiert main model, sonst Mastermodell angeben
		EmergencyRoomModel model = new EmergencyRoomModel(null,
				"Emergency-Room Model", true, true);

		// Modell mit Experiment verbinden
		model.connectToExperiment(schalterExperiment);

		// Intervall fuer trace/debug
		schalterExperiment.tracePeriod(new SimTime(0.0), new SimTime(simulationTime));
		schalterExperiment.debugPeriod(new SimTime(0.0), new SimTime(simulationTime));

		// Ende der Simulation setzen
		// -> hier 4 Stunden (= 240 min)
		schalterExperiment.stop(new SimTime(simulationTime));

		// Experiment zur Zeit 0.0 starten
		schalterExperiment.start();

		// -> Simulation laeuft bis Abbruchkriterium erreicht ist
		// -> danach geht es hier weiter

		// Report generieren
		schalterExperiment.report();
		
		// Ausgabekanaele schliessen, allfaellige threads beenden
		schalterExperiment.finish();
		

	}
}
