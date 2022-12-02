<#import "parts/common.ftlh" as c>
<#import "parts/addTransaction.ftlh" as at>
<#include "parts/security.ftlh">

<@c.page>
<#--noinspection FtlReferencesInspection-->
    <h1>${currentProfile.firstName} ${currentProfile.lastName} dashboard</h1>

    <div class="container-fluid my-3">
        <h3>Your balance is
            <#if currentProfile.balance gt 0>
                <span class="text-success">${currentProfile.balance}</span>
            <#elseif currentProfile.balance lt 0 >
                <span class="text-danger">${currentProfile.balance}</span>
            <#else>
                <span class="text-muted">${currentProfile.balance}</span>
            </#if>
        </h3>
    </div>

    <div class="container-fluid accordion" id="accordionExample">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                    Add Income Transaction
                </button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <@at.addTransaction incomeCategories true today />
                </div>
            </div>
        </div>
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                    Add Expense Transaction
                </button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
                 data-bs-parent="#accordionExample">
                <div class="accordion-body">
                    <@at.addTransaction expenseCategories false today />
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid my-4">
        <h3>In <span style="text-transform: lowercase">${today.month}</span> you:</h3>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><span class="text-success">Earned ${monthIncome}</span></h5>
                        <h6 class="card-subtitle mb-2">
                            and <span class="text-danger">spent ${monthExpense}</span>
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-success">${monthIncome}</span> - <span
                                class="text-danger">${monthExpense}</span>
                        =
                        <#if monthIncome - monthExpense gt 0>
                            <span class="text-success">${monthIncome - monthExpense}</span>
                        <#elseif monthIncome - monthExpense lt 0>
                            <span class="text-danger">${monthIncome - monthExpense}</span>
                        <#else>
                            <span class="text-muted">0</span>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-success">Earned the most money (${maxIncomeCategory.second!"0"})</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            from ${maxIncomeCategory.first!"nothing"}
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-success">+${maxIncomeCategory.second!"0"}</span>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-danger">Spent the most money (${maxExpenseCategory.second!"0"})</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            on ${maxExpenseCategory.first!"nothing"}
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-danger">-${maxExpenseCategory.second!"0"}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#if recentTransactions[0]??>
        <div class="container-fluid my-4">
            <h3 class="mb-4">Your recent transactions:</h3>
            <div class="row row-cols-1 row-cols-md-3 g-4">


                <#assign maxDate = recentTransactions[0].transactionDate>
                <hr class="col-md-12 mb-1">
                <h4 class="col-md-12 text-lowercase">${maxDate.month} ${maxDate.year}
                </h4>
                <#list recentTransactions as transaction>
                    <#if transaction.transactionDate.month != maxDate.month ||
                    transaction.transactionDate.year != maxDate.year>
                        <#assign maxDate = transaction.transactionDate>
                        <hr class="col-md-12 mt-5">
                        <h4 class="col-md-12 text-lowercase">${maxDate.month} ${maxDate.year}</h4>
                    </#if>
                    <#if transaction.isIncome>
                        <div class="col">
                            <div class="card tran-card border-success">
                                <div class="card-body">
                                    <h5 class="card-title">${transaction.category.category}</h5>
                                    <#if transaction.message != "">
                                        <p class="card-text">${transaction.message}</p>
                                    <#else>
                                        <p class="card-text">
                                            Got ${transaction.amount} from ${transaction.category.category}.
                                        </p>
                                    </#if>
                                </div>
                                <div class="card-footer text-success">
                                    +${transaction.amount}
                                    <span class="text-muted float-end">${transaction.transactionDate}</span>
                                    <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                                </div>
                                <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                            </div>
                        </div>
                    <#else>
                        <div class="col">
                            <div class="card tran-card border-danger">
                                <div class="card-body">
                                    <h5 class="card-title">${transaction.category.category}</h5>
                                    <#if transaction.message != "">
                                        <p class="card-text">${transaction.message}</p>
                                    <#else>
                                        <p class="card-text">
                                            Spent ${transaction.amount} on ${transaction.category.category}.
                                        </p>
                                    </#if>
                                </div>
                                <div class="card-footer text-danger">
                                    -${transaction.amount}
                                    <span class="text-muted float-end">${transaction.transactionDate}</span>
                                    <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list>
                <hr class="col-md-12 mt-5">
            </div>
        </div>
    </#if>
</@c.page>