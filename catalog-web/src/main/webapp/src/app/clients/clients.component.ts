import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  addNewClient(){
    console.log("add new client button clicked");
    this.router.navigate(["client/new"]);
  }
}
