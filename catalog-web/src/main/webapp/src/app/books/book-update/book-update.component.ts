import { Component, OnInit } from '@angular/core';
import {BookService} from "../shared/book.service";
import {ActivatedRoute} from "@angular/router";
import {NgForm} from "@angular/forms";
import {Location} from "@angular/common";

@Component({
  selector: 'app-book-update',
  templateUrl: './book-update.component.html',
  styleUrls: ['./book-update.component.css']
})
export class BookUpdateComponent implements OnInit {

  title:string;
  author:string;
  price:number;
  year:number;
  inStock:number;
  serialNumber:string;

  constructor(private bookService:BookService, private route: ActivatedRoute,private location: Location) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.bookService.showDetails(id).subscribe(book=>{
        this.title=book.title;
        this.author=book.author;
        this.price=book.price;
        this.year=book.year;
        this.inStock=book.inStock;
        this.serialNumber=book.serialNumber;
      }
    )
  }
  onSubmit(form:NgForm){
    console.log("updateBook:",form.value);
    var newValues = form.value;
    var title = newValues['title'];
    var serialNumber = newValues['serialNumber'];
    var author = newValues['author'];
    var year = newValues['year'];
    var price = newValues['price'];
    var inStock = newValues['inStock'];
    this.bookService.updateBook({
      id:parseInt(this.route.snapshot.paramMap.get('id'),10),
      serialNumber,
      title,
      author,
      year,
      price,
      inStock,
    }).subscribe(book=>console.log("updated book: ",book));
    this.location.back();
  }

}
