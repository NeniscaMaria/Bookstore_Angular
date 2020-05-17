import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Book} from "./book.model";

class Client {
}

@Injectable()
export class BookService{
  private bookURL = 'http://localhost:8080/api/books';
  private sortURL = "http://localhost:8080/api/sort";

  constructor(private httpClient: HttpClient) {
  }

  getBooks(): Observable<Book[]>{;
    return this.httpClient.get<Array<Book>>(this.bookURL);
  }

  saveBook(book: { serialNumber: string; year: string; author: string; price: string; inStock: string; id: number; title: string, clients:Client[] }): Observable<Book>{
    console.log("save book",book);
    return this.httpClient.post<Book>(this.bookURL,book);
  }

  deleteBook(id:number) : Observable<Book>{
    console.log("delete book",id);
    return this.httpClient.delete<Book>(this.bookURL+"/"+id.toString(10))
  }

  showDetails(id:number) : Observable<Book>{
    console.log("showDetails",id);
    return this.httpClient.get<Book>(this.bookURL + "/" + id);
  }

  updateBook(book: { serialNumber: any; year: any; author: any; price: any; inStock: any; id: number; title: any }): Observable<Book>{
    console.log("update book",book);
    return this.httpClient.put<Book>(this.bookURL+"/"+book.id,book);
  }

  addClientToBook(c: Client, book: Book) {
    console.log("add client to book",c,book);
    return this.httpClient.put<Book>(this.bookURL+"/purchase/"+book.id,c);
  }

  removeClientFromBook(book: Book, client: Client) : Observable<Book>{
    console.log("removeClientFromBook ",book,client);
    return this.httpClient.put<Book>(this.bookURL+"/purchase/remove/"+book.id,client);

  }
}
