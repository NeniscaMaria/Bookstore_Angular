import { Component, OnInit } from '@angular/core';
import {Purchase} from "../shared/purchase.model";
import {PurchaseService} from "../shared/purchase.service";

@Component({
  selector: 'app-purchase-list',
  templateUrl: './purchase-list.component.html',
  styleUrls: ['./purchase-list.component.css']
})
export class PurchaseListComponent implements OnInit {

  purchases: Purchase[];

  constructor(private purchaseService: PurchaseService) { }

  ngOnInit(): void {
    this.purchaseService.getPurchases().subscribe(purchases=>this.purchases=purchases);
  }

}
