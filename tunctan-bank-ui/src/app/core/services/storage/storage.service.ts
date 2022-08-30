import {Inject, Injectable} from "@angular/core";
import {StorageKey} from "../../enums/storage-key.enum";

@Injectable()
export class StorageService {


  constructor() {
  }


  set(key: StorageKey = StorageKey.TOKEN, value: any): any {
    localStorage.setItem(key, JSON.stringify(value));
  }

  get(key: StorageKey): any {
    const value = localStorage.getItem(key);
    if (value == null || value == "") {
      return null;
      // throw new Error("Not found value by key:" + key);
    }
    return JSON.parse(value);
  }

  remove(key: StorageKey): void {
    localStorage.removeItem(key);
  }

  clearAll() {
    localStorage.clear();
  }


}
