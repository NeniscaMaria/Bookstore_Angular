import { Component, OnInit } from '@angular/core';
import {BookService} from "../shared/book.service";
import {Book} from "../shared/book.model";
import {Location} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-books-list',
  styleUrls: ['./books-list.component.css'],
  template: `<h2>Books</h2>
  <input class = 'searchField' [(ngModel)]="searchText" placeholder="Search by title..">
  <ol class="books">
    <li *ngFor="let book of books | filter: searchText | paginate: {itemsPerPage: 5, currentPage: p}" routerLink="/book/update/{{book.id}}" >
      {{book.title}}
      <button class="deletebtn" (click)="deleteBook(book.id)">
        Delete
      </button>
    </li>
  </ol>
  <pagination-controls (pageChange)="p = $event"></pagination-controls>`
})
export class BooksListComponent implements OnInit {
  books: Book[];
  p: number = 1;
  searchText: string;
  constructor(private booksService: BookService,private location: Location,private router: Router) { }

  ngOnInit(): void {
    this.booksService.getBooks().subscribe(books=>this.books=books);
  }

  deleteBook(id:number){
    console.log("deleting book", id);
    this.booksService.deleteBook(id)
      .subscribe(book=>console.log("deleted book: ",book));
    window.location.reload();
  }
}
