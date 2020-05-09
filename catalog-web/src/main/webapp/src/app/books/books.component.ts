import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-books',
  template: '<app-books-list></app-books-list>\n',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
