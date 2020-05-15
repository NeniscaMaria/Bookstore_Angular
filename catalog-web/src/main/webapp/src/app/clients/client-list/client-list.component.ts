import { Component, OnInit } from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  clients: Client[];
  constructor(private clientService: ClientService, private location:Location) { }

  ngOnInit(): void {
    this.clientService.getClients().subscribe(clients=>this.clients=clients);
  }

  deleteClient(id:number){
    console.log("deleting client", id);
    this.clientService.deleteClient(id)
      .subscribe(c=>console.log("deleted client: ",c));
    window.location.reload();
  }

}
