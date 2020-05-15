import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Client} from "./client.model";

@Injectable()
export class ClientService{
  private clientURL = 'http://localhost:8080/api/clients';
  private sortURL = "http://localhost:8080/api/sort";

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Client[]>{;
    return this.httpClient.get<Array<Client>>(this.clientURL);
  }

  saveClient(client: Client): Observable<Client>{
    console.log("saveClient",client);
    return this.httpClient.post<Client>(this.clientURL,client);
  }

  deleteClient(id: number): Observable<Client>{
    console.log("deleteClient",id);
    return this.httpClient.delete<Client>(this.clientURL+"/"+id);
  }
}
