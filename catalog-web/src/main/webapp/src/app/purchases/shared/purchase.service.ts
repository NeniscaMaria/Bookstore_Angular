import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Purchase} from "./purchase.model";

@Injectable()
export class PurchaseService{
  private purchaseURL = 'http://localhost:8080/api/purchases';
  private sortURL = "http://localhost:8080/api/sort";

  constructor(private httpClient: HttpClient) {
  }

  getPurchases(): Observable<Purchase[]>{;
    return this.httpClient.get<Array<Purchase>>(this.purchaseURL);
  }
}
