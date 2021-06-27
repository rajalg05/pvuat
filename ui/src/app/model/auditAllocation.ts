import { Audit } from "./audit";
import { AuditDate } from "./auditDate";
import { Resource } from "./resource";

export class AuditAllocation {
     public id: number;
     public audit: Audit;
     public auditDate: AuditDate;
     public resource: Resource;
     public allocatedAt: Date;
     public allocatedBy: String;
     public auditDay: number;
     constructor() {}
}