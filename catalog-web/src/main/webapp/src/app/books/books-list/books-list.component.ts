import { Component, OnInit } from '@angular/core';
import {BookService} from "../shared/book.service";
import {Book} from "../shared/book.model";

@Component({
  selector: 'app-books-list',
  styleUrls: ['./books-list.component.css'],
  template: `<h2>Books</h2>
  <ol class="books">
    <li *ngFor="let book of books "> title: {{book.title}} author: {{book.author}} year: {{book.year}}
      price: {{book.price}} in stock: {{book.inStock}}      </li>
  </ol>  `
})
export class BooksListComponent implements OnInit {
  books: Book[];
  constructor(private booksService: BookService) { }

  ngOnInit(): void {
    this.booksService.getBooks().subscribe(books=>this.books=books);
  }

}
