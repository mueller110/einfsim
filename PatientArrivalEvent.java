import java.util.Iterator;

import desmoj.core.simulator.Event;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.SimTime;

/**
 * The patient arrival event represents the arrival of a patient
 */

public class PatientArrivalEvent extends Event<PatientEntity> {

	static int total = 0;
	private EmergencyRoomModel model;

	/**
	 * constructor
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public PatientArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (EmergencyRoomModel) owner;
	}

	@Override
	public void eventRoutine(PatientEntity patient) {
		Queue<PatientEntity> queue;
		int cPriority = patient.getPriority();
		total++;
		// insert the patient into its queue according to its priority
		if (cPriority == 1) {
			if (!patient.treatementInterrupted) {
				patient.arrivalTime = new SimTime(model.currentTime());
				//insert patient into allPatientsQueue to mantain reference, 
				//when the patient leaves the hospital
				model.allPatientsQueue.insert(patient);
			}
			queue = model.lowPriorityPatientQueue;
		} else if (cPriority == 2) {
			queue = model.lastCheckPatientQueue;
			//patient has priority 3? shedule a death event
		} else {
			patient.arrivalTime = new SimTime(model.currentTime());
			model.allPatientsQueue.insert(patient);
			queue = model.highPriorityPatientQueue;
			if (model.deathOfPatientsFlag) {
				PatientDeathEvent pde = new PatientDeathEvent(model, "Death",
						true);
				pde.schedule(patient, new SimTime(model.getDeathTime()));
				patient.deathEvent = pde;
			}
		}
		//set start of waiting to currentTime
		if (patient.waitingTime == null)
			patient.waitingTime = new SimTime(0.0);
		
		
		//if the treatment of the patient was interrupted insert him into the queue at the first position
		//reason: closer to reallity!
		if (patient.treatementInterrupted) {
			if (queue.isEmpty())
				queue.insert(patient);
			else
				queue.insertBefore(patient, queue.first());
		} else {
			queue.insert(patient);
		}
		//is there a free doctor?=> patient goes to "that" doctor
		if (!model.freeDoctorQueue.isEmpty()) {
			DoctorEntity doctor = (DoctorEntity) model.freeDoctorQueue.first();
			model.freeDoctorQueue.remove(doctor);
			model.busyDoctorQueue.insert(doctor);
			queue.remove(patient);
			if (patient.getPriority() == 3 && model.deathOfPatientsFlag)
				patient.deathEvent.cancel();
			patient.treatmentStart = new SimTime(model.currentTime());
			TreatmentTermination treatmentTerm = new TreatmentTermination(
					model, "End of Treatment", true);
			//was the patient interrupted?
		
			if (!patient.treatementInterrupted) {
				patient.treatmentDuration = new SimTime(
						model.getTreatmentTime(patient.getPriority()));
				treatmentTerm.schedule(patient, new SimTime(
						patient.treatmentDuration));
			//yes? continue with the rest of its total treatment time 
			} else {
				patient.treatmentDuration = new SimTime(patient.rest);
				treatmentTerm.schedule(patient, new SimTime(patient.rest));
			}
			patient.treatementInterrupted = false;
			patient.treatmentTermination = treatmentTerm;
			EmergencyRoomModel.inTreatmentQueue.insert(patient);
			patient.end = model.currentTime();
			//no free doctor
			//if the new patient is a patient with priority 3 it can interrupt 
			//the treatment of an priority 1 patient
		} else {
			if (patient.getPriority() == 3 && EmergencyRoomModel.prio3kicks1) {
				PatientEntity tmpPatient = null;
				boolean prio1Found = false;
				//look for an priority 1 patient that can be removed from the doctor
				Iterator<PatientEntity> it = model.inTreatmentQueue.iterator();
				while (!prio1Found && it.hasNext()) {
					tmpPatient = it.next();
					if (tmpPatient.getPriority() == 1) {
						prio1Found = true;
					}
				}

				//remove this priority 1 patient i.e. let him arrive again with the interrupted flag set
				//calculate the patients rest treatment time. 
				if (prio1Found) {
					tmpPatient.treatementInterrupted = true;
					SimTime drtime = SimTime.diff(model.currentTime(),
							tmpPatient.end);
					SimTime absolut = SimTime.add(tmpPatient.end,
							tmpPatient.treatmentDuration);
					tmpPatient.rest = SimTime.diff(absolut,
							SimTime.add(tmpPatient.end, drtime));

					tmpPatient.treatmentTermination.cancel();
					queue.remove(patient);
					model.inTreatmentQueue.remove(tmpPatient);
					model.inTreatmentQueue.insert(patient);
					tmpPatient.treatementInterrupted = true;
					PatientArrivalEvent arrival = new PatientArrivalEvent(
							model, "Reschedule", true);
					arrival.schedule(tmpPatient, new SimTime(0.0));
					TreatmentTermination te = new TreatmentTermination(model,
							"3 vor 1", true);
					patient.treatmentDuration = new SimTime(
							model.getTreatmentTime(patient.getPriority()));
					te.schedule(patient, new SimTime(patient.treatmentDuration));
					if (model.deathOfPatientsFlag) {
						patient.deathEvent.cancel();
					}
				} else {
					patient.start = model.currentTime();
					patient.isZero = false;
				}
			} else {
				patient.start = model.currentTime();
				patient.isZero = false;
			}
		}

	}

	
}
