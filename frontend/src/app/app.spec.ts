import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { App } from './app';
import { FincontrolApiService } from './fincontrol-api.service';

describe('App', () => {
  beforeEach(async () => {
    const apiStub = {
      customers: () => of([]),
      categories: () => of([]),
      transactions: () => of([]),
      peopleReport: () => of({ people: [], total: { revenues: 0, expenditures: 0, balance: 0 } }),
      categoriesReport: () => of({ categories: [], total: { revenues: 0, expenditures: 0, balance: 0 } })
    };

    await TestBed.configureTestingModule({
      imports: [App],
      providers: [{ provide: FincontrolApiService, useValue: apiStub }]
    }).compileComponents();
  });

  it('should create the app and render the overview', async () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();
    await fixture.whenStable();

    expect(fixture.componentInstance).toBeTruthy();
    expect((fixture.nativeElement as HTMLElement).querySelector('h1')?.textContent).toContain('Visão geral');
  });

  it('should clear the selected category when the transaction type changes', () => {
    const fixture = TestBed.createComponent(App);
    fixture.componentInstance.transactionForm.categoryId = 'previous-category';

    fixture.componentInstance.changeTransactionType('Revenue');

    expect(fixture.componentInstance.transactionForm.categoryId).toBe('');
    expect(fixture.componentInstance.transactionForm.transactionType).toBe('Revenue');
  });
});
