import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Purchase} from "./purchase.model";
import {Book} from "../../books/shared/book.model";
import {Client} from "../../clients/shared/client.model";

@Injectable()
export class PurchaseService{
  private purchaseURL = 'http://localhost:8080/api/clients/purchase/get';
  private sortURL = "http://localhost:8080/api/sort";

  constructor(private httpClient: HttpClient) {
  }

  //function that return a list of books bought by the client with ID id
  getPurchases(id:number): Observable<Array<Book>>{
    return this.httpClient.put<Array<Book>>(this.purchaseURL+"/"+id,null);
  }

}
