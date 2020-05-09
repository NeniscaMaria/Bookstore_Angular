import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "./book.model";

@Injectable()
export class BookService{
  private bookURL = 'http://localhost:8080/api/books';
  private sortURL = "http://localhost:8080/api/sort";

  constructor(private httpClient: HttpClient) {
  }

  getBooks(): Observable<Book[]>{;
    return this.httpClient.get<Array<Book>>(this.bookURL);
  }
}
