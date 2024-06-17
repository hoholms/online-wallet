<#import "parts/common.ftlh" as c>
<#import "parts/addTransaction.ftlh" as at>
<#import "parts/pager.ftlh" as p>
<#include "parts/security.ftlh">

<#assign filterMinAmount = filters.minAmount!""?has_content?then(filters.minAmount?c, '')>
<#assign filterMaxAmount = filters.maxAmount!""?has_content?then(filters.maxAmount?c, '')>
<#assign filterMessage = filters.message!"">
<#assign filterIsIncome = (filters.isIncome?? && filters.isIncome)?string("on", "")!"">
<#assign filterIsExpense = (filters.isExpense?? && filters.isExpense)?string("on", "")!"">
<#assign filterIncomeCategories = filters.incomeCategories!?join(',')!"">
<#assign filterExpenseCategories = filters.expenseCategories!?join(',')!"">
<#assign filterTransactionDateFrom = filters.transactionDateFrom!"">
<#assign filterTransactionDateTo = filters.transactionDateTo!"">
<#assign isAnyParamPresent = filterMinAmount?has_content || filterMaxAmount?has_content || filterMessage?has_content || filterIsIncome?has_content || filterIsExpense?has_content || filterIncomeCategories?has_content || filterExpenseCategories?has_content || filterTransactionDateFrom?has_content || filterTransactionDateTo?has_content>
<#assign filterParams = "minAmount=${filterMinAmount?has_content?then(filterMinAmount?c, '')}&maxAmount=${filterMaxAmount?has_content?then(filterMaxAmount?c, '')}&message=${filterMessage}&isIncome=${filterIsIncome}&isExpense=${filterIsExpense}&incomeCategories=${filterIncomeCategories}&expenseCategories=${filterExpenseCategories}&transactionDateFrom=${filterTransactionDateFrom}&transactionDateTo=${filterTransactionDateTo}">

<@c.page>
<#--noinspection FtlReferencesInspection-->
    <h1 class="ps-2">${currentProfile.firstName} ${currentProfile.lastName} dashboard</h1>

    <div class="container-fluid my-3">
        <h3>Your balance is
            <#if currentProfile.balance gt 0>
                <span class="text-success">${currentProfile.balance?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
            <#elseif currentProfile.balance lt 0 >
                <span class="text-danger">${currentProfile.balance?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
            <#else>
                <span class="text-muted">${currentProfile.balance?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
            </#if>
        </h3>
    </div>

    <div class="container-fluid accordion" id="addTransactionAccordion">
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingOne">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                        data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                    Add Income Transaction
                </button>
            </h2>
            <div id="collapseOne"
                 class="accordion-collapse collapse <#if errorsMap?? && transactionDto.isIncome>show</#if>"
                 aria-labelledby="headingOne"
                 data-bs-parent="#addTransactionAccordion">
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
            <div id="collapseTwo"
                 class="accordion-collapse collapse <#if errorsMap?? && !transactionDto.isIncome>show</#if>"
                 aria-labelledby="headingTwo"
                 data-bs-parent="#addTransactionAccordion">
                <div class="accordion-body">
                    <@at.addTransaction expenseCategories false today />
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid my-4">
        <h3>In <span style="text-transform: lowercase">${today.month}</span> you:</h3>
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col-sm">
                <div class="card" style="height: 100%;">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-success">Earned ${monthIncome?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            and <span
                                    class="text-danger">spent ${monthExpense?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-success">${monthIncome?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        - <span
                                class="text-danger">${monthExpense?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        =
                        <#if monthIncome - monthExpense gt 0>
                            <span class="text-success">${(monthIncome - monthExpense)?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        <#elseif monthIncome - monthExpense lt 0>
                            <span class="text-danger">${(monthIncome - monthExpense)?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        <#else>
                            <span class="text-muted">${0?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="col-sm">
                <div class="card" style="height: 100%;">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-success">Earned the most money (${(maxIncomeCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]})</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            from ${maxIncomeCategory.first!"nothing"}
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-success">+${(maxIncomeCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                    </div>
                </div>
            </div>
            <div class="col-sm">
                <div class="card" style="height: 100%;">
                    <div class="card-body">
                        <h5 class="card-title"><span
                                    class="text-danger">Spent the most money (${(maxExpenseCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]})</span>
                        </h5>
                        <h6 class="card-subtitle mb-2">
                            on ${maxExpenseCategory.first!"nothing"}
                        </h6>
                    </div>
                    <div class="card-footer">
                        <span class="text-danger">-${(maxExpenseCategory.second!"0")?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="accordion" id="filterAccordion">
        <div class="accordion-item">
            <h2 class="accordion-header">
                <button class="accordion-button <#if !isAnyParamPresent>collapsed</#if>" type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#collapseFilter"
                        aria-expanded="false" aria-controls="collapseFilter">
                    Filter Transactions
                </button>
            </h2>
            <div id="collapseFilter" class="accordion-collapse collapse <#if isAnyParamPresent>show</#if>"
                 data-bs-parent="#filterAccordion">
                <div class="accordion-body">
                    <form action="/dashboard" method="get" class="row g-3">
                        <div class="col-md-6">
                            <div class="input-group">
                                <div class="form-floating">
                                    <input type="number" step="0.01" class="form-control" id="minAmount"
                                           name="minAmount"
                                           value="${filterMinAmount?has_content?then(filterMinAmount?c, '')}"
                                           placeholder="Min Amount">
                                    <label for="minAmount">Min Amount</label>
                                </div>
                                <div class="form-floating">
                                    <input type="number" step="0.01" class="form-control" id="maxAmount"
                                           name="maxAmount"
                                           value="${filterMaxAmount?has_content?then(filterMaxAmount?c, '')}"
                                           placeholder="Max Amount">
                                    <label for="maxAmount">Max Amount</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-floating">
                                <input type="text" class="form-control" id="message" name="message"
                                       value="${filterMessage}"
                                       placeholder="Message">
                                <label for="message">Message</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="incomeCategories" class="form-label">Income Categories</label>
                            <div class="form-check form-switch mb-2">
                                <input class="form-check-input" type="checkbox" id="isIncome" name="isIncome"
                                       <#if filters.isIncome??>checked</#if>
                                       onchange="toggleCategorySelects()">
                                <label class="form-check-label" for="isIncome">Income</label>
                            </div>
                            <select id="incomeCategories" name="incomeCategories" class="form-select"
                                    data-placeholder="Income categories" multiple
                                    <#if !filterIsIncome?? || filterIsIncome == "">disabled</#if>>
                                <#list incomeCategories as category>
                                    <option value="${category.id}"
                                            <#if filterIncomeCategories?split(",")?seq_contains(category.id?string)>selected</#if>>${category.category}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label for="expenseCategories" class="form-label">Expense Categories</label>
                            <div class="form-check form-switch mb-2">
                                <input class="form-check-input" type="checkbox" id="isExpense" name="isExpense"
                                       <#if filters.isExpense??>checked</#if>
                                       onchange="toggleCategorySelects()">
                                <label class="form-check-label" for="isExpense">Expense</label>
                            </div>
                            <select id="expenseCategories" name="expenseCategories" class="form-select"
                                    data-placeholder="Expense categories" multiple
                                    <#if !filterIsExpense?? || filterIsExpense == "">disabled</#if>>
                                <#list expenseCategories as category>
                                    <option value="${category.id}"
                                            <#if filterExpenseCategories?split(",")?seq_contains(category.id?string)>selected</#if>>${category.category}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-md-12">
                            <div class="input-group">
                                <div class="form-floating">
                                    <input type="date" class="form-control" id="transactionDateFrom"
                                           name="transactionDateFrom"
                                           value="${filterTransactionDateFrom}" placeholder="Transaction Date From">
                                    <label for="transactionDateFrom">Transaction Date From</label>
                                </div>
                                <div class="form-floating">
                                    <input type="date" class="form-control" id="transactionDateTo"
                                           name="transactionDateTo"
                                           value="${filterTransactionDateTo}" placeholder="Transaction Date To">
                                    <label for="transactionDateTo">Transaction Date To</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">Filter</button>
                            <a href="dashboard" class="btn btn-danger">Clear filters</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <#if recentTransactions.getSize() gte 25>
        <@p.pager "/dashboard" recentTransactions filterParams/>
    </#if>

    <#if recentTransactions.content[0]??>
        <div class="container-fluid my-4">
            <h3 class="mb-4">Your recent transactions:</h3>
            <div class="row row-cols-1 row-cols-md-4 g-4" style="-webkit-transform: translateZ(0)">
                <#assign maxDate = recentTransactions.content[0].transactionDate>
                <hr class="col-md-12 mb-1">
                <h4 class="col-md-12">${maxDate.month?capitalize} ${maxDate.year?string.computer}
                </h4>
                <#list recentTransactions.content as transaction>
                    <#if transaction.transactionDate.month != maxDate.month ||
                    transaction.transactionDate.year != maxDate.year>
                        <#assign maxDate = transaction.transactionDate>
                        <hr class="col-md-12 mt-5">
                        <h4 class="col-md-12">${maxDate.month?capitalize} ${maxDate.year?string.computer}</h4>
                    </#if>
                    <#if transaction.isIncome>
                        <div class="col-sm-3">
                            <div class="card tran-card border-<#if transaction.transactionDate.isAfter(today)>secondary<#else>success</#if>"
                                 style="height: 100%;">
                                <div class="card-body <#if transaction.transactionDate.isAfter(today)>text-muted</#if>">
                                    <h5 class="card-title">${transaction.category.category}</h5>
                                    <#if transaction.message?? && transaction.message != "">
                                        <p class="card-text ">${transaction.message!""}</p>
                                    <#else>
                                        <p class="card-text">
                                            Got ${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                            from ${transaction.category.category}.
                                        </p>
                                    </#if>
                                </div>
                                <div class="card-footer text-<#if transaction.transactionDate.isAfter(today)>muted<#else>success</#if>">
                                    +${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                    <span class="text-muted float-end">${transaction.transactionDate}</span>
                                    <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                                </div>
                                <a href="/transactions/${transaction.id}" class="stretched-link"></a>
                            </div>
                        </div>
                    <#else>
                        <div class="col-sm-3">
                            <div class="card tran-card border-<#if transaction.transactionDate.isAfter(today)>secondary<#else>danger</#if>"
                                 style="height: 100%;">
                                <div class="card-body <#if transaction.transactionDate.isAfter(today)>text-muted</#if>">
                                    <h5 class="card-title">${transaction.category.category}</h5>
                                    <#if transaction.message?? && transaction.message != "">
                                        <p class="card-text">${transaction.message!""}</p>
                                    <#else>
                                        <p class="card-text">
                                            Spent ${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
                                            on ${transaction.category.category}.
                                        </p>
                                    </#if>
                                </div>
                                <div class="card-footer text-<#if transaction.transactionDate.isAfter(today)>muted<#else>danger</#if>">
                                    -${transaction.amount?string[",###.## ¤;; currencyCode=${currentProfile.currency}"]}
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
    <@p.pager "/dashboard" recentTransactions filterParams/>
</@c.page>
