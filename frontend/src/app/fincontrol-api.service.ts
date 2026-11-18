import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

export type CategoryPurpose = 'Expenditure' | 'Revenue' | 'Both';
export type TransactionType = 'Expenditure' | 'Revenue';

export interface Customer {
  id: string;
  name: string;
  age: number;
}

export interface Category {
  id: string;
  description: string;
  purpose: CategoryPurpose;
}

export interface Transaction {
  id: string;
  description: string;
  transactionType: TransactionType;
  amount: number;
  customerName: string;
  categoryDescription: string;
  categoryPurpose: CategoryPurpose;
  customerId: string;
  categoryId: string;
}

export interface Balance {
  revenues: number;
  expenditures: number;
  balance: number;
}

export interface PersonBalance extends Balance {
  customerId: string;
  name: string;
}

export interface CategoryBalance extends Balance {
  categoryId: string;
  description: string;
  purpose: CategoryPurpose;
}

export interface BalanceReport<T> {
  people?: T[];
  categories?: T[];
  total: Balance;
}

@Injectable({ providedIn: 'root' })
export class FincontrolApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api';

  customers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.baseUrl}/customers`);
  }

  createCustomer(body: { name: string; age: number }): Observable<Customer> {
    return this.http.post<Customer>(`${this.baseUrl}/customers`, body);
  }

  updateCustomer(id: string, body: { name: string; age: number }): Observable<Customer> {
    return this.http.put<Customer>(`${this.baseUrl}/customers/${id}`, body);
  }

  deleteCustomer(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/customers/${id}`);
  }

  categories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories`);
  }

  createCategory(body: { description: string; purpose: CategoryPurpose }): Observable<Category> {
    return this.http.post<Category>(`${this.baseUrl}/categories`, body);
  }

  transactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.baseUrl}/transactions`);
  }

  createTransaction(body: {
    description: string;
    transactionType: TransactionType;
    amount: number;
    categoryId: string;
    customerId: string;
  }): Observable<Transaction> {
    return this.http.post<Transaction>(`${this.baseUrl}/transactions`, body);
  }

  peopleReport(): Observable<BalanceReport<PersonBalance>> {
    return this.http.get<BalanceReport<PersonBalance>>(`${this.baseUrl}/reports/people`);
  }

  categoriesReport(): Observable<BalanceReport<CategoryBalance>> {
    return this.http.get<BalanceReport<CategoryBalance>>(`${this.baseUrl}/reports/categories`);
  }
}
