import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import {
  BalanceReport,
  Category,
  CategoryBalance,
  CategoryPurpose,
  Customer,
  FincontrolApiService,
  PersonBalance,
  Transaction,
  TransactionType
} from './fincontrol-api.service';

type Section = 'overview' | 'people' | 'categories' | 'transactions';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  private readonly api = inject(FincontrolApiService);
  private readonly changeDetector = inject(ChangeDetectorRef);

  activeSection: Section = 'overview';
  customers: Customer[] = [];
  categories: Category[] = [];
  transactions: Transaction[] = [];
  peopleReport: BalanceReport<PersonBalance> | null = null;
  categoryReport: BalanceReport<CategoryBalance> | null = null;
  customerForm = { id: '', name: '', age: 18 };
  categoryForm = { description: '', purpose: 'Both' as CategoryPurpose };
  transactionForm = {
    description: '',
    transactionType: 'Expenditure' as TransactionType,
    amount: 0,
    categoryId: '',
    customerId: ''
  };
  errorMessage = '';
  successMessage = '';
  loading = false;

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.loading = true;
    forkJoin({
      customers: this.api.customers(),
      categories: this.api.categories(),
      transactions: this.api.transactions(),
      peopleReport: this.api.peopleReport(),
      categoryReport: this.api.categoriesReport()
    }).subscribe({
      next: (result) => {
        this.customers = result.customers;
        this.categories = result.categories;
        this.transactions = result.transactions;
        this.peopleReport = result.peopleReport;
        this.categoryReport = result.categoryReport;
        this.loading = false;
        this.changeDetector.markForCheck();
      },
      error: (error: HttpErrorResponse) => {
        this.loading = false;
        this.showError(error);
      }
    });
  }

  saveCustomer(): void {
    const body = { name: this.customerForm.name.trim(), age: Number(this.customerForm.age) };
    const request = this.customerForm.id
      ? this.api.updateCustomer(this.customerForm.id, body)
      : this.api.createCustomer(body);
    request.subscribe({
      next: () => {
        this.resetCustomerForm();
        this.showSuccess('Pessoa salva com sucesso.');
        this.refresh();
      },
      error: (error: HttpErrorResponse) => this.showError(error)
    });
  }

  editCustomer(customer: Customer): void {
    this.customerForm = { id: customer.id, name: customer.name, age: customer.age };
    this.activeSection = 'people';
    this.clearMessages();
  }

  deleteCustomer(customer: Customer): void {
    if (!confirm(`Excluir ${customer.name} e todas as suas transações?`)) return;
    this.api.deleteCustomer(customer.id).subscribe({
      next: () => {
        this.showSuccess('Pessoa e transações removidas.');
        this.refresh();
      },
      error: (error: HttpErrorResponse) => this.showError(error)
    });
  }

  saveCategory(): void {
    this.api.createCategory({
      description: this.categoryForm.description.trim(),
      purpose: this.categoryForm.purpose
    }).subscribe({
      next: () => {
        this.categoryForm = { description: '', purpose: 'Both' };
        this.showSuccess('Categoria cadastrada com sucesso.');
        this.refresh();
      },
      error: (error: HttpErrorResponse) => this.showError(error)
    });
  }

  saveTransaction(): void {
    this.api.createTransaction({
      ...this.transactionForm,
      description: this.transactionForm.description.trim(),
      amount: Number(this.transactionForm.amount)
    }).subscribe({
      next: () => {
        this.transactionForm = {
          description: '',
          transactionType: 'Expenditure',
          amount: 0,
          categoryId: '',
          customerId: ''
        };
        this.showSuccess('Transação cadastrada com sucesso.');
        this.refresh();
      },
      error: (error: HttpErrorResponse) => this.showError(error)
    });
  }

  availableCategories(): Category[] {
    return this.categories.filter(category =>
      category.purpose === 'Both' || category.purpose === this.transactionForm.transactionType
    );
  }

  changeTransactionType(type: TransactionType): void {
    this.transactionForm.transactionType = type;
    this.transactionForm.categoryId = '';
  }

  setSection(section: Section): void {
    this.activeSection = section;
    this.clearMessages();
  }

  resetCustomerForm(): void {
    this.customerForm = { id: '', name: '', age: 18 };
  }

  money(value: number | undefined): string {
    return (value ?? 0).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
  }

  private showSuccess(message: string): void {
    this.errorMessage = '';
    this.successMessage = message;
    this.changeDetector.markForCheck();
  }

  private showError(error: HttpErrorResponse): void {
    const apiMessage = error.error?.error;
    this.errorMessage = typeof apiMessage === 'string'
      ? apiMessage
      : 'Não foi possível acessar a API. Verifique se o back-end está ativo.';
    this.successMessage = '';
    this.changeDetector.markForCheck();
  }

  private clearMessages(): void {
    this.errorMessage = '';
    this.successMessage = '';
  }
}
