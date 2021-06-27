import { Address } from "./address";
import { BasicContactDetail } from "./BasicContactDetail";
import { KYC } from "./kyc";

export class Resource {
    public constructor() {} 

	public id: number;

	public allocated: string;

	public  basicContactDetail: BasicContactDetail ;  
	
	public  kyc: KYC;
	
	public  address: Address;
	
	public  dateOfBirth: Date;
	
	public  qualification: string;
	
	public  excelSkills: string;
	
	public  stockAuditExp: string;
	
	public  resourceType: string;

	public  bike: string;

	public  tlNonTl: string;

	public 	paymentType: string;

	public 	paymentAmount: number;

	public  createdTs; Date;
	
	public  updatedTs: Date;

    
}