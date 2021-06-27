
import { Associate } from "./associateMaster";
import { Audit } from "./audit";

export class Job {

	public id: number;

  	public associate: Associate;

	public audits: Audit[];
	  
	public jobName: string;
	
	public clientName: number;

	public frequencyOfAudit: string;

	public paymentType: string;

	public totalPayment: number;

	public resourcesNeeded: number;

	public createdTs: Date;

	public updatedTs: Date;

	public auditOrJob: string; // This is to identify if a job or audit tab is opened
  }