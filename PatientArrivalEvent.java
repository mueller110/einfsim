import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

@SuppressWarnings("deprecation")
public class PatientArrivalEvent extends Event<PatientEntity> {
	static int oneCount = 0;
	static int twoCount = 0;
	static int threeCount = 0;
	static int total = 0;
	private EmergencyRoomModel model;

	public PatientArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		Queue<PatientEntity> queue;
		int cPriority = patient.getPriority();
		total++;
		if (cPriority == 1) {
			if (!patient.treatementInterrupted){
				patient.arrivalTime = model.currentTime();
			}else{}
			queue = model.lowPriorityPatientQueue;
		} else if (cPriority == 2) {
			patient.start2 = model.currentTime();
			queue = model.lastCheckPatientQueue;
		} else {
			patient.arrivalTime = model.currentTime();
			queue = model.highPriorityPatientQueue;
		}
		queue.insert(patient);

		// there is a free doctor?! -> no waiting
		if (!model.freeDoctorQueue.isEmpty()) {
			DoctorEntity doctor = (DoctorEntity) model.freeDoctorQueue.first();
			model.freeDoctorQueue.remove(doctor);
			model.busyDoctorQueue.insert(doctor);
			queue.remove(patient);
			if (cPriority == 2) {
				patient.departureTime = model.currentTime();
				patient.waitingTime = SimTime.add(
						SimTime.diff(patient.end, patient.arrivalTime),
						SimTime.diff(patient.departureTime, patient.start2));
				if (SimTime.isSmallerOrEqual(patient.waitingTime, new SimTime(
						5.0))) {
					model.underFive++;
				}
			} else {
				patient.end = model.currentTime();
			}

			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);
			treatmentTerm.schedule(patient,
					new SimTime(model.getTreatmentTime(patient.getPriority())));
			
			patient.treatmentTermination = treatmentTerm;
			
			EmergencyRoomModel.treatedPantientQueue.insert(patient);

		} else {
			if(patient.getPriority()==3){				
				
				PatientEntity tmpPatient;
				PatientEntity firstPatient = model.treatedPantientQueue.first();
				
				do{
					tmpPatient = model.treatedPantientQueue.first();
					model.treatedPantientQueue.remove(tmpPatient);
					
					
					
					model.treatedPantientQueue.insert(tmpPatient);
				}while(firstPatient!=model.treatedPantientQueue.first() || tmpPatient.getPriority()!=1);
				
				if(tmpPatient.getPriority()==1){
					model.treatedPantientQueue.remove(tmpPatient);
					tmpPatient.treatementInterrupted=true;
					
					PatientArrivalEvent arrival = new PatientArrivalEvent(model,
							"Arrival of Patient", true);
					arrival.schedule(tmpPatient, new SimTime(0.0)); // instant arrival
				}
			}
		}

		
	}

}
