import {Client} from "../../clients/shared/client.model";
import {Book} from "../../books/shared/book.model";

export class Purchase{
  id: number;
  client: Client;
  book: Book;
  nrBooks: number;
}
