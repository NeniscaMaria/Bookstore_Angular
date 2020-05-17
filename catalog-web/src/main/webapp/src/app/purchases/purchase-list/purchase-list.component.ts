import {Component, OnInit, ViewChild} from '@angular/core';
import {PurchaseService} from "../shared/purchase.service";
import {ClientService} from "../../clients/shared/client.service";
import {Purchase} from "../shared/purchase.model";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {BehaviorSubject} from "rxjs";
import {SelectionModel} from "@angular/cdk/collections";
import {BookService} from "../../books/shared/book.service";
import {MatDialog} from "@angular/material/dialog";
import {DialogUpdateComponent} from "../dialog-update/dialog-update.component";
import {UpdatePurchaseBookComponent} from "../update-purchase-book/update-purchase-book.component";
import {UpdatePurchaseClientComponent} from "../update-purchase-client/update-purchase-client.component";
export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];
@Component({
  selector: 'app-purchase-list',
  templateUrl: './purchase-list.component.html',
  styleUrls: ['./purchase-list.component.css']
})
export class PurchaseListComponent implements OnInit {

  purchases : Purchase[];
  choice: string;
  clientID: number;
  bookID : number;
  displayedColumns: string[] = ['select','name','title'];
  dataSource : MatTableDataSource<Purchase>;
  selection = new SelectionModel<Purchase>(true, []);
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatTable,{static:true}) table: MatTable<Purchase>;

  constructor(private dialog:MatDialog,private purchaseService: PurchaseService,private clientService: ClientService, private bookService:BookService) {
    this.clientService.getClients().subscribe(clients=>{
      this.dataSource =  new MatTableDataSource();
      this.dataSource.sort = this.sort;
      this.purchases = [];
      clients.forEach((client)=>
        this.purchaseService.getPurchases(client.id)
          .subscribe(books =>{
            if(books.length!=0) {
              books.forEach((book) => {
                var purchase = new Purchase(client, book,client.name,book.title);
                this.purchases.push(purchase);
                this.dataSource.data = this.purchases;
              });
            }
          }));
    });

  }

  ngOnInit(): void {
  }

  applyFilter($event: KeyboardEvent) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  /** The label for the checkbox on the passed row */
  checkboxLabel(row?: Purchase): string {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.name + 1}`;
  }

  deletePurchase() {
    console.log("delete purchases: nr:",this.selection.selected.length);
    console.log(this.selection.selected);
    if(this.selection.selected.length==0)
      alert("Please select at least one purchase to delete.");
    else {
      this.selection.selected.forEach((purchase) => {
        this.purchases = this.purchases.filter(p => p != purchase);
        var client = purchase.client;
        var book = purchase.book;
        this.clientService.removeBookFromClient(client, book).subscribe(
          b => this.bookService.removeClientFromBook(book, client)
            .subscribe(c => {
              console.log("purchase removed");
              this.dataSource.data = this.purchases;
              this.table.renderRows();
            }));
      });
    }
  }

  updatePurchase() {
    console.log("update purchase");
    if(this.selection.selected.length==0)
      alert("Select one purchase to update");
    else{
      if(this.selection.selected.length!=1)
        alert("You can update one purchase at a time");
      else{
        this.openDialog();
      }
    }
  }

  private openDialog(): void {
    const dialogRef = this.dialog.open(DialogUpdateComponent, {
      width: '20em'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.choice = result;
      console.log(result);
      if(this.choice=='cancel')
        return;
      if(this.choice=='client'){
        this.openClientUpdateDialog();
      }
      if(this.choice=='book'){
        this.openBookUpdateDialog();
      }
    });
  }

  private openClientUpdateDialog() :void {
    console.log("Open client dialog");
    const dialogRef = this.dialog.open(UpdatePurchaseClientComponent, {
      width: '20em'
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.clientID = result;
      console.log(result);
      if(result!='cancel')
        this.updateClientPurchase();
    });
  }

  private updateClientPurchase() {
    console.log("update purchase client");
    var purchase = this.selection.selected[0];
    this.clientService.showDetails(this.clientID).subscribe(clientToAdd=>{
        this.bookService.removeClientFromBook(purchase.book,purchase.client).subscribe(book=>{
            this.bookService.addClientToBook(clientToAdd,purchase.book).subscribe(book2=>{
              purchase.client = clientToAdd;
              purchase.name = clientToAdd.name;
              this.dataSource.data = this.purchases;
              this.selection.selected.pop();
              this.table.renderRows();
            });
          });
      });
  }

  private openBookUpdateDialog() {
    console.log("Open book dialog");
    const dialogRef = this.dialog.open(UpdatePurchaseBookComponent, {
      width: '30em'
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.bookID = result;
      console.log(result);
      if(result!='cancel')
        this.updateBookPurchase();
    });
  }

  private updateBookPurchase() {
    console.log("update purchase book");
    var purchase = this.selection.selected[0];
    this.bookService.showDetails(this.bookID).subscribe(bookToAdd=>{
      this.clientService.removeBookFromClient(purchase.client,purchase.book).subscribe(client=>{
        this.clientService.addBookToClient(purchase.client,bookToAdd).subscribe(client2=>{
          purchase.book = bookToAdd;
          purchase.title = bookToAdd.title;
          this.dataSource.data = this.purchases;
          this.selection.selected.pop();
          this.table.renderRows();
        });
      });
    });
  }
}
