import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BooksComponent } from './books/books.component';
import { BooksListComponent } from './books/books-list/books-list.component';
import {RouterModule} from "@angular/router";
import { HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BookService} from "./books/shared/book.service";
import { ClientsComponent } from './clients/clients.component';
import { PurchasesComponent } from './purchases/purchases.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import { PurchaseListComponent } from './purchases/purchase-list/purchase-list.component';
import {PurchaseService} from "./purchases/shared/purchase.service";
import { BookNewComponent } from './books/book-new/book-new.component';
import { ClientNewComponent } from './clients/client-new/client-new.component';
import { PurchaseNewComponent } from './purchases/purchase-new/purchase-new.component';
import { ClientDeleteComponent } from './clients/client-delete/client-delete.component';
import { BookUpdateComponent } from './books/book-update/book-update.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    BooksComponent,
    BooksListComponent,
    ClientsComponent,
    PurchasesComponent,
    ClientListComponent,
    PurchaseListComponent,
    BookNewComponent,
    ClientNewComponent,
    PurchaseNewComponent,
    ClientDeleteComponent,
    BookUpdateComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [BookService, ClientService, PurchaseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
