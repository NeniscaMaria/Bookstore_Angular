import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {BookNewComponent} from "./book-new/book-new.component";

@Component({
  selector: 'app-books',
  template: '<button (click)="addNewBook()">Add new book </button>' +
    '<app-books-list></app-books-list>\n'    ,
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  constructor(private dialog:MatDialog,private router: Router) { }

  ngOnInit(): void {
  }

  addNewBook(){
    console.log("add new book button clicked");
    //this.router.navigate(["book/new"]);
    this.openDialog();
  }
  private openDialog(): void {
    const dialogRef = this.dialog.open(BookNewComponent, {
      width: '20em'
    });
    dialogRef.afterClosed().subscribe(c=>window.location.reload());

  }
}
