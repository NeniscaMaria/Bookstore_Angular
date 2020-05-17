import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ClientNewComponent} from "./client-new/client-new.component";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  constructor(private dialog:MatDialog) { }

  ngOnInit(): void {
  }

  addNewClient(){
    console.log("add new client button clicked");
    this.openDialog();
  }
  private openDialog(): void {
    const dialogRef = this.dialog.open(ClientNewComponent, {
      width: '20em'
    });
    dialogRef.afterClosed().subscribe(c=>window.location.reload());

  }
}
