import { Address } from "./address";
import { Resource } from "./resource";
import { AuditDate } from "./auditDate";
import { AuditAllocation } from "./auditAllocation";

export class Audit { 

	public id: number;
	
	public jobId: number;

	public jobName: string; // use it when new Audit tab is added from job-view 
	
	public address: Address;
	
	public auditStatus: string;

	public auditName: string;
    
	public auditDates: AuditDate[] = [];

	selectedAuditDate: AuditDate; // required in audit allocation page while passing the date to pick list

	public auditAllocations: AuditAllocation[];

	public allocatedResources: Resource[] = [];

	public unAllocatedResources: Resource[] = [];

	public statusUpdatedBy: string;
    
	public paymentReceived: number;
    
	public createdTs: Date;
    
	public updatedTs: Date;

}