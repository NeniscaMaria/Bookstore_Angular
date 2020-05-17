import {Client} from "../../clients/shared/client.model";
import {Book} from "../../books/shared/book.model";

//one client and a list of books for that client
export class Purchase{
  client: Client;
  book: Book;
  name:string;
  title: string;
  constructor(client: Client, book: Book,name:string,title:string) {
    this.client = client;
    this.book = book;
    this.name = name;
    this.title = title;
  }
}
