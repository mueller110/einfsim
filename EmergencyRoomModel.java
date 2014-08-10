import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import desmoj.core.dist.RealDistExponential;
import desmoj.core.dist.RealDistUniform;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

/**
 * The emergency room model represents the model.
 */
public class EmergencyRoomModel extends Model {
	// how many times will be run
	public static int runs = 1;
	// the total count of the zeros (= non-waiting patients)
	public static int totalZeros = 0;
	// the count of the zeros for 1 run
	public static int zeros = 0;
	// patient count
	public static int allPatients = 0;
	// the number of working doctors
	public static int numberOfDoctors = 2;
	// the simulation time (minutes) (28800 = 20 days)
	public static int simulationTime = 28800; // 31680
	// interval used for the distribution of the patients to arrive
	public static int arrivalTime = 40;
	// the number of patients which waited less than five minutes
	public static int underFive = 0;
	// the number of deaths (of priority 3 patients)
	public static int deaths = 0;
	// quantile
	public static double quantile = 0;
	public static int quantileCount;
	// the distributions
	public static int dist1Min, dist1Max, dist2Min, dist2Max, dist3Min,
			dist3Max, deathOfPatientsMin, deathOfPatientsMax;
	// intial phase flag
	public static boolean initialPhaseFlag = false;
	// death of patients flag
	public static boolean deathOfPatientsFlag = false;
	// priority 3 patients can kick priority 1 patients flag
	public static boolean prio3kicks1 = false;

	public static SimTime warmUp;

	/**
	 * constructor
	 * 
	 * @param owner
	 * @param name
	 * @param showInReport
	 * @param showInTrace
	 */
	public EmergencyRoomModel(Model owner, String name, boolean showInReport,
			boolean showInTrace) {
		super(owner, name, showInReport, showInTrace);
	}

	private RealDistExponential patientArrivalTime;

	/**
	 * @return the arrival time for the patient
	 */
	public double getPatientArrivalTime() {
		return patientArrivalTime.sample();
	}

	private RealDistUniform treatmentTime[] = new RealDistUniform[3];
	private RealDistUniform deathTime;

	/**
	 * 
	 * @param cprio
	 *            - the priority of the patient
	 * @return the treatement time of the patient
	 */
	public double getTreatmentTime(int cprio) {
		return treatmentTime[cprio - 1].sample();
	}

	/**
	 * 
	 * @return the time the patient dies (priority 3)
	 */
	public double getDeathTime() {
		return deathTime.sample();
	}

	// the queues
	// priority 3 patients queue
	public static Queue<PatientEntity> highPriorityPatientQueue;
	// priority 1 patients queue
	public static Queue<PatientEntity> lowPriorityPatientQueue;
	// priority 2 patients queue
	public static Queue<PatientEntity> lastCheckPatientQueue;
	// all patients queue
	public static Queue<PatientEntity> allPatientsQueue;
	// available/free doctors queue
	public static Queue<DoctorEntity> freeDoctorQueue;
	// occupied/busy doctors queue
	public static Queue<DoctorEntity> busyDoctorQueue;
	// currently treated patients queue
	public static Queue<PatientEntity> inTreatmentQueue;

	/**
	 * @return the description of the model
	 */
	public String description() {
		return "TODO: Emergency Department Description";
	}

	/**
	 * create a new patient and set its arrival time
	 */
	public void doInitialSchedules() {
		NewPatientEvent firstPatient = new NewPatientEvent(this,
				"Creation of Patient", true);
		firstPatient.schedule(new SimTime(getPatientArrivalTime()));
	}

	/**
	 * intitialise every needed component
	 */
	public void init() {
		underFive = 0;
		deaths = 0;
		zeros = 0;
		quantile = 0;
		// new patient every 40 mins (average)
		patientArrivalTime = new RealDistExponential(this,
				"arrival time interval", arrivalTime, true, true);

		patientArrivalTime.setNonNegative(true);

		// es muss gelten: bedienZeitPrio1 < bedienZeitPrio3 und
		// bedienZeitPrio2_1< bedienZeitPrio2_3
		treatmentTime[0] = new RealDistUniform(this,
				"treatment time interval (Priority 1)", dist1Min, dist1Max,
				true, true); // bedienZeitPrio
	
		treatmentTime[1] = new RealDistUniform(this,
				"treatment time interval (Priority 2)", dist2Min, dist2Max,
				true, true); // bedienZeitPrio
		
		treatmentTime[2] = new RealDistUniform(this,
				"treatment time inverval (Priority 3)", dist3Min, dist3Max,
				true, true); // bedienZeitPrio

		if (deathOfPatientsFlag) {
			deathTime = new RealDistUniform(this,
					"Time remaining until treatement (Priority3)",
					deathOfPatientsMin, deathOfPatientsMax, true, true);
		}

		highPriorityPatientQueue = new Queue<PatientEntity>(this,
				"High-Priority Queue", true, true);
		lowPriorityPatientQueue = new Queue<PatientEntity>(this,
				"Low-Priority Queue", true, true);

		allPatientsQueue = new Queue<PatientEntity>(this, "Statistic", false,
				false);

		inTreatmentQueue = new Queue<PatientEntity>(this, "Patient with Dr.",
				false, true);

		// for priority 2 patients
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

	/**
	 * runs the simulation
	 */
	
	public static void runSimulation() {
		int totalUnderFive = 0;
		int overallDeaths = 0;
		int P3MaxWSum = 0;
		double P3AvgWSum = 0;
		int P12MaxWSum = 0;
		double P12AvgWSum = 0;
		double P3AvgWaitingTime = 0;
		double P12AvgWaitingTime = 0;
		int P3Count = 0;
		quantile = 0;
		double overallQuantile = 0;
		quantileCount = 0;
		totalZeros = 0;
		zeros = 0;
		allPatients = 0;

		// run as many simulations as runs is set to
		for (int r = 0; r < runs; r++) {

			Experiment emergencyExperiment = new Experiment("Emergency-Room");
			EmergencyRoomModel model = new EmergencyRoomModel(null,
					"Emergency-Room Model", true, true);
			model.connectToExperiment(emergencyExperiment);
			emergencyExperiment.tracePeriod(new SimTime(0.0), new SimTime(
					simulationTime));
			emergencyExperiment.debugPeriod(new SimTime(0.0), new SimTime(
					simulationTime));
			if (initialPhaseFlag) {
				warmUp = new SimTime(2880.0);
				ResetEvent reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						highPriorityPatientQueue), warmUp);
				reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						lowPriorityPatientQueue), warmUp);
				reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						lastCheckPatientQueue), warmUp);
				reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						allPatientsQueue), warmUp);
				reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						busyDoctorQueue), warmUp);
				reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						freeDoctorQueue), warmUp);
				reset = new ResetEvent(model, "Queue Reset", true);
				reset.schedule(new QueueEntity(model, "Queue", true,
						inTreatmentQueue), warmUp);
			} else {
				warmUp = new SimTime(0);
			}
			emergencyExperiment.stop(new SimTime(simulationTime));
			emergencyExperiment.start();
			emergencyExperiment.report();
			emergencyExperiment.finish();
			
			// create and output statistics of simulation
			String fileContent = "";
			try {
				FileInputStream fstream = new FileInputStream(
						"Emergency-Room_report.html");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;

				while ((strLine = br.readLine()) != null)
					fileContent += strLine;
				in.close();
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
			}
			P3Count += highPriorityPatientQueue.getObservations();
			P3MaxWSum += highPriorityPatientQueue.maxLength();
			P12MaxWSum += (lowPriorityPatientQueue.maxLength() + lastCheckPatientQueue
					.maxLength());
			P3AvgWSum += highPriorityPatientQueue.averageLength();
			P12AvgWSum += (lowPriorityPatientQueue.averageLength() + lastCheckPatientQueue
					.averageLength());
			P3AvgWaitingTime += highPriorityPatientQueue.averageWaitTime()
					.getTimeAsDouble();
			P12AvgWaitingTime += (lowPriorityPatientQueue.averageWaitTime()
					.getTimeAsDouble() + lastCheckPatientQueue
					.averageWaitTime().getTimeAsDouble());
			fileContent += "<p>maximale Anzahl wartender akuter Notfaelle: "
					+ highPriorityPatientQueue.maxLength() + "<br>";
			fileContent += "mittlere Anzahl wartender akuter Notfaelle: "
					+ highPriorityPatientQueue.averageLength() + "<br>";
			fileContent += "maximale Anzahl wartender nicht akuter Notfaelle: "
					+ (lowPriorityPatientQueue.maxLength() + lastCheckPatientQueue
							.maxLength()) + "<br>";
			fileContent += "mittlere Anzahl wartender nicht akuter Notfaelle: "
					+ (lowPriorityPatientQueue.averageLength() + lastCheckPatientQueue
							.averageLength()) + "<br>";
			fileContent += "mittlere Wartezeit akuter Notfaelle: "
					+ highPriorityPatientQueue.averageWaitTime() + "<br>";
			fileContent += "mittlere Wartezeit nicht akuter Notfaelle: "
					+ ((lowPriorityPatientQueue.averageWaitTime()
							.getTimeAsDouble() + lastCheckPatientQueue
							.averageWaitTime().getTimeAsDouble()) / 2) + "<br>";

			int totalPatientCount = 0;
			SimTime[] simTimeArr = new SimTime[allPatientsQueue.size()];
			allPatients += allPatientsQueue.size();
			int patients = allPatientsQueue.size();
			int count = 0;

			for (int i = 0; i < simTimeArr.length; i++) {
				PatientEntity patient = allPatientsQueue.first();
				if (SimTime.isLarger(patient.arrivalTime, warmUp)) {
					SimTime tmp = patient.getStay();
					if (patient.isZero) {
						totalZeros++;
						zeros++;
					}
					if (SimTime.isSmallerOrEqual(patient.waitingTime,
							new SimTime(5.0))) {
						model.underFive++;
					}
					if (tmp == null) {
						tmp = SimTime.diff(new SimTime(simulationTime),
								patient.arrivalTime);
					}
					simTimeArr[count++] = tmp;

				}
				allPatientsQueue.removeFirst();
			}

			fileContent += "Anzahl der Patienten die nicht warten mussten(waehrend gesamten Aufenthalt in Notaufnahme): "
					+ (totalZeros / (double) count) * 100 + "%<br>";
			fileContent += "Anteil der Patienten, welche maximal 5 min warten muessen: "
					+ (underFive / (double) count) * 100 + "%<br>";

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

			if (count > 5) {
				if (n == count - 1) {
					quantile = 0.5 * SimTime.add(simTimeArr[n],
							simTimeArr[n - 1]).getTimeValue();
				} else {
					quantile = 0.5 * SimTime.add(simTimeArr[n],
							simTimeArr[n + 1]).getTimeValue();
				}
				quantileCount++;
			}

			if (deathOfPatientsFlag) {
				fileContent += "Tode: " + deaths + "<br>";

			}

			try {
				FileWriter fw = new FileWriter("Emergency-Room_report.html");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(fileContent + "</p></BODY></HTML>");
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Quantile: " + quantile);
			overallQuantile += quantile;
			System.out.println("Zeros: " + zeros);
			System.out.println("UnderFive: " + underFive);
			totalUnderFive += underFive;
			System.out.println("Deaths: " + deaths);
			overallDeaths += deaths;
			System.out.println("Patients: " + patients);
			System.out
					.println("Run: "
							+ (r + 1)
							+ " finished.\n-------------------------------------------------------------------\n");
		}
		String result = "";

		result += "\n-------------------------------------------------------------------\n";
		result += "Finished after " + runs + " runs:\n";

		result += "Patients: \n";
		System.out.println("\tPSum:\t" + allPatients);
		System.out.println("\tPAvg:\t" + ((double) allPatients / runs));
		result += "\tMaximale Anzahl wartender akuter Notfaelle:\t\t"
				+ ((double) P3MaxWSum / runs) + "\n";
		result += "\tMittlere Anzahl wartender akuter Notfaelle:\t\t"
				+ ((double) P3AvgWSum / runs) + "\n";
		result += "\tMaximale Anzahl wartender nicht akuter Notfaelle:\t"
				+ ((double) P12MaxWSum / runs) + "\n";
		result += "\tMittlere Anzahl wartender nicht akuter Notfaelle\t"
				+ ((double) P12AvgWSum / runs) + "\n";

		result += "WaitingTimeAvg:\n";
		result += "\tMittlere Wartezeit akuter Notfaelle:\t"
				+ (P3AvgWaitingTime / runs) + "\n";
		result += "\tMittlere Wartezeit nicht akuter Notfaelle:\t"
				+ (P12AvgWaitingTime / runs) + "\n";

		result += "Zeros:\n";
		result += "\tAnteil der Patienten die nicht warten müssen\t"
				+ (((double) totalZeros) / allPatients) * 100 + "\n";
		result += "\tAnteil der Patienten die nicht länger als 5 min warten mussten\t"
				+ (((double) totalUnderFive) / allPatients) * 100 + "\n";

		result += "Deaths: " + deathOfPatientsFlag + "\n";
		if (deathOfPatientsFlag)
			result += "\tDAnteil der verstorbenen Patienten:\t"
					+ ((double) overallDeaths / P3Count) * 100 + "\n";
		System.out.println(result);
		System.out.println("InitialPhase: " + initialPhaseFlag);
		if (initialPhaseFlag) {
			System.out.print("\tIPDur:\t" + warmUp + " ("
					+ ((double) warmUp.getTimeValue() / 1440) + " day");
			if (((double) warmUp.getTimeValue() / 1440) != 1) {
				System.out.print("s");
			}
			System.out.println(")");
		}
		System.out.println("Quantile:");
		System.out.println("\tQSum:\t" + overallQuantile);
		System.out.println("\tQAvg:\t"
				+ ((double) overallQuantile / quantileCount));
	}
}
