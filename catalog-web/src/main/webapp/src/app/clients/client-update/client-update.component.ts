import { Component, OnInit } from '@angular/core';
import {ClientService} from "../shared/client.service";
import {ActivatedRoute} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Location} from "@angular/common";

@Component({
  selector: 'app-client-update',
  templateUrl: './client-update.component.html',
  styleUrls: ['./client-update.component.css']
})
export class ClientUpdateComponent implements OnInit {

  serialNumber:string;
  name:string;
  constructor(private clientService:ClientService, private route: ActivatedRoute,private location: Location) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.clientService.showDetails(id).subscribe(c=>{
        this.name=c.name;
        this.serialNumber=c.serialNumber;
      }
    )
  }

  onSubmit(form:NgForm){
    console.log("updateClient",form.value);
    var newValues = form.value;
    var name = newValues['name'];
    var serialNumber = newValues['serialNumber'];
    this.clientService.updateClient({
      id:parseInt(this.route.snapshot.paramMap.get('id'),10),
      serialNumber,
      name
    }).subscribe(c=>console.log("updated clieny: ",c));
    this.location.back();
  }

}


