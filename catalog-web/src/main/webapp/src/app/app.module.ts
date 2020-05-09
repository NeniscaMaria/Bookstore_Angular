import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BooksComponent } from './books/books.component';
import { BooksListComponent } from './books/books-list/books-list.component';
import {RouterModule} from "@angular/router";
import { HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {BookService} from "./books/shared/book.service";

@NgModule({
  declarations: [
    AppComponent,
    BooksComponent,
    BooksListComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [BookService],
  bootstrap: [AppComponent]
})
export class AppModule { }
