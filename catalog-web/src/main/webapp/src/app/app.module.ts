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
import { ClientDeleteComponent } from './clients/client-delete/client-delete.component';
import { BookUpdateComponent } from './books/book-update/book-update.component';
import {FormsModule} from "@angular/forms";
import { ClientUpdateComponent } from './clients/client-update/client-update.component';
import {NgxPaginationModule} from "ngx-pagination";
import { FilterPipe} from './clients/filter/filter.pipe';
import {FilterTitlePipe} from "./books/filter/filterTitle.pipe";
import { PurchaseNewComponent } from './purchases/purchase-new/purchase-new.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatListModule} from "@angular/material/list";
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatButtonModule} from "@angular/material/button";
import { DialogUpdateComponent } from './purchases/dialog-update/dialog-update.component';
import {MatDialogModule} from "@angular/material/dialog";
import { UpdatePurchaseClientComponent } from './purchases/update-purchase-client/update-purchase-client.component';
import { UpdatePurchaseBookComponent } from './purchases/update-purchase-book/update-purchase-book.component';
import {MatPaginatorModule} from "@angular/material/paginator";

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
    ClientDeleteComponent,
    BookUpdateComponent,
    ClientUpdateComponent,
    FilterPipe,
    FilterTitlePipe,
    PurchaseNewComponent,
    DialogUpdateComponent,
    UpdatePurchaseClientComponent,
    UpdatePurchaseBookComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    NgxPaginationModule,
    BrowserAnimationsModule,
    MatListModule,
    MatTableModule,
    MatSortModule,
    MatFormFieldModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,
    MatDialogModule,
    MatPaginatorModule
  ],
  providers: [BookService, ClientService, PurchaseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
