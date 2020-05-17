import { Component, OnInit } from '@angular/core';
import {BookService} from "../shared/book.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-book-new',
  templateUrl: './book-new.component.html',
  styleUrls: ['./book-new.component.css']
})
export class BookNewComponent implements OnInit {

  constructor(private bookService: BookService, private location: Location) { }

  ngOnInit(): void {
  }

  saveBook(serialNumber:string, title:string, author: string, year:string, price:string, inStock:string){
    console.log("saving book", serialNumber,title,author,year,price,inStock);
    this.bookService.saveBook({
      id:0,
      serialNumber,
      title,
      author,
      year,
      price,
      inStock,
      clients: []
    }).subscribe(book=>console.log("saved book: ",book));
    this.location.back();

  }
}
