import { Component, EventEmitter, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { PrimeNGConfig, SelectItem } from 'primeng/api';
import { Resource } from '../../model/resource';
import { ResourceService } from '../../service/resource.service';

@Component({
    selector: 'app-resource-view',
    templateUrl: './resource-view.component.html',
    styleUrls: ['./resource-view.component.css']
})
export class ResourceViewComponent implements OnInit, OnChanges, OnDestroy {

    resources: Resource[] = [];

    @Input() resource: Resource; // sent from resource-form on submit to resource-master which in turn sent via Input so update resource[] 

    sortOptions: SelectItem[];

    sortOrder: number;

    sortField: string;

    private subsriptionResource: any = null;

    @Output() openExistingResourceTabEmitter = new EventEmitter();

    constructor(private primengConfig: PrimeNGConfig,
        private resourceService: ResourceService) { }
    ngOnChanges(changes: SimpleChanges): void {
        if (this.resource != undefined && this.resource.basicContactDetail.firstName) { // if jobs array is empty and job added first time
            this.resources = [...this.resources, this.resource]; // update the Resource list tab when a new Resource is added in Resource form
        } else if (this.resource != undefined && this.resource.basicContactDetail.firstName && this.resources.length > 0) {
            let index: number = this.resources.findIndex(resource => resource.basicContactDetail.firstName == this.resource.basicContactDetail.firstName);
            if (index == -1)
                this.resources = [...this.resources, this.resource]; // update the Resource list tab when a new Resource is added in Resource form
            else {
                this.resources[index] = this.resource;
            }
        }

    }
    ngOnInit() {
        this.subsriptionResource = this.resourceService.getResources().subscribe(data => {
            console.log('resource = ', data);
            this.resources = data;
        },
            error => {
                console.log('error getResources : ', error)
            })

        this.sortOptions = [
            { label: 'Price High to Low', value: '!price' },
            { label: 'Price Low to High', value: 'price' }
        ];

        this.primengConfig.ripple = true;
    }

    onSortChange(event) {
        let value = event.value;

        if (value.indexOf('!') === 0) {
            this.sortOrder = -1;
            this.sortField = value.substring(1, value.length);
        }
        else {
            this.sortOrder = 1;
            this.sortField = value;
        }
    }

    openTab(resource: Resource) {
        this.openExistingResourceTabEmitter.emit(resource);
    }

    deleteResource(resource: Resource) {
        this.resources = this.resources.filter(o => o !== resource);
        this.resourceService.deleteResource(resource).subscribe(data => {
            console.log('data = ', data);
        });
    }

    ngOnDestroy() {
        if(this.subsriptionResource != undefined)
            this.subsriptionResource.unsubscribe();
    }
}
