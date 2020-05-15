import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-books',
  template: '<button (click)="addNewBook()">Add new book </button>' +
    '<app-books-list></app-books-list>\n'    ,
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewBook(){
    console.log("add new book button clicked");
    this.router.navigate(["book/new"]);
  }
}
