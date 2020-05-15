import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BooksComponent} from "./books/books.component";
import {ClientsComponent} from "./clients/clients.component";
import {PurchasesComponent} from "./purchases/purchases.component";
import {ClientNewComponent} from "./clients/client-new/client-new.component";
import {BookNewComponent} from "./books/book-new/book-new.component";
import {BookUpdateComponent} from "./books/book-update/book-update.component";


const routes: Routes = [
  // { path: '', redirectTo: '/home', pathMatch: 'full' },
  {path: 'books', component: BooksComponent},
  {path: 'clients', component: ClientsComponent},
  {path: 'purchases', component: PurchasesComponent},
  {path:'client/new', component: ClientNewComponent},
  {path:'book/new', component: BookNewComponent},
  {path:'book/update/:id', component: BookUpdateComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
